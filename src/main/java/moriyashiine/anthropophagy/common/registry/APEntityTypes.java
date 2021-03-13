package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

public class APEntityTypes {
	public static final EntityType<PigluttonEntity> PIGLUTTON = create(PigluttonEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PigluttonEntity::new).dimensions(EntityDimensions.fixed(1, 1.75f)).trackRangeBlocks(10).trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());
	
	private static <T extends LivingEntity> EntityType<T> create(DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
		FabricDefaultAttributeRegistry.register(type, attributes);
		return type;
	}
	
	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, new Identifier(Anthropophagy.MODID, "piglutton"), PIGLUTTON);
		if (Anthropophagy.config.enablePiglutton) {
			BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld().and(BiomeSelectors.categories(Biome.Category.FOREST)), PIGLUTTON.getSpawnGroup(), PIGLUTTON, 1, 1, 1);
			SpawnRestrictionAccessor.callRegister(PIGLUTTON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE, (type, serverWorldAccess, spawnReason, pos, random) -> random.nextFloat() < 1 / 8f);
		}
	}
}
