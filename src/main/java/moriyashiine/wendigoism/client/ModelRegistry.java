package moriyashiine.wendigoism.client;

import moriyashiine.wendigoism.Wendigoism;
import moriyashiine.wendigoism.common.entity.WendigoEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Wendigoism.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelRegistry {
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(WendigoEntity.class, WendigoRenderer::new);
	}
}