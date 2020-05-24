package moriyashiine.wendigoism;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class WDConfig {
	public static final WDConfig INSTANCE;
	static final ForgeConfigSpec SPEC;
	
	static {
		final Pair<WDConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(WDConfig::new);
		INSTANCE = specPair.getLeft();
		SPEC = specPair.getRight();
	}
	
	public final ForgeConfigSpec.ConfigValue<Boolean> enableWendigo;
	public final ForgeConfigSpec.ConfigValue<Integer> damagedNeeded;
	public final ForgeConfigSpec.ConfigValue<List<String>> dropMap;
	public final ForgeConfigSpec.ConfigValue<Boolean> strongerWendigo;
	
	private WDConfig(ForgeConfigSpec.Builder builder) {
		enableWendigo = builder.comment("Whether or not the Wendigo should spawn by any means").define("enableWendigo", true);
		damagedNeeded = builder.comment("The amount of damage needed to get a guaranteed flesh drop").define("damageNeeded", 8);
		dropMap = builder.comment("The map for Entities -> Items when right clicked with a knife. Format: EntityType/RegularDrop/FireDrop").define("dropMap", Arrays.asList("minecraft:player/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:witch/wendigoism:corrupt_flesh/wendigoism:corrupt_flesh", "minecraft:villager/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:wandering_trader/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:pillager/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:vindicator/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:evoker/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:illusioner/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:pig/minecraft:porkchop/minecraft:cooked_porkchop", "minecraft:cow/minecraft:beef/minecraft:cooked_beef", "minecraft:mooshroom/minecraft:beef/minecraft:cooked_beef", "minecraft:sheep/minecraft:mutton/minecraft:cooked_mutton", "minecraft:chicken/minecraft:chicken/minecraft:cooked_chicken", "minecraft:rabbit/minecraft:rabbit/minecraft:cooked_rabbit"));
		strongerWendigo = builder.comment("Whether or not the Wendigo will have 120 health instead of 60 and deal 12 damage instead of 6").define("strongerWendigo", false);
	}
}
