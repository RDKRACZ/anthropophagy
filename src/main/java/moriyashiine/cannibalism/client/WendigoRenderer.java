package moriyashiine.cannibalism.client;

import moriyashiine.cannibalism.Cannibalism;
import moriyashiine.cannibalism.common.entity.WendigoEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class WendigoRenderer extends MobRenderer<WendigoEntity, WendigoModel<WendigoEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Cannibalism.MODID, "textures/entity/wendigo.png");
	
	WendigoRenderer(EntityRendererManager manager) {
		super(manager, new WendigoModel<>(), 0.3f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull WendigoEntity entity) {
		return TEXTURE;
	}
}