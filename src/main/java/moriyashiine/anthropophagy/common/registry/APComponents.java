package moriyashiine.anthropophagy.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import moriyashiine.anthropophagy.api.component.CannibalComponent;
import moriyashiine.anthropophagy.common.Anthropophagy;
import net.minecraft.util.Identifier;

public class APComponents implements EntityComponentInitializer {
	public static final ComponentKey<CannibalComponent> CANNIBAL_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(Anthropophagy.MODID, "cannibal"), CannibalComponent.class);
	
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(CANNIBAL_COMPONENT, CannibalComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
