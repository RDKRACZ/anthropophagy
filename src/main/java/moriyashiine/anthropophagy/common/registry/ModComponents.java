/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.component.entity.CannibalLevelComponent;
import moriyashiine.anthropophagy.common.component.entity.TetheredComponent;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {
	public static int playerCannibalLevel = -1;

	public static final ComponentKey<CannibalLevelComponent> CANNIBAL_LEVEL = ComponentRegistry.getOrCreate(new Identifier(Anthropophagy.MOD_ID, "cannibal_level"), CannibalLevelComponent.class);
	public static final ComponentKey<TetheredComponent> TETHERED = ComponentRegistry.getOrCreate(new Identifier(Anthropophagy.MOD_ID, "tethered"), TetheredComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(CANNIBAL_LEVEL, CannibalLevelComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(TETHERED, player -> new TetheredComponent(), RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
