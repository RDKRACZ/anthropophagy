/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.client.renderer.entity.living;

import moriyashiine.anthropophagy.client.model.entity.living.PigluttonEntityModel;
import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PigluttonEntityRenderer extends MobEntityRenderer<PigluttonEntity, PigluttonEntityModel<PigluttonEntity>> {
	private static final Identifier TEXTURE = Anthropophagy.id("textures/entity/living/piglutton.png");

	public PigluttonEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new PigluttonEntityModel<>(context.getPart(PigluttonEntityModel.MODEL_LAYER)), 0.5F);
	}

	@Override
	public Identifier getTexture(PigluttonEntity entity) {
		return TEXTURE;
	}
}
