package moriyashiine.anthropophagy.common;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import moriyashiine.anthropophagy.api.component.CannibalComponent;
import moriyashiine.anthropophagy.common.registry.APEntityTypes;
import moriyashiine.anthropophagy.common.registry.APItems;
import moriyashiine.anthropophagy.common.registry.APRecipeTypes;
import moriyashiine.anthropophagy.common.registry.APSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Anthropophagy implements ModInitializer {
	public static final String MODID = "anthropophagy";
	
	public static APConfig config;
	
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(Anthropophagy.MODID, Anthropophagy.MODID), () -> new ItemStack(APItems.IRON_KNIFE));
	
	@Override
	public void onInitialize() {
		AutoConfig.register(APConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(APConfig.class).getConfig();
		APItems.init();
		APEntityTypes.init();
		APRecipeTypes.init();
		APSoundEvents.init();
		ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
			if (CannibalComponent.get(player).isTethered()) {
				player.dropStack(new ItemStack(APItems.PIGLUTTON_HEART));
			}
			return true;
		});
	}
}
