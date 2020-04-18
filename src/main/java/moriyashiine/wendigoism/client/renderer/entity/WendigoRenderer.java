package moriyashiine.wendigoism.client.renderer.entity;

import moriyashiine.wendigoism.Wendigoism;
import moriyashiine.wendigoism.client.model.entity.WendigoModel;
import moriyashiine.wendigoism.common.entity.WendigoEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/** File created by mason on 4/18/20 **/
@OnlyIn(Dist.CLIENT)
public class WendigoRenderer extends MobRenderer<WendigoEntity, WendigoModel<WendigoEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Wendigoism.MODID, "textures/entity/living/wendigo.png");
	
	public WendigoRenderer(EntityRendererManager manager) {
		super(manager, new WendigoModel<>(), 0.3f);
	}
	
	@Override
	@Nonnull
	public ResourceLocation getEntityTexture(@Nonnull WendigoEntity entity) {
		return TEXTURE;
	}
}