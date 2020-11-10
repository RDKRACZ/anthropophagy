package moriyashiine.anthropophagy.common;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = Anthropophagy.MODID)
public class APConfig implements ConfigData {
	public boolean enablePiglutton = true;
	
	public int damageNeededForGuaranteedFleshDrop = 8;
	
	public boolean strongerPiglutton = false;
}
