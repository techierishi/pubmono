import os
import subprocess
import shutil
import tempfile
import time
import sys

def generate_random_name():
    now = str(time.time()).replace(".", "")
    return f"folder_{now}"

def main():
    if len(sys.argv) < 2:
        print("Usage: python main.py path/file.gz")
        return

    gzip_file_path = sys.argv[1]
    tmp_folder = tempfile.gettempdir()

    random_folder_name = generate_random_name()
    random_folder_path = os.path.join(tmp_folder, random_folder_name)
    os.mkdir(random_folder_path)

    output_file_name = os.path.splitext(os.path.basename(gzip_file_path))[0]
    decompressed_file_path = os.path.join(random_folder_path, output_file_name)

    cmd_decompress = f"gzip -d -c {gzip_file_path} > {decompressed_file_path}"
    subprocess.run(cmd_decompress, shell=True, check=True)

    cmd_vim = f"vim {decompressed_file_path}"
    subprocess.run(cmd_vim, shell=True, check=True)

    cmd_compress = f"gzip {decompressed_file_path}"
    subprocess.run(cmd_compress, shell=True, check=True)

    compressed_file_path = decompressed_file_path + ".gz"
    shutil.move(compressed_file_path, os.path.join(os.path.dirname(gzip_file_path), os.path.basename(compressed_file_path)))


if __name__ == "__main__":
    main()
