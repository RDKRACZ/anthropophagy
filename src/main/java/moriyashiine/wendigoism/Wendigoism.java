package moriyashiine.wendigoism;

import io.github.cottonmc.cotton.config.ConfigManager;
import moriyashiine.wendigoism.common.registry.WDEntityTypes;
import moriyashiine.wendigoism.common.registry.WDItems;
import net.fabricmc.api.ModInitializer;

public class Wendigoism implements ModInitializer {
	public static final String MODID = "wendigoism";
	
	@Override
	public void onInitialize() {
		ConfigManager.loadConfig(WDConfig.class);
		WDItems.init();
		WDEntityTypes.init();
	}
}
