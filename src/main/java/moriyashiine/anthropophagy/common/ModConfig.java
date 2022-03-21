/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Anthropophagy.MOD_ID)
public class ModConfig implements ConfigData {
	@ConfigEntry.Gui.RequiresRestart
	public boolean enablePiglutton = true;
	@ConfigEntry.Gui.RequiresRestart
	public boolean strongerPiglutton = false;

	public int damageNeededForGuaranteedFleshDrop = 8;
}
