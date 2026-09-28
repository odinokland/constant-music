import semver from "semver";
import { parse } from 'smol-toml'

const fs = require('fs');
const path = require('path');
const core = require('@actions/core');

/**
 * The main function for the action.
 *
 * @returns {Promise<void>} Resolves when the action is complete.
 */
export async function run() {
	try {
		const workspace = process.env.GITHUB_WORKSPACE;
		if (!workspace) {
			throw new Error("GITHUB_WORKSPACE is not defined. Ensure actions/checkout was run.");
		}

		const relativeFilePath = core.getInput('stonecutter.properties.toml');
		const filePath = path.resolve(workspace, relativeFilePath);

		if (!fs.existsSync(filePath)) {
			throw new Error(`File not found at: ${filePath}`);
		}

		const fileContent = fs.readFileSync(filePath, 'utf8');
		const tomlData = parse(fileContent)

		// 4. Use or log the content
		console.log(`Successfully read file content:\n${fileContent}`);

		const getJavaVersion = (ver) => {
			const mcVersion = semver.coerce(ver, { loose: true })
			if (semver.gte(mcVersion, "26.0.0", { loose: true })) {
				return 25
			}
			if (semver.gte(mcVersion, "1.20.5", { loose: true })) {
				return 21
			}
			if (semver.gte(mcVersion, "1.18.0", { loose: true })) {
				return 17
			}
			if (semver.gte(mcVersion, "1.17.0", { loose: true })) {
				return 16
			}
			return 8
		}

		const outputData = []
		Object.keys(tomlData?.fabric).forEach((key) => {
			if (key !=="deps") {
				const fApi = tomlData.fabric[key].deps["fabricApi"]
				const mc = tomlData.fabric[key].deps["minecraft"]
				const v = {
					mc,
					modloader: "fabric",
					type: "fabric",
					java: getJavaVersion(mc),
					regex: ".*fabric.*",
					fabricApi: fApi,
				};
				outputData.push(v)
			}
		})
		const forgeLike = ["forge", "neoforge"]
		forgeLike.forEach((modloader) => {
			Object.keys(tomlData?.[modloader]).forEach((key) => {
				if (key !=="deps") {
					const mc = tomlData[modloader][key].deps["minecraft"]
					const type = modloader === "forge" ? "lexforge" : "neoforge"
					const v = {
						mc,
						modloader,
						type,
						java: getJavaVersion(mc),
						regex: `.*${modloader}.*`,
						fabricApi: "none",
					}
					outputData.push(v)
				}
			})
		})
		core.setOutput('versions', JSON.stringify(outputData));

	} catch (error) {
		core.setFailed(`Action failed with error: ${error.message}`);
	}
}
