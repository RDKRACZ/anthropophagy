package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.item.FleshItem;
import moriyashiine.anthropophagy.common.item.KnifeItem;
import moriyashiine.anthropophagy.common.item.TetheredHeartItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class APItems {
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
	
	public static final ItemGroup group = FabricItemGroupBuilder.build(new Identifier(Anthropophagy.MODID, Anthropophagy.MODID), () -> new ItemStack(APItems.IRON_KNIFE));
	
	public static final Item WOODEN_KNIFE = create("wooden_knife", new KnifeItem(ToolMaterials.WOOD, gen()));
	public static final Item STONE_KNIFE = create("stone_knife", new KnifeItem(ToolMaterials.STONE, gen()));
	public static final Item GOLDEN_KNIFE = create("golden_knife", new KnifeItem(ToolMaterials.GOLD, gen()));
	public static final Item IRON_KNIFE = create("iron_knife", new KnifeItem(ToolMaterials.IRON, gen()));
	public static final Item DIAMOND_KNIFE = create("diamond_knife", new KnifeItem(ToolMaterials.DIAMOND, gen()));
	public static final Item NETHERITE_KNIFE = create("netherite_knife", new KnifeItem(ToolMaterials.NETHERITE, gen()));
	
	public static final Item FLESH = create("flesh", new FleshItem(gen().food(FoodComponents.BEEF)));
	public static final Item COOKED_FLESH = create("cooked_flesh", new FleshItem(gen().food(FoodComponents.COOKED_BEEF)));
	public static final Item CORRUPT_FLESH = create("corrupt_flesh", new FleshItem(gen().food(FoodComponents.BEEF)));
	
	public static final Item PIGLUTTON_HEART = create("piglutton_heart", new FleshItem(gen().food(FoodComponents.COOKED_BEEF)));
	public static final Item TETHERED_HEART = create("tethered_heart", new TetheredHeartItem(gen()));
	public static final Item PIGLUTTON_SPAWN_EGG = create("piglutton_spawn_egg", new SpawnEggItem(APEntityTypes.PIGLUTTON, 0x7f3d00, 0xc4c4c4, gen()));
	
	private static Item.Settings gen() {
		return new Item.Settings().group(group);
	}
	
	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, new Identifier(Anthropophagy.MODID, name));
		return item;
	}
	
	public static void init() {
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}
}
