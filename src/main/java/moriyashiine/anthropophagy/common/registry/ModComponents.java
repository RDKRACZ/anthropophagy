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
import moriyashiine.anthropophagy.common.component.entity.CannibalComponent;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {
	public static final ComponentKey<CannibalComponent> CANNIBAL_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Anthropophagy.MOD_ID, "cannibal"), CannibalComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(CANNIBAL_COMPONENT, CannibalComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
