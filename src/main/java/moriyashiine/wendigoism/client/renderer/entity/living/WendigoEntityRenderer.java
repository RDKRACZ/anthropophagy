package moriyashiine.wendigoism.client.renderer.entity.living;

import moriyashiine.wendigoism.Wendigoism;
import moriyashiine.wendigoism.client.model.entity.living.WendigoEntityModel;
import moriyashiine.wendigoism.common.entity.WendigoEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WendigoEntityRenderer extends MobEntityRenderer<WendigoEntity, WendigoEntityModel<WendigoEntity>> {
	private static final Identifier TEXTURE = new Identifier(Wendigoism.MODID, "textures/entity/living/wendigo.png");
	
	public WendigoEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new WendigoEntityModel<>(), 0.3f);
	}
	
	@Override
	public Identifier getTexture(WendigoEntity entity) {
		return TEXTURE;
	}
}