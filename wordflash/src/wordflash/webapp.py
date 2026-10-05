"""WordFlash web application.

Serves the frontend (web/index.html) plus a small JSON API for:
  - generating Anki decks from YAML (with live progress)
  - reviewing/fixing card images
  - downloading generated .apkg files

Run with:
    uv run wordflash-web
"""

import argparse
import io
import json
import os
import sys
import threading
import webbrowser
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from urllib.parse import urlparse, parse_qs

from .flashcard_generator import FlashcardGenerator
from .quiz_flashcard_generator import QuizFlashcardGenerator
from .review_server import ReviewApp

HTML_PATH = Path(__file__).parent / "web" / "app.html"

# Generation job state (single job at a time).
GEN = {
    "running": False,
    "done": False,
    "error": None,
    "output": None,
    "log": io.StringIO(),
    "lock": threading.Lock(),
}

# Review app state.
REVIEW = {"app": None, "key": None}


def list_yamls() -> list[dict]:
    files = []
    for base in ("data/categories", "data"):
        root = Path(base)
        if not root.is_dir():
            continue
        for p in sorted(root.glob("*.yaml")):
            files.append({"path": str(p), "name": str(p)})
    return files


def list_decks() -> list[dict]:
    out = []
    root = Path("data/output")
    if root.is_dir():
        for p in sorted(root.glob("*.apkg")):
            out.append(
                {
                    "name": p.name,
                    "size": p.stat().st_size,
                    "url": f"/api/download/{p.name}",
                }
            )
    return out


def _generation_thread(config: dict) -> None:
    old_stdout = sys.stdout

    class Tee:
        def write(self, s):
            old_stdout.write(s)
            with GEN["lock"]:
                GEN["log"].write(s)

        def flush(self):
            old_stdout.flush()

    sys.stdout = Tee()
    try:
        output_dir = Path(config.get("output_dir", "data/output"))
        output_dir.mkdir(parents=True, exist_ok=True)

        input_type = config.get("type", "auto")
        yaml_path = config.get("yaml", "")

        if input_type == "auto":
            import yaml as _yaml

            data = _yaml.safe_load(Path(yaml_path).read_text(encoding="utf-8"))
            input_type = "quiz" if isinstance(data, dict) and "quizzes" in data else "vocab"

        if input_type == "quiz":
            generator = QuizFlashcardGenerator(
                output_dir=output_dir,
                deck_name=config.get("deck_name", "WordFlash Deck"),
                question_lang=config.get("question_lang", "en"),
                answer_lang=config.get("answer_lang", "en"),
                manual_image_approval=not config.get("batch", False),
                clipboard_only=False,
                request_delay=float(config.get("request_delay", 1.0)),
                image_provider=config.get("image_provider", "auto"),
                force=bool(config.get("force", False)),
            )
        else:
            generator = FlashcardGenerator(
                output_dir=output_dir,
                deck_name=config.get("deck_name", "WordFlash Deck"),
                source_lang=config.get("source_lang", "de"),
                target_lang=config.get("target_lang", "en"),
                clipboard_only=False,
                request_delay=float(config.get("request_delay", 1.0)),
                image_provider=config.get("image_provider", "auto"),
                force=bool(config.get("force", False)),
            )

        result = generator.generate_from_yaml(Path(yaml_path))
        GEN["output"] = result
    except Exception as e:  # noqa: BLE001
        GEN["error"] = str(e)
    finally:
        sys.stdout = old_stdout
        GEN["running"] = False
        GEN["done"] = True


def get_review_app(config: dict) -> ReviewApp:
    key = (
        config.get("yaml", "data/categories/kids_picture_words.yaml"),
        config.get("images_dir", "data/output/images"),
        config.get("provider", "wikimedia"),
    )
    if REVIEW["app"] is None or REVIEW["key"] != key:
        REVIEW["app"] = ReviewApp(
            Path(key[0]),
            Path(key[1]),
            key[2],
            Path(config.get("state", "data/review_state.json")),
            Path(config.get("out", "data/fix_wrong.yaml")),
        )
        REVIEW["key"] = key
    return REVIEW["app"]


class Handler(BaseHTTPRequestHandler):
    images_dir: Path = Path("data/output/images")

    def log_message(self, *args):  # silence request logs
        pass

    def _json(self, payload, status: int = 200) -> None:
        data = json.dumps(payload).encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(data)))
        self.end_headers()
        self.wfile.write(data)

    def _send_bytes(self, data: bytes, content_type: str) -> None:
        self.send_response(200)
        self.send_header("Content-Type", content_type)
        self.send_header("Content-Length", str(len(data)))
        self.end_headers()
        self.wfile.write(data)

    def _read_body(self) -> dict:
        length = int(self.headers.get("Content-Length", 0))
        try:
            return json.loads(self.rfile.read(length) or b"{}")
        except Exception:
            return {}

    def do_GET(self):
        parsed = urlparse(self.path)
        path = parsed.path

        if path == "/":
            if HTML_PATH.exists():
                self._send_bytes(HTML_PATH.read_bytes(), "text/html; charset=utf-8")
            else:
                self._send_bytes(
                    b"<h1>web/app.html not found</h1>", "text/html; charset=utf-8"
                )
        elif path == "/api/yamls":
            self._json({"yamls": list_yamls()})
        elif path == "/api/decks":
            self._json({"decks": list_decks()})
        elif path == "/api/generate/status":
            qs = parse_qs(parsed.query)
            after = int(qs.get("after", [0])[0])
            with GEN["lock"]:
                text = GEN["log"].getvalue()
            lines = text.splitlines()
            self._json(
                {
                    "running": GEN["running"],
                    "done": GEN["done"],
                    "error": GEN["error"],
                    "output": GEN["output"],
                    "total": len(lines),
                    "lines": lines[after:],
                }
            )
        elif path.startswith("/api/download/"):
            name = os.path.basename(path)
            deck = Path("data/output") / name
            if deck.is_file():
                self._send_bytes(deck.read_bytes(), "application/octet-stream")
            else:
                self._json({"error": "not found"}, 404)
        elif path.startswith("/images/"):
            name = os.path.basename(path)
            img = self.images_dir / name
            if img.is_file():
                self._send_bytes(img.read_bytes(), "image/jpeg")
            else:
                self._json({"error": "not found"}, 404)
        elif path == "/api/state":
            if REVIEW["app"] is None:
                self._json({"error": "review not initialized"}, 400)
            else:
                self._json(REVIEW["app"].state_dict())
        else:
            self._json({"error": "not found"}, 404)

    def do_POST(self):
        parsed = urlparse(self.path)
        path = parsed.path
        body = self._read_body()

        if path == "/api/generate":
            if GEN["running"]:
                self._json({"error": "generation already running"}, 409)
                return
            GEN.update(
                {
                    "running": True,
                    "done": False,
                    "error": None,
                    "output": None,
                    "log": io.StringIO(),
                }
            )
            threading.Thread(target=_generation_thread, args=(body,), daemon=True).start()
            self._json({"started": True})

        elif path == "/api/review/init":
            try:
                app = get_review_app(body)
                self._json(app.state_dict())
            except Exception as e:
                self._json({"error": str(e)}, 400)

        elif path == "/api/decision":
            try:
                REVIEW["app"].set_decision(body["term"], body["decision"])
                self._json(REVIEW["app"].state_dict())
            except Exception as e:
                self._json({"error": str(e)}, 400)

        elif path == "/api/search":
            self._json({"candidates": REVIEW["app"].search_candidates(body.get("term", ""))})

        elif path == "/api/pick":
            ok = REVIEW["app"].pick_image(body.get("term", ""), body.get("url", ""))
            payload = REVIEW["app"].state_dict()
            payload["ok"] = ok
            self._json(payload)

        elif path == "/api/undo":
            REVIEW["app"].undo(body.get("term", ""))
            self._json(REVIEW["app"].state_dict())

        elif path == "/api/export":
            count, out_path = REVIEW["app"].export_fix_yaml()
            self._json({"exported": count, "path": str(out_path)})

        else:
            self._json({"error": "not found"}, 404)


def main() -> None:
    parser = argparse.ArgumentParser(description="WordFlash web application")
    parser.add_argument("--host", default="127.0.0.1")
    parser.add_argument("--port", type=int, default=8000)
    parser.add_argument("--images-dir", default="data/output/images")
    parser.add_argument("--no-browser", action="store_true", help="Don't open a browser")
    args = parser.parse_args()

    Handler.images_dir = Path(args.images_dir)
    server = ThreadingHTTPServer((args.host, args.port), Handler)
    url = f"http://{args.host}:{args.port}"
    print(f"WordFlash web app running at {url}")
    print("Press Ctrl+C to stop.")
    if not args.no_browser:
        webbrowser.open(url)

    try:
        server.serve_forever()
    except KeyboardInterrupt:
        print("\nStopped.")


if __name__ == "__main__":
    main()
