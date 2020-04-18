package moriyashiine.wendigoism.proxy;

import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.client.renderer.entity.WendigoRenderer;
import moriyashiine.wendigoism.common.registry.WDEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/** File created by mason on 4/18/20 **/
@OnlyIn(Dist.CLIENT)
public class ClientProxy extends ServerProxy {
	@Override
	public void registerListeners(IEventBus bus) {
		super.registerListeners(bus);
		bus.addListener(this::clientSetup);
	}
	
	private void clientSetup(FMLClientSetupEvent event) {
		if (WDConfig.INSTANCE.isWendigoEnabled) {
			RenderingRegistry.registerEntityRenderingHandler(WDEntityTypes.wendigo, WendigoRenderer::new);
		}
	}
}