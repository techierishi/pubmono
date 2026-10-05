"""Web-based image review + fix for WordFlash.

Serves a single-page app that shows each card's current image. On reject it
searches the configured image providers for alternatives, lets you pick one,
and falls back to pasting an image URL. Everything is saved directly to the
image cache, so afterwards you can just regenerate the deck.

Usage:
    uv run wordflash-review data/categories/kids_picture_words.yaml
"""

import argparse
import hashlib
import json
import os
import webbrowser
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from urllib.parse import urlparse

import yaml

try:
    from .quiz_loader import QuizLoader
except ImportError:  # run directly as a script
    from wordflash.quiz_loader import QuizLoader

try:
    from .image_service import ImageService
except ImportError:
    from wordflash.image_service import ImageService


def get_image_filename(search_term: str, provider: str = "wikimedia") -> str:
    """Mirror ImageService._get_image_filename so we can find cached files."""
    word_hash = hashlib.md5(search_term.encode()).hexdigest()[:8]
    safe_word = "".join(c for c in search_term if c.isalnum() or c in "-_")[:20]
    provider_tag = provider.replace("-", "_")
    return f"{safe_word}_{provider_tag}_{word_hash}.jpg"


def load_cards(yaml_path: Path, images_dir: Path, provider: str) -> list[dict]:
    quizzes = QuizLoader().load_from_yaml(str(yaml_path))
    cards = []
    seen = set()
    for q in quizzes:
        term = q.get("question_image_search_term") or q.get("question")
        if term is None or term in seen:
            continue
        seen.add(term)
        fname = get_image_filename(term, provider)
        image_path = images_dir / fname
        cards.append(
            {
                "question": str(q.get("question", term)),
                "search_term": str(term),
                "image": f"/images/{fname}" if image_path.is_file() else None,
            }
        )
    return cards


HTML_PATH = Path(__file__).parent / "web" / "review.html"


def _load_html() -> str:
    return HTML_PATH.read_text(encoding="utf-8")


class ReviewApp:
    def __init__(
        self,
        yaml_path: Path,
        images_dir: Path,
        provider: str,
        state_path: Path,
        out_path: Path,
    ):
        self.cards = load_cards(yaml_path, images_dir, provider)
        self.state_path = state_path
        self.out_path = out_path
        self.provider = provider
        self.images_dir = images_dir
        self.image_service = ImageService(
            output_dir=images_dir.parent,
            image_provider=provider,
            request_delay=1.0,
        )
        self.decisions = self._load_state()
        self._auto_reject_missing()
        self._save_state()

    def _load_state(self) -> dict:
        if self.state_path.exists():
            try:
                return json.loads(self.state_path.read_text(encoding="utf-8")).get(
                    "decisions", {}
                )
            except Exception:
                return {}
        return {}

    def _save_state(self) -> None:
        self.state_path.parent.mkdir(parents=True, exist_ok=True)
        self.state_path.write_text(
            json.dumps({"decisions": self.decisions}, ensure_ascii=False, indent=2),
            encoding="utf-8",
        )

    def _auto_reject_missing(self) -> None:
        for card in self.cards:
            if card["image"] is None and card["search_term"] not in self.decisions:
                self.decisions[card["search_term"]] = "reject"

    def set_decision(self, term: str, decision: str) -> None:
        if decision not in ("accept", "fixed", "reject", "skip"):
            raise ValueError(decision)
        if any(c["search_term"] == term for c in self.cards):
            self.decisions[term] = decision
            self._save_state()

    def undo(self, term: str) -> None:
        self.decisions.pop(term, None)
        self._save_state()

    def search_candidates(self, term: str) -> list[str]:
        return list(self.image_service._iter_interactive_candidates(term, per_provider=3))

    def pick_image(self, term: str, url: str) -> bool:
        if not url:
            return False
        fname = get_image_filename(term, self.provider)
        image_path = self.images_dir / fname
        saved = self.image_service._save_image(url, image_path)
        if saved:
            self.decisions[term] = "fixed"
            for c in self.cards:
                if c["search_term"] == term:
                    c["image"] = f"/images/{fname}"
            self._save_state()
            return True
        return False

    def state_dict(self) -> dict:
        counts = {"accept": 0, "fixed": 0, "reject": 0, "skip": 0}
        for card in self.cards:
            status = self.decisions.get(card["search_term"])
            if status in counts:
                counts[status] += 1

        cards = []
        for c in self.cards:
            image = c["image"]
            if image:
                # Cache-bust so the browser refetches after we overwrite a file.
                fname = image.rsplit("/", 1)[-1]
                ipath = self.images_dir / fname
                if ipath.is_file():
                    image = f"/images/{fname}?v={int(ipath.stat().st_mtime_ns)}"
            cards.append(
                {
                    "question": c["question"],
                    "search_term": c["search_term"],
                    "image": image,
                    "status": self.decisions.get(c["search_term"]),
                }
            )

        return {
            "total": len(self.cards),
            "decided": sum(counts.values()),
            "counts": counts,
            "cards": cards,
        }

    def export_fix_yaml(self) -> tuple[int, Path]:
        rejected = [
            (c["question"], c["search_term"])
            for c in self.cards
            if self.decisions.get(c["search_term"]) == "reject"
        ]
        questions = [
            {
                "question": question,
                "question_media": {"text": False, "audio": False, "image": True},
                "question_image_search_term": term,
                "answer": "-",
                "answer_media": {"text": False, "audio": False, "image": False},
            }
            for question, term in rejected
        ]
        doc = {"quizzes": [{"category": "Manual Fixes", "questions": questions}]}
        self.out_path.parent.mkdir(parents=True, exist_ok=True)
        self.out_path.write_text(
            yaml.safe_dump(doc, allow_unicode=True, sort_keys=False, default_flow_style=False),
            encoding="utf-8",
        )
        return len(rejected), self.out_path


class Handler(BaseHTTPRequestHandler):
    app: ReviewApp = None
    images_dir: Path = None

    def log_message(self, *args):  # silence request logs
        pass

    def _json(self, payload: dict, status: int = 200) -> None:
        data = json.dumps(payload).encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(data)))
        self.end_headers()
        self.wfile.write(data)

    def _serve_image(self, path: str) -> None:
        name = os.path.basename(path)
        image_path = self.images_dir / name
        if not image_path.is_file():
            self._json({"error": "not found"}, 404)
            return
        data = image_path.read_bytes()
        self.send_response(200)
        self.send_header("Content-Type", "image/jpeg")
        self.send_header("Content-Length", str(len(data)))
        self.end_headers()
        self.wfile.write(data)

    def do_GET(self):
        parsed = urlparse(self.path)
        if parsed.path == "/":
            data = _load_html().encode("utf-8")
            self.send_response(200)
            self.send_header("Content-Type", "text/html; charset=utf-8")
            self.send_header("Content-Length", str(len(data)))
            self.end_headers()
            self.wfile.write(data)
        elif parsed.path == "/api/state":
            self._json(self.app.state_dict())
        elif parsed.path.startswith("/images/"):
            self._serve_image(parsed.path)
        else:
            self._json({"error": "not found"}, 404)

    def do_POST(self):
        parsed = urlparse(self.path)
        length = int(self.headers.get("Content-Length", 0))
        try:
            body = json.loads(self.rfile.read(length) or b"{}")
        except Exception:
            body = {}

        if parsed.path == "/api/decision":
            try:
                self.app.set_decision(body["term"], body["decision"])
            except Exception as e:
                self._json({"error": str(e)}, 400)
                return
            self._json(self.app.state_dict())
        elif parsed.path == "/api/search":
            self._json({"candidates": self.app.search_candidates(body.get("term", ""))})
        elif parsed.path == "/api/pick":
            ok = self.app.pick_image(body.get("term", ""), body.get("url", ""))
            payload = self.app.state_dict()
            payload["ok"] = ok
            self._json(payload)
        elif parsed.path == "/api/undo":
            self.app.undo(body.get("term", ""))
            self._json(self.app.state_dict())
        elif parsed.path == "/api/export":
            count, out_path = self.app.export_fix_yaml()
            self._json({"exported": count, "path": str(out_path)})
        else:
            self._json({"error": "not found"}, 404)


def main() -> None:
    parser = argparse.ArgumentParser(description="Web-based image review + fix for WordFlash")
    parser.add_argument("yaml", help="Main quiz YAML file")
    parser.add_argument("--images-dir", default="data/output/images")
    parser.add_argument("--provider", default="wikimedia")
    parser.add_argument("--state", default="data/review_state.json")
    parser.add_argument("--out", default="data/fix_wrong.yaml")
    parser.add_argument("--host", default="127.0.0.1")
    parser.add_argument("--port", type=int, default=8000)
    parser.add_argument("--no-browser", action="store_true", help="Don't open a browser")
    args = parser.parse_args()

    app = ReviewApp(
        Path(args.yaml),
        Path(args.images_dir),
        args.provider,
        Path(args.state),
        Path(args.out),
    )

    Handler.app = app
    Handler.images_dir = Path(args.images_dir)

    server = ThreadingHTTPServer((args.host, args.port), Handler)
    url = f"http://{args.host}:{args.port}"
    print(f"WordFlash review running at {url}")
    print(f"{len(app.cards)} cards loaded.")
    print("Press Ctrl+C to stop. Progress is saved automatically.")
    if not args.no_browser:
        webbrowser.open(url)

    try:
        server.serve_forever()
    except KeyboardInterrupt:
        print("\nStopped.")


if __name__ == "__main__":
    main()
