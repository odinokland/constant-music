package com.odinokland.constantmusic.gametest.provider;

//? >= 1.21.5 && forge {
/*import com.google.gson.JsonObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import com.odinokland.constantmusic.gametest.GametestConstants;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class GametestInstanceProvider implements DataProvider {
	private final PackOutput.PathProvider pathProvider;
	private final Collection<String> testKeys;

	public GametestInstanceProvider(PackOutput output, Collection<String> testKeys) {
		this.pathProvider = output.createRegistryElementsPathProvider(Registries.TEST_INSTANCE);
		this.testKeys = testKeys;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		return CompletableFuture.allOf(
				this.testKeys.stream()
						.map(name -> {
							return DataProvider.saveStable(cache, buildTestInstance(name),
									pathProvider.json(ResourceLocation.fromNamespaceAndPath(GametestConstants.MOD_ID, name)));
						})
						.toArray(CompletableFuture[]::new)
		);
	}

	private JsonObject buildTestInstance(String name) {
		JsonObject json = new JsonObject();
		json.addProperty("type", "minecraft:function");
		json.addProperty("environment", "minecraft:default");
		json.addProperty("function", GametestConstants.MOD_ID + ":" + name);
		json.addProperty("structure", "minecraft:empty");
		json.addProperty("max_ticks", 400);
		json.addProperty("setup_ticks", 50);
		json.addProperty("required", true);
		return json;
	}

	@Override
	public String getName() {
		return "Gametest Instance Provider";
	}
}
*///? }
