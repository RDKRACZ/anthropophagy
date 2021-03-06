package moriyashiine.anthropophagy.common;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import moriyashiine.anthropophagy.common.registry.APEntityTypes;
import moriyashiine.anthropophagy.common.registry.APItems;
import moriyashiine.anthropophagy.common.registry.APRecipeTypes;
import moriyashiine.anthropophagy.common.registry.APSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Anthropophagy implements ModInitializer {
	public static final String MODID = "anthropophagy";
	
	public static APConfig config;
	
	public static boolean isBewitchmentLoaded;
	
	@Override
	public void onInitialize() {
		AutoConfig.register(APConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(APConfig.class).getConfig();
		APItems.init();
		APEntityTypes.init();
		APRecipeTypes.init();
		APSoundEvents.init();
		isBewitchmentLoaded = FabricLoader.getInstance().isModLoaded("bewitchment");
	}
}
