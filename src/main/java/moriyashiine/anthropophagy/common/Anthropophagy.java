/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common;

import com.google.gson.Gson;
import eu.midnightdust.lib.config.MidnightConfig;
import moriyashiine.anthropophagy.common.registry.ModEntityTypes;
import moriyashiine.anthropophagy.common.registry.ModItems;
import moriyashiine.anthropophagy.common.registry.ModSoundEvents;
import moriyashiine.anthropophagy.common.reloadlisteners.FleshDropsReloadListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class Anthropophagy implements ModInitializer {
	public static final String MOD_ID = "anthropophagy";

	@Override
	public void onInitialize() {
		MidnightConfig.init(MOD_ID, ModConfig.class);
		ModItems.init();
		ModEntityTypes.init();
		ModSoundEvents.init();
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new FleshDropsReloadListener(new Gson(), MOD_ID + "_flesh_drops"));
	}

	public static Identifier id(String value) {
		return new Identifier(MOD_ID, value);
	}
}
