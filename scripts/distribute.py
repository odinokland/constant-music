from pathlib import Path
import re
import shutil
import tomllib
import argparse

parser = argparse.ArgumentParser("Copy files from build/libs to folders with the format <loader>-<mc_version>")
parser.add_argument("destination", type=str, help="Base directory which contains the loader directories (No trailing backslash)")
args = parser.parse_args()

script_dir = Path(__file__).parent

with open(f"{script_dir}/../stonecutter.properties.toml", "rb") as f:
	data = tomllib.load(f)

mod_version = data["mod"]["version"]

dir_path = Path(f"{script_dir}/../build/libs/{mod_version}-SNAPSHOT")

for file_path in dir_path.iterdir():
	if file_path.is_file():
		pattern = r".*-(neoforge|forge|fabric)\+(\d{1,2}\.\d{1,2}(?>\.\d{1,2})?)-.*(?:SNAPSHOT\.jar|gametest\.jar)$"
		match = re.search(pattern, file_path.name)
		if match:
			print(f"Match found: {match.group(0)}")
			print("Loader: ", match.group(1))
			print("MC version: ", match.group(2))
			destination = f"{args.destination}\\{match.group(1)}-{match.group(2)}\\mods\\"
			print(f"Copying {file_path.name} to {destination}")
			shutil.copy(file_path, destination)
			print("\n")
