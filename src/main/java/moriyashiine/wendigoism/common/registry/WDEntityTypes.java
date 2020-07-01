package moriyashiine.wendigoism.common.registry;

import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.Wendigoism;
import moriyashiine.wendigoism.common.entity.WendigoEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class WDEntityTypes {
	public static final Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> ATTRIBUTES = new HashMap<>();
	
	public static final EntityType<WendigoEntity> wendigo = create(WendigoEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WendigoEntity::new).dimensions(EntityDimensions.fixed(1, 2.8f)).trackable(10, 1).build());
	
	private static <T extends LivingEntity> EntityType<T> create(DefaultAttributeContainer attributes, EntityType<T> type)
	{
		ATTRIBUTES.put(type, attributes);
		return type;
	}
	
	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, new Identifier(Wendigoism.MODID, "wendigo"), wendigo);
		if (WDConfig.INSTANCE.enableWendigo) {
			for (String biomeName : WDConfig.INSTANCE.wendigoBiomes) {
				Biome biome = Registry.BIOME.get(new Identifier(biomeName));
				if (biome != null) {
					biome.getEntitySpawnList(SpawnGroup.MONSTER).add(new Biome.SpawnEntry(wendigo, 1, 1, 1));
				}
			}
		}
	}
}