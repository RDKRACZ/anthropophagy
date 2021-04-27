package moriyashiine.anthropophagy.common;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import moriyashiine.anthropophagy.api.accessor.CannibalAccessor;
import moriyashiine.anthropophagy.common.registry.APEntityTypes;
import moriyashiine.anthropophagy.common.registry.APItems;
import moriyashiine.anthropophagy.common.registry.APRecipeTypes;
import moriyashiine.anthropophagy.common.registry.APSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public class Anthropophagy implements ModInitializer {
	public static final String MODID = "anthropophagy";
	
	public static APConfig config;
	
	@Override
	public void onInitialize() {
		AutoConfig.register(APConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(APConfig.class).getConfig();
		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			if (alive) {
				((CannibalAccessor) newPlayer).setTethered(((CannibalAccessor) oldPlayer).getTethered());
				((CannibalAccessor) newPlayer).setCannibalLevel(((CannibalAccessor) oldPlayer).getCannibalLevel());
				((CannibalAccessor) newPlayer).setHungerTimer(((CannibalAccessor) oldPlayer).getHungerTimer());
			}
		});
		APItems.init();
		APEntityTypes.init();
		APRecipeTypes.init();
		APSoundEvents.init();
	}
}
