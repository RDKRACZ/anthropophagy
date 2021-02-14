package moriyashiine.anthropophagy.common;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Anthropophagy.MODID)
public class APConfig implements ConfigData {
	public boolean enablePiglutton = true;
	
	public int damageNeededForGuaranteedFleshDrop = 8;
	
	public boolean strongerPiglutton = false;
}
