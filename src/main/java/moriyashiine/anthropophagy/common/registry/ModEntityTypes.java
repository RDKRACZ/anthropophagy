/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

public class ModEntityTypes {
	public static final EntityType<PigluttonEntity> PIGLUTTON = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PigluttonEntity::new).dimensions(EntityDimensions.fixed(1, 1.75F)).build();

	public static void init() {
		FabricDefaultAttributeRegistry.register(PIGLUTTON, PigluttonEntity.createAttributes());
		Registry.register(Registry.ENTITY_TYPE, new Identifier(Anthropophagy.MOD_ID, "piglutton"), PIGLUTTON);
		if (Anthropophagy.config.enablePiglutton) {
			BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.FOREST), PIGLUTTON.getSpawnGroup(), PIGLUTTON, 1, 1, 1);
			SpawnRestrictionAccessor.callRegister(PIGLUTTON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE, PigluttonEntity::canSpawn);
		}
	}
}
