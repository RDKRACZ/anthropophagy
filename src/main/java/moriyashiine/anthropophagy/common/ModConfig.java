package moriyashiine.anthropophagy.common;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Anthropophagy.MOD_ID)
public class ModConfig implements ConfigData {
	public boolean enablePiglutton = true;
	
	public int damageNeededForGuaranteedFleshDrop = 8;
	
	public boolean strongerPiglutton = false;
}
