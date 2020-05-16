package moriyashiine.wendigoism;

import moriyashiine.wendigoism.proxy.ClientProxy;
import moriyashiine.wendigoism.proxy.ServerProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Wendigoism.MODID)
public class Wendigoism {
	public static final String MODID = "wendigoism";
	
	public static final ServerProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
	
	public Wendigoism() {
		proxy.registerListeners(FMLJavaModLoadingContext.get().getModEventBus());
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WDConfig.SPEC);
	}
}
