package moriyashiine.wendigoism;

import blue.endless.jankson.Comment;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;

import java.util.Arrays;
import java.util.List;

@ConfigFile(name = Wendigoism.MODID)
public class WDConfig {
	public static final WDConfig INSTANCE = new WDConfig();
	
	@Comment("Whether or not the Wendigo should spawn by any means")
	public boolean enableWendigo = true;
	
	@Comment("The list of biomes a Wendigo can spawn in")
	public List<String> wendigoBiomes = Arrays.asList("minecraft:taiga", "minecraft:taiga_hills", "minecraft:snowy_taiga", "minecraft:snowy_taiga_hills", "minecraft:giant_tree_taiga", "minecraft:giant_tree_taiga_hills", "minecraft:taiga_mountains", "minecraft:snowy_taiga_mountains", "minecraft:giant_spruce_taiga", "minecraft:giant_spruce_taiga_hills");
	
	@Comment("The amount of damage needed to get a guaranteed flesh drop")
	public int damageNeeded = 8;
	
	@Comment("The map for Entities -> Items when right clicked with a knife. Format: EntityType/RegularDrop/FireDrop")
	public List<String> dropMap = Arrays.asList("minecraft:player/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:villager/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:wandering_trader/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:witch/wendigoism:corrupt_flesh/wendigoism:corrupt_flesh", "minecraft:pillager/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:vindicator/wendigoism:flesh/wendigoism:cooked_flesh", "minecraft:evoker/wendigoism:corrupt_flesh/wendigoism:corrupt_flesh", "minecraft:illusioner/wendigoism:corrupt_flesh/wendigoism:corrupt_flesh", "minecraft:pig/minecraft:porkchop/minecraft:cooked_porkchop", "minecraft:cow/minecraft:beef/minecraft:cooked_beef", "minecraft:mooshroom/minecraft:beef/minecraft:cooked_beef", "minecraft:sheep/minecraft:mutton/minecraft:cooked_mutton", "minecraft:chicken/minecraft:chicken/minecraft:cooked_chicken", "minecraft:rabbit/minecraft:rabbit/minecraft:cooked_rabbit");
	
	@Comment("Whether or not the Wendigo will have 120 health instead of 60 and deal 12 damage instead of 6")
	public boolean strongerWendigo = false;
}