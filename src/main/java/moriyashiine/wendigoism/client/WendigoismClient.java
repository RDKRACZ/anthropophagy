package moriyashiine.wendigoism.client;

import moriyashiine.wendigoism.client.renderer.entity.living.WendigoEntityRenderer;
import moriyashiine.wendigoism.common.registry.WDEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class WendigoismClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(WDEntityTypes.WENDIGO, ((entityRenderDispatcher, context) -> new WendigoEntityRenderer(entityRenderDispatcher)));
	}
}