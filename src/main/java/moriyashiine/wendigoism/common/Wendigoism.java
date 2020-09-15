package moriyashiine.wendigoism.common;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import moriyashiine.wendigoism.common.registry.WDEntityTypes;
import moriyashiine.wendigoism.common.registry.WDItems;
import moriyashiine.wendigoism.common.registry.WDRecipeTypes;
import net.fabricmc.api.ModInitializer;

public class Wendigoism implements ModInitializer {
	public static final String MODID = "wendigoism";
	
	public static WDConfig CONFIG;
	
	@Override
	public void onInitialize() {
		AutoConfig.register(WDConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(WDConfig.class).getConfig();
		WDItems.init();
		WDEntityTypes.init();
		WDRecipeTypes.init();
	}
}
