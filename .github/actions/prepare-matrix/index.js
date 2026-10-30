import { setOutput, setFailed } from '@actions/core'
import fs from 'node:fs';
import path from "path";
import { parse } from "smol-toml";
import semver from "semver";

const main = () => {
  	const debug = process.env.DEBUG;
	const workspace = process.env.GITHUB_WORKSPACE;
	if (!workspace && !debug) {
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

	const buildMatrixData = []
	const testMatrixData = []
	Object.keys(tomlData?.fabric).forEach((key) => {
		if (key !== "deps") {
			const fApi = tomlData.fabric[key].deps["fabricApi"]
			const fGApi = tomlData.fabric[key].deps["fabricGametestApi"]
			const version = tomlData.fabric[key].deps["minecraft"]
			const additionalVersions = tomlData.fabric[key]?.publish?.additionalVersions
			const v = {
				modId,
				modVersion,
				version,
				runnerMcVersion: version,
				modLoader: "fabric",
				type: "fabric",
				java: getJavaVersion(version),
				regex: ".*fabric.*",
				fabricApi: fApi,
				fabricGametestApi: fGApi,
			};
			buildMatrixData.push(v)
			testMatrixData.push(v)
			if (additionalVersions) {
				Array.from(new Set(additionalVersions)).forEach(mcVersion => {
					const fabricApi = tomlData.additional[mcVersion]?.deps?.fabricApi || "none"
					const fabricGametestApi = tomlData.additional[mcVersion]?.deps?.fabricGametestApi || "none"
					const extraVersion = {
						modId,
						modVersion,
						version,
						runnerMcVersion: mcVersion,
						modLoader: "fabric",
						type: "fabric",
						java: getJavaVersion(mcVersion),
						regex: ".*fabric.*",
						fabricApi,
						fabricGametestApi,
					}
					testMatrixData.push(extraVersion)
				})
			}
		}
	})
	const forgeLike = ["forge", "neoforge"]
	forgeLike.forEach((modLoader) => {
		Object.keys(tomlData?.[modLoader]).forEach((key) => {
			if (key !== "deps") {
				const version = tomlData[modLoader][key].deps["minecraft"]
				const type = modLoader === "forge" ? "lexforge" : "neoforge"

				const additionalVersions = tomlData[modLoader][key]?.publish?.additionalVersions
				const v = {
					modId,
					modVersion,
					version,
					runnerMcVersion: version,
					modLoader,
					type,
					java: getJavaVersion(version),
					regex: `.*${modLoader}.*`,
					fabricApi: "none",
					fabricGametestApi: "none",
				}
				buildMatrixData.push(v)
				testMatrixData.push(v)
				if (additionalVersions) {
					Array.from(new Set(additionalVersions)).forEach(mcVersion => {
						testMatrixData.push({
							modId,
							modVersion,
							version,
							runnerMcVersion: mcVersion,
							modLoader,
							type,
							java: getJavaVersion(mcVersion),
							regex: `.*${modLoader}.*`,
							fabricApi: "none",
							fabricGametestApi: "none",
						})
					})
				}
			}
		})
	})
	if (debug) {
		console.log("Matrix data:")
		console.log(buildMatrixData)
	}
	setOutput('matrix', JSON.stringify(buildMatrixData));
	setOutput('test-matrix', JSON.stringify(testMatrixData));
}

(function (){
	console.log('executing')
  try {
    main();
  } catch (error) {
    setFailed(error.message);
  }
})();
