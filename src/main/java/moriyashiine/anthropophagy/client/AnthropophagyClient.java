/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.client;

import moriyashiine.anthropophagy.client.model.entity.living.PigluttonEntityModel;
import moriyashiine.anthropophagy.client.renderer.entity.living.PigluttonEntityRenderer;
import moriyashiine.anthropophagy.common.registry.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class AnthropophagyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(PigluttonEntityModel.MODEL_LAYER, PigluttonEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(ModEntityTypes.PIGLUTTON, PigluttonEntityRenderer::new);
	}
}
