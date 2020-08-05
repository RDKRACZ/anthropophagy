package moriyashiine.wendigoism.common.registry;

import moriyashiine.wendigoism.common.Wendigoism;
import moriyashiine.wendigoism.common.item.FleshItem;
import moriyashiine.wendigoism.common.item.KnifeItem;
import moriyashiine.wendigoism.common.item.TetheredHeartItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class WDItems {
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
	
	public static final ItemGroup group = FabricItemGroupBuilder.build(new Identifier(Wendigoism.MODID, Wendigoism.MODID), () -> new ItemStack(WDItems.IRON_KNIFE));
	
	public static final Item WOODEN_KNIFE = create("wooden_knife", new KnifeItem(ToolMaterials.WOOD, gen()));
	public static final Item STONE_KNIFE = create("stone_knife", new KnifeItem(ToolMaterials.STONE, gen()));
	public static final Item IRON_KNIFE = create("iron_knife", new KnifeItem(ToolMaterials.IRON, gen()));
	public static final Item GOLDEN_KNIFE = create("golden_knife", new KnifeItem(ToolMaterials.GOLD, gen()));
	public static final Item DIAMOND_KNIFE = create("diamond_knife", new KnifeItem(ToolMaterials.DIAMOND, gen()));
	public static final Item NETHERITE_KNIFE = create("netherite_knife", new KnifeItem(ToolMaterials.NETHERITE, gen()));
	
	public static final Item FLESH = create("flesh", new FleshItem(gen().food(FoodComponents.BEEF)));
	public static final Item COOKED_FLESH = create("cooked_flesh", new FleshItem(gen().food(FoodComponents.COOKED_BEEF)));
	public static final Item CORRUPT_FLESH = create("corrupt_flesh", new FleshItem(gen().food(FoodComponents.BEEF)));
	
	public static final Item WENDIGO_HEART = create("wendigo_heart", new FleshItem(gen().food(FoodComponents.COOKED_BEEF)));
	public static final Item TETHERED_HEART = create("tethered_heart", new TetheredHeartItem(gen()));
	public static final Item WENDIGO_SPAWN_EGG = create("wendigo_spawn_egg", new SpawnEggItem(WDEntityTypes.WENDIGO, 0x7f3d00, 0xc4c4c4, gen()));
	
	private static Item.Settings gen() {
		return new Item.Settings().group(group);
	}
	
	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, new Identifier(Wendigoism.MODID, name));
		return item;
	}
	
	public static void init() {
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}
}
