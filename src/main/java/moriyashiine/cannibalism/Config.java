package moriyashiine.cannibalism;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class Config {
	public static final Config COMMON;
	static final ForgeConfigSpec COMMON_SPEC;
	
	static {
		final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
	final ForgeConfigSpec.ConfigValue<List<String>> dropMap;
	public final ForgeConfigSpec.ConfigValue<Integer> damagedNeeded;
	
	private Config(ForgeConfigSpec.Builder builder) {
		dropMap = builder.comment("The map for Entities -> Items when right clicked with a knife. Format: EntityType/RegularDrop/FireDrop").define("dropMap", Arrays.asList("minecraft:player/cannibalism:flesh/cannibalism:cooked_flesh", "minecraft:witch/cannibalism:corrupt_flesh/cannibalism:corrupt_flesh", "minecraft:villager/cannibalism:flesh/cannibalism:cooked_flesh", "minecraft:wandering_trader/cannibalism:flesh/cannibalism:cooked_flesh", "minecraft:pillager/cannibalism:flesh/cannibalism:cooked_flesh", "minecraft:vindicator/cannibalism:flesh/cannibalism:cooked_flesh", "minecraft:evoker/cannibalism:flesh/cannibalism:cooked_flesh", "minecraft:illusioner/cannibalism:flesh/cannibalism:cooked_flesh", "minecraft:pig/minecraft:porkchop/minecraft:cooked_porkchop", "minecraft:cow/minecraft:beef/minecraft:cooked_beef", "minecraft:mooshroom/minecraft:beef/minecraft:cooked_beef", "minecraft:sheep/minecraft:mutton/minecraft:cooked_mutton", "minecraft:chicken/minecraft:chicken/minecraft:cooked_chicken", "minecraft:rabbit/minecraft:rabbit/minecraft:cooked_rabbit"));
		damagedNeeded = builder.comment("The amount of damage needed to get a guaranteed flesh drop.").define("damageNeeded", 8);
	}
}