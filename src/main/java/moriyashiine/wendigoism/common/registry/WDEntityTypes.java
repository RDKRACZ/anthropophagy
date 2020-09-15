package moriyashiine.wendigoism.common.registry;

import moriyashiine.wendigoism.common.Wendigoism;
import moriyashiine.wendigoism.common.entity.WendigoEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WDEntityTypes {
	public static final EntityType<WendigoEntity> WENDIGO = create(WendigoEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WendigoEntity::new).dimensions(EntityDimensions.fixed(1, 2.8f)).trackRangeBlocks(10).trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());
	
	private static <T extends LivingEntity> EntityType<T> create(DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
		FabricDefaultAttributeRegistry.register(type, attributes);
		return type;
	}
	
	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, new Identifier(Wendigoism.MODID, "wendigo"), WENDIGO);
		//		if (Wendigoism.CONFIG.enableWendigo) {
		//			SpawnSettings.SpawnEntry entry = new SpawnSettings.SpawnEntry(WENDIGO, 1, 1, 1);
		//			for (Biome biome : BuiltinRegistries.BIOME) {
		//				if (biome.getCategory() == Biome.Category.TAIGA) {
		//					biome.getSpawnSettings().getSpawnEntry(WENDIGO.getSpawnGroup()).add(entry);
		//				}
		//			}
		//			RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, identifier, biome) -> {
		//				if (biome.getCategory() == Biome.Category.TAIGA) {
		//					biome.getSpawnSettings().getSpawnEntry(WENDIGO.getSpawnGroup()).add(entry);
		//				}
		//			});
		//		}
	}
}
