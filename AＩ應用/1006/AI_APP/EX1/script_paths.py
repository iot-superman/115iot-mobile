from pathlib import Path


BASE_DIR = Path(__file__).resolve().parent
DATA_DIR = BASE_DIR
IMAGE_DIR = BASE_DIR / "image"


def data_file(name: str) -> Path:
    return DATA_DIR / name


def image_file(name: str) -> Path:
    IMAGE_DIR.mkdir(exist_ok=True)
    return IMAGE_DIR / name
