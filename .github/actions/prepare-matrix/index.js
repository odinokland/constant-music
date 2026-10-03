import { setOutput, setFailed } from '@actions/core'
import fs from 'node:fs';
import path from "path";
import { parse } from "smol-toml";
import semver from "semver";

const main = () => {
  	const debug = process.env.DEBUG;
	const workspace = process.env.GITHUB_WORKSPACE;
	if (!workspace) {
		throw new Error("GITHUB_WORKSPACE is not defined. Ensure actions/checkout was run.");
	}

	const relativeFilePath = "stonecutter.properties.toml";
	const filePath = path.resolve(workspace, relativeFilePath);

	if (!fs.existsSync(filePath)) {
		throw new Error(`File not found at: ${filePath}`);
	}

	const fileContent = fs.readFileSync(filePath);
	const tomlData = parse(fileContent.toString('utf8'))

	if (debug) {
		console.log(`Successfully read file content:\n${fileContent}`);
	}

	const getJavaVersion = (ver) => {
		const versionVersion = semver.coerce(ver, {loose: true})
		if (semver.gte(versionVersion, "26.0.0", {loose: true})) {
			return 25
		}
		if (semver.gte(versionVersion, "1.20.5", {loose: true})) {
			return 21
		}
		if (semver.gte(versionVersion, "1.18.0", {loose: true})) {
			return 17
		}
		if (semver.gte(versionVersion, "1.17.0", {loose: true})) {
			return 16
		}
		return 8
	}

	const modId = tomlData?.mod.id;
	const modVersion = tomlData?.mod.version;

	const outputData = []
	Object.keys(tomlData?.fabric).forEach((key) => {
		if (key !== "deps") {
			const fApi = tomlData.fabric[key].deps["fabricApi"]
			const version = tomlData.fabric[key].deps["minecraft"]
			// Skip 26.3 until there's a version-runtime-test release for it
			if (version === "26.3") return;
			const v = {
				modId,
				modVersion,
				version,
				modLoader: "fabric",
				type: "fabric",
				java: getJavaVersion(version),
				regex: ".*fabric.*",
				fabricApi: fApi,
			};
			outputData.push(v)
		}
	})
	const forgeLike = ["forge", "neoforge"]
	forgeLike.forEach((modLoader) => {
		Object.keys(tomlData?.[modLoader]).forEach((key) => {
			if (key !== "deps") {
				const version = tomlData[modLoader][key].deps["minecraft"]
				const type = modLoader === "forge" ? "lexforge" : "neoforge"
				// Skip 26.3 until there's a version-runtime-test release for it
				if (version === "26.3") return;
				const v = {
					modId,
					modVersion,
					version,
					modLoader,
					type,
					java: getJavaVersion(version),
					regex: `.*${modLoader}.*`,
					fabricApi: "none",
				}
				outputData.push(v)
			}
		})
	})
	if (debug) {
		console.log("Matrix data:")
		console.log(outputData)
	}
	setOutput('matrix', JSON.stringify(outputData));
}

(function (){
  try {
    main();
  } catch (error) {
    setFailed(error.message);
  }
})();
