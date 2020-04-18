package moriyashiine.wendigoism.common.registry;

import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.common.entity.WendigoEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** File created by mason on 4/18/20 **/
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class WDEntityTypes {
	public static final EntityType<WendigoEntity> wendigo = create("wendigo", WendigoEntity::new, EntityClassification.MONSTER, 1, 2.8f, false);
	
	private static <T extends Entity> EntityType<T> create(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, boolean immuneToFire) {
		EntityType.Builder<T> builder = EntityType.Builder.create(factory, classification).setTrackingRange(64).setUpdateInterval(1).size(width, height);
		if (immuneToFire) {
			builder.immuneToFire();
		}
		EntityType<T> type = builder.build(name);
		type.setRegistryName(name);
		return type;
	}
	
	@SubscribeEvent
	public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
		if (WDConfig.INSTANCE.isWendigoEnabled) {
			event.getRegistry().register(wendigo);
		}
	}
}