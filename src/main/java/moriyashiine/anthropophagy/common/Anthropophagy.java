package moriyashiine.anthropophagy.common;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import moriyashiine.anthropophagy.common.registry.APEntityTypes;
import moriyashiine.anthropophagy.common.registry.APItems;
import moriyashiine.anthropophagy.common.registry.APRecipeTypes;
import net.fabricmc.api.ModInitializer;

public class Anthropophagy implements ModInitializer {
	public static final String MODID = "anthropophagy";
	
	public static APConfig CONFIG;
	
	@Override
	public void onInitialize() {
		AutoConfig.register(APConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(APConfig.class).getConfig();
		APItems.init();
		APEntityTypes.init();
		APRecipeTypes.init();
	}
}
