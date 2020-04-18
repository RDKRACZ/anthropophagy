package moriyashiine.wendigoism;

import moriyashiine.wendigoism.proxy.ClientProxy;
import moriyashiine.wendigoism.proxy.ServerProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.io.File;
import java.nio.file.Files;

/** File created by mason on 4/18/20 **/
@Mod(Wendigoism.MODID)
public class Wendigoism {
	public static final String MODID = "wendigoism";
	
	public static ServerProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
	
	public Wendigoism() {
		proxy.registerListeners(FMLJavaModLoadingContext.get().getModEventBus());
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WDConfig.SPEC);
		File file = new File("config/wendigoism-common.toml");
		if (file.exists()) {
			try {
				for (String line : Files.readAllLines(file.toPath())) {
					if (line.equals("enableWendigo = false")) {
						WDConfig.INSTANCE.isWendigoEnabled = false;
						break;
					}
				}
			}
			catch (Exception ignored) {}
		}
	}
}