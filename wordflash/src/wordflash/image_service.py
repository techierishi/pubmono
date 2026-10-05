import hashlib
import html
import platform
import re
import subprocess
import tempfile
import time
import webbrowser
from pathlib import Path
from typing import Optional
from urllib.parse import quote

import requests

USER_AGENT = (
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
    "(KHTML, like Gecko) Chrome/120.0 Safari/537.36"
)

# Wikimedia requires an identifying User-Agent (app name + contact).
WIKIMEDIA_USER_AGENT = (
    "WordFlash/0.1 (Anki flashcard generator; contact: wordflash@example.com)"
)


class ImageService:
    def __init__(
        self,
        output_dir: Path,
        auto_approve: bool = False,
        clipboard_only: bool = False,
        request_delay: float = 1.0,
        image_provider: str = "auto",
        force: bool = False,
    ):
        self.output_dir = output_dir
        self.images_dir = output_dir / "images"
        self.images_dir.mkdir(exist_ok=True)
        self.auto_approve = auto_approve
        self.clipboard_only = clipboard_only
        # Delay (seconds) between image API searches to be polite to providers.
        self.request_delay = request_delay
        self.image_provider = image_provider
        # When True, ignore cached images and re-download (used for manual review).
        self.force = force

        # Provider order. "auto" tries DuckDuckGo first, then Wikimedia
        # Commons (best semantic correctness for concrete nouns), then Bing,
        # then Pixabay. Explicit "duckduckgo" still falls back so images keep
        # working when DuckDuckGo rate-limits us.
        if image_provider == "auto":
            self._providers = ["duckduckgo", "wikimedia", "bing", "pixabay"]
        elif image_provider == "duckduckgo":
            self._providers = ["duckduckgo", "wikimedia", "bing"]
        elif image_provider == "wikimedia":
            self._providers = ["wikimedia", "bing"]
        else:
            self._providers = [image_provider]

        # Providers that were rate-limited/blocked are disabled for the run.
        self._disabled_providers = {}

    def download_image(self, word: str, manual_approval: bool = True) -> Optional[str]:
        try:
            image_filename = self._get_image_filename(word)
            image_path = self.images_dir / image_filename

            if not self.force and image_path.exists():
                return str(image_path)

            if self.auto_approve or not manual_approval:
                # Try each candidate URL until one downloads successfully.
                # Some hosts block hotlinking, so the first URL can 403.
                image_urls = self._search_images_multiple(word, interactive=False)
                for image_url in image_urls:
                    saved = self._save_image(image_url, image_path)
                    if saved:
                        return saved
                return None

            total_shown = 0
            for image_url in self._iter_interactive_candidates(word):
                total_shown += 1
                headers = {"User-Agent": USER_AGENT}
                try:
                    response = requests.get(image_url, timeout=30, headers=headers)
                    if response.status_code != 200:
                        continue

                    with tempfile.NamedTemporaryFile(
                        suffix=".jpg", delete=False
                    ) as tmp:
                        tmp.write(response.content)
                        tmp_path = tmp.name

                    approval = self._show_image_for_approval(
                        tmp_path, word, total_shown
                    )

                    if approval == 1:
                        image_path.parent.mkdir(parents=True, exist_ok=True)
                        with open(image_path, "wb") as f:
                            f.write(response.content)
                        Path(tmp_path).unlink()
                        return str(image_path)
                    elif approval == -1:
                        Path(tmp_path).unlink()
                        print(f"  ✗ Skipping")
                        return self._get_image_from_clipboard(word, image_path)
                    else:
                        Path(tmp_path).unlink()
                        continue
                except Exception as e:
                    print(f"  Error processing image {total_shown}: {e}")
                    continue

            # Rejected every candidate from every provider.
            print(f"  ✗ No images approved")
            return self._get_image_from_clipboard(word, image_path)

        except Exception as e:
            print(f"Failed to download image for '{word}': {e}")
            return None

    def _get_image_from_clipboard(self, word: str, image_path: Path) -> Optional[str]:
        self._open_google_search(word)
        print(f"  Browser opened with Google search for '{word}'")
        time.sleep(1)
        clipboard_url = input("  Paste image URL from clipboard (or press Enter to skip): ").strip()
        if clipboard_url:
            headers = {"User-Agent": USER_AGENT}
            try:
                clip_response = requests.get(clipboard_url, timeout=30, headers=headers)
                if clip_response.status_code == 200:
                    image_path.parent.mkdir(parents=True, exist_ok=True)
                    with open(image_path, "wb") as f:
                        f.write(clip_response.content)
                    print(f"  ✓ Image saved from clipboard")
                    return str(image_path)
            except Exception as e:
                print(f"  ✗ Failed to download from clipboard: {e}")
        return None

    def _clipboard_image_search(self, query: str) -> list[str]:
        urls = []
        try:
            print(f"\n  ⚠ Clipboard mode: Please provide image URL for '{query}'")
            self._open_google_search(query)
            print(f"  Browser opened with Google search for '{query}'")
            time.sleep(1)
            clipboard_url = input("  Paste image URL from clipboard (or press Enter to skip): ").strip()
            if clipboard_url:
                urls.append(clipboard_url)
                print(f"  ✓ Added image from clipboard")
        except Exception:
            pass
        return urls

    def _show_image_for_approval(
        self, image_path: str, search_term: str, attempt: int
    ) -> int:
        print(f"\n  Showing image {attempt}...")
        self._open_image(image_path)

        while True:
            approval = (
                input(f"  Approve this image for '{search_term}'? (y/n/s): ")
                .strip()
                .lower()
            )
            if approval == "y":
                return 1
            elif approval == "n":
                return 0
            elif approval == "s":
                return -1
            else:
                print("  Enter 'y', 'n', or 's' for skip.")

    def _open_image(self, image_path: str) -> None:
        system = platform.system()
        try:
            if system == "Darwin":
                subprocess.run(["open", image_path], check=True, timeout=5)
            elif system == "Windows":
                subprocess.run(["start", image_path], shell=True, check=True, timeout=5)
            elif system == "Linux":
                self._open_image_linux(image_path)
            else:
                print(f"  [Image saved temporarily - unsupported OS: {system}]")
        except subprocess.TimeoutExpired:
            print(f"  [Image viewer timed out]")
        except Exception as e:
            print(f"  [Could not open image: {e}]")

    def _open_image_linux(self, image_path: str) -> None:
        viewers = ["xdg-open", "eog", "display", "feh", "gpicview", "geeqie"]
        for viewer in viewers:
            try:
                subprocess.Popen(
                    [viewer, image_path],
                    stdout=subprocess.DEVNULL,
                    stderr=subprocess.DEVNULL,
                )
                return
            except FileNotFoundError:
                continue
        print(f"  [Image saved temporarily - no viewer found. Install: apt install eog]")

    def _open_google_search(self, query: str) -> None:
        try:
            search_url = f"https://www.google.com/search?q={quote(query)}"
            webbrowser.open(search_url)
        except Exception as e:
            print(f"Could not open browser: {e}")

    def _save_image(self, image_url: str, image_path: Path) -> Optional[str]:
        headers = {"User-Agent": USER_AGENT}
        for attempt in range(2):
            try:
                response = requests.get(image_url, timeout=30, headers=headers)
                if response.status_code == 429:
                    if attempt == 0:
                        time.sleep(10)
                        continue
                    print("Failed to save image: 429 (rate-limited) after retry")
                    return None
                response.raise_for_status()
                with open(image_path, "wb") as f:
                    f.write(response.content)
                return str(image_path)
            except Exception as e:
                print(f"Failed to save image: {e}")
                return None
        return None

    def _search_images_multiple(
        self, query: str, interactive: bool = False
    ) -> list[str]:
        if self.clipboard_only:
            return self._clipboard_image_search(query)

        for provider in self._providers:
            results = self._search_provider(provider, query)
            if results:
                print(f"  ✓ {provider.title()}: found {len(results)} images")
                return results[:3]

        if interactive:
            return self._clipboard_image_search(query)
        return []

    def _iter_interactive_candidates(
        self, query: str, per_provider: int = 3
    ):
        """Yield candidate image URLs lazily, provider by provider.

        The next provider is only searched after the user rejects every image
        from the current provider, so manual review does not over-fetch.
        """
        seen = set()
        for provider in self._providers:
            results = self._search_provider(provider, query)
            if not results:
                continue
            print(f"  ✓ {provider.title()}: found {len(results)} images")
            for url in results[:per_provider]:
                if url not in seen:
                    seen.add(url)
                    yield url

    def _search_provider(self, provider: str, query: str) -> list[str]:
        """Run a single provider search, returning [] on any failure."""
        if self._disabled_providers.get(provider):
            return []

        try:
            if provider == "duckduckgo":
                return self._duckduckgo_search(query)
            if provider == "wikimedia":
                return self._wikimedia_search(query)
            if provider == "bing":
                return self._bing_search(query)
            if provider == "pixabay":
                return self._pixabay_search(query)
            return []
        except Exception as e:
            err = str(e)
            # 403/429 mean the provider is rate-limiting/blocking us. Disable
            # it for the rest of the run and let the caller try the next one.
            if "403" in err or "429" in err:
                self._disabled_providers[provider] = True
                code = "403" if "403" in err else "429"
                print(
                    f"  ⚠ {provider.title()} returned {code} (rate-limited); "
                    "skipping this provider."
                )
            else:
                print(f"  ✗ {provider.title()} search failed: {e}")
            return []

    def _duckduckgo_search(self, query: str) -> list[str]:
        if self.request_delay > 0:
            time.sleep(self.request_delay)

        session = requests.Session()
        session.headers.update(
            {"User-Agent": USER_AGENT, "Accept-Language": "en-US,en;q=0.9"}
        )

        search_resp = session.get(
            "https://duckduckgo.com/",
            params={"q": query, "iax": "images", "ia": "images"},
            timeout=20,
        )
        search_resp.raise_for_status()

        match = re.search(r'vqd=["\']([^"\']+)', search_resp.text)
        if not match:
            return []
        vqd = match.group(1)

        api_resp = session.get(
            "https://duckduckgo.com/i.js",
            params={
                "l": "us-en",
                "o": "json",
                "q": query,
                "vqd": vqd,
                "p": "1",
                "f": ",,,",
                "s": "0",
            },
            headers={
                "User-Agent": USER_AGENT,
                "Accept": "application/json, text/javascript, */*; q=0.01",
                "Referer": "https://duckduckgo.com/",
                "X-Requested-With": "XMLHttpRequest",
            },
            timeout=20,
        )
        if api_resp.status_code == 403:
            raise RuntimeError("403")
        api_resp.raise_for_status()

        data = api_resp.json()
        return [
            item.get("image")
            for item in data.get("results", [])
            if item.get("image")
        ]

    def _wikimedia_search(self, query: str) -> list[str]:
        # Wikimedia asks clients to stay at or below ~1 request/second and to
        # send an identifying User-Agent. We use a 2s floor to be safe.
        time.sleep(max(self.request_delay, 2.0))

        params = {
            "action": "query",
            "generator": "search",
            "gsrsearch": query,
            "gsrnamespace": "6",  # File namespace
            "gsrlimit": "10",
            "prop": "imageinfo",
            "iiprop": "url",
            "iiurlwidth": "640",
            "format": "json",
        }
        resp = requests.get(
            "https://commons.wikimedia.org/w/api.php",
            params=params,
            headers={"User-Agent": WIKIMEDIA_USER_AGENT},
            timeout=20,
        )
        # Retry once with a longer backoff if we hit the rate limit.
        if resp.status_code == 429:
            time.sleep(15)
            resp = requests.get(
                "https://commons.wikimedia.org/w/api.php",
                params=params,
                headers={"User-Agent": WIKIMEDIA_USER_AGENT},
                timeout=20,
            )
        if resp.status_code == 429:
            raise RuntimeError("429")
        resp.raise_for_status()
        data = resp.json()

        urls = []
        for page in data.get("query", {}).get("pages", {}).values():
            info = (page.get("imageinfo") or [{}])[0]
            url = info.get("thumburl") or info.get("url")
            if url:
                urls.append(url)
        return urls

    def _bing_search(self, query: str) -> list[str]:
        if self.request_delay > 0:
            time.sleep(self.request_delay)

        resp = requests.get(
            "https://www.bing.com/images/search",
            params={"q": query, "form": "HDRSC2", "first": "1"},
            headers={"User-Agent": USER_AGENT, "Accept-Language": "en-US,en;q=0.9"},
            timeout=20,
        )
        resp.raise_for_status()

        raw_urls = re.findall(r"murl&quot;:&quot;(.*?)&quot;", resp.text)
        if not raw_urls:
            raw_urls = re.findall(r'"murl":"(.*?)"', resp.text)

        image_extensions = (".jpg", ".jpeg", ".png", ".webp", ".gif")
        urls = []
        seen = set()
        for url in raw_urls:
            url = html.unescape(url)
            if not url.startswith("http"):
                continue
            path = url.split("?")[0].lower()
            if not any(path.endswith(ext) for ext in image_extensions):
                continue
            if url in seen:
                continue
            seen.add(url)
            urls.append(url)
        return urls

    def _pixabay_search(self, query: str) -> list[str]:
        try:
            # Rate-limit requests so we don't hammer the image API.
            if self.request_delay > 0:
                time.sleep(self.request_delay)
            pixabay_url = f"https://pixabay.com/api/?key=9656065-a4094594c34f9ac14c7fc4c39&q={query}&image_type=photo&category=all&min_width=400&per_page=3&safesearch=true"
            response = requests.get(pixabay_url, timeout=10)
            if response.status_code == 200:
                data = response.json()
                return [hit["webformatURL"] for hit in data.get("hits", [])]
        except Exception as e:
            print(f"Error searching Pixabay: {e}")
        return []

    def _get_image_filename(self, word: str) -> str:
        word_hash = hashlib.md5(word.encode()).hexdigest()[:8]
        safe_word = "".join(c for c in word if c.isalnum() or c in "-_")[:20]
        provider_tag = self.image_provider.replace("-", "_")
        return f"{safe_word}_{provider_tag}_{word_hash}.jpg"
