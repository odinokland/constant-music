plugins {
    id("dev.kikugie.stonecutter")

    id("net.neoforged.moddev") version "2.0.107" apply false
    id("fabric-loom") version "1.11-SNAPSHOT" apply false
	id("dev.kikugie.fletching-table") version "0.1.0-alpha.22" apply false
}

stonecutter active "1.21.8" /* [SC] DO NOT EDIT */

stonecutter {

}
//subprojects {
//	apply(plugin = "dev.kikugie.fletching-table")
//
////	project.extensions.configure<FletchingTableExtension>("fletchingTable") {
////		j52j.register("main") {
////			extension("json", "*.mixins.json5")
////		}
////	}
//}