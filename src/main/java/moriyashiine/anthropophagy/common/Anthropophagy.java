/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import moriyashiine.anthropophagy.common.registry.ModEntityTypes;
import moriyashiine.anthropophagy.common.registry.ModItems;
import moriyashiine.anthropophagy.common.registry.ModRecipeTypes;
import moriyashiine.anthropophagy.common.registry.ModSoundEvents;
import net.fabricmc.api.ModInitializer;

public class Anthropophagy implements ModInitializer {
	public static final String MOD_ID = "anthropophagy";

	public static ConfigHolder<ModConfig> config;

	@Override
	public void onInitialize() {
		ModItems.init();
		ModEntityTypes.init();
		ModRecipeTypes.init();
		ModSoundEvents.init();
	}

	public static ModConfig getConfig() {
		if (config == null) {
			AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
			config = AutoConfig.getConfigHolder(ModConfig.class);
		}
		return config.getConfig();
	}
}
