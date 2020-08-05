package moriyashiine.wendigoism.common;

import blue.endless.jankson.Comment;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;

@ConfigFile(name = Wendigoism.MODID)
public class WDConfig {
	@Comment("Whether or not the Wendigo should spawn by any means")
	public boolean enableWendigo = true;
	
	@Comment("The amount of damage needed to get a guaranteed flesh drop")
	public int damageNeeded = 8;
	
	@Comment("Whether or not the Wendigo will have 120 health instead of 60 and deal 12 damage instead of 6")
	public boolean strongerWendigo = false;
}