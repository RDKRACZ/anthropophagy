package moriyashiine.wendigoism.common.registry;

import moriyashiine.wendigoism.common.WDConfig;
import moriyashiine.wendigoism.common.Wendigoism;
import moriyashiine.wendigoism.common.entity.WendigoEntity;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.LinkedHashMap;
import java.util.Map;

public class WDEntityTypes {
	public static final Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> ATTRIBUTES = new LinkedHashMap<>();
	
	public static final EntityType<WendigoEntity> WENDIGO = create(WendigoEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WendigoEntity::new).dimensions(EntityDimensions.fixed(1, 2.8f)).trackable(10, 1).build());
	
	private static <T extends LivingEntity> EntityType<T> create(DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
		FabricDefaultAttributeRegistry.register(type, attributes);
		return type;
	}
	
	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, new Identifier(Wendigoism.MODID, "wendigo"), WENDIGO);
		if (WDConfig.INSTANCE.enableWendigo) {
			Biome.SpawnEntry entry = new Biome.SpawnEntry(WENDIGO, 1, 1, 1);
			for (Biome biome : Registry.BIOME) {
				if (biome.getCategory() == Biome.Category.TAIGA) {
					addEntitySpawn(biome, WENDIGO.getSpawnGroup(), entry);
				}
			}
			RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> {
				if (biome.getCategory() == Biome.Category.TAIGA) {
					addEntitySpawn(biome, WENDIGO.getSpawnGroup(), entry);
				}
			});
		}
	}
	
	private static void addEntitySpawn(Biome biome, SpawnGroup group, Biome.SpawnEntry entry) {
		biome.getEntitySpawnList(group).add(entry);
	}
}