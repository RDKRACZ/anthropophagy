/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
	@Entry
	public static boolean enablePiglutton = true;
	@Entry
	public static boolean strongerPiglutton = false;

	@Entry
	public static int damageNeededForGuaranteedFleshDrop = 8;
}
