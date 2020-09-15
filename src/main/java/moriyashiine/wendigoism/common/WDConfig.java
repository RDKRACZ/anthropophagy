package moriyashiine.wendigoism.common;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = Wendigoism.MODID)
public class WDConfig implements ConfigData {
	public boolean enableWendigo = true;
	
	public int damageNeededForGuaranteedFleshDrop = 8;
	
	public boolean strongerWendigo = false;
}
