package moriyashiine.wendigoism.common;

import io.github.cottonmc.cotton.config.ConfigManager;
import moriyashiine.wendigoism.common.registry.WDEntityTypes;
import moriyashiine.wendigoism.common.registry.WDItems;
import moriyashiine.wendigoism.common.registry.WDRecipeTypes;
import net.fabricmc.api.ModInitializer;

public class Wendigoism implements ModInitializer {
	public static final String MODID = "wendigoism";
	
	public static final WDConfig CONFIG = new WDConfig();
	
	@Override
	public void onInitialize() {
		ConfigManager.loadConfig(WDConfig.class);
		WDItems.init();
		WDEntityTypes.init();
		WDRecipeTypes.init();
	}
}