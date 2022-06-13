/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.ModConfig;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class ModEntityTypes {
	public static final EntityType<PigluttonEntity> PIGLUTTON = FabricEntityTypeBuilder.createMob().spawnGroup(SpawnGroup.MONSTER).entityFactory(PigluttonEntity::new).defaultAttributes(PigluttonEntity::createAttributes).dimensions(EntityDimensions.fixed(1, 1.75F)).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE, PigluttonEntity::canSpawn).build();

	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, new Identifier(Anthropophagy.MOD_ID, "piglutton"), PIGLUTTON);
		if (ModConfig.enablePiglutton) {
			BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_FOREST), PIGLUTTON.getSpawnGroup(), PIGLUTTON, 1, 1, 1);
		}
	}
}
