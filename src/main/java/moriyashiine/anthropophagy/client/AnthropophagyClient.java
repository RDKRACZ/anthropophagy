package moriyashiine.anthropophagy.client;

import moriyashiine.anthropophagy.client.model.entity.living.PigluttonEntityModel;
import moriyashiine.anthropophagy.client.renderer.entity.living.PigluttonEntityRenderer;
import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.registry.APEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@SuppressWarnings("UnstableApiUsage")
@Environment(EnvType.CLIENT)
public class AnthropophagyClient implements ClientModInitializer {
	public static final EntityModelLayer PIGLUTTON_MODEL_LAYER = new EntityModelLayer(new Identifier(Anthropophagy.MODID, "piglutton"), "main");
	
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(PIGLUTTON_MODEL_LAYER, PigluttonEntityModel::getTexturedModelData);
		EntityRendererRegistry.INSTANCE.register(APEntityTypes.PIGLUTTON, PigluttonEntityRenderer::new);
	}
}
