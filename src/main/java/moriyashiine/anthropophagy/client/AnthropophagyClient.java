package moriyashiine.anthropophagy.client;

import moriyashiine.anthropophagy.client.renderer.entity.living.PigluttonEntityRenderer;
import moriyashiine.anthropophagy.common.registry.APEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class AnthropophagyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(APEntityTypes.PIGLUTTON, ((entityRenderDispatcher, context) -> new PigluttonEntityRenderer(entityRenderDispatcher)));
	}
}
