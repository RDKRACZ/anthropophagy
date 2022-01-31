package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.item.FleshItem;
import moriyashiine.anthropophagy.common.item.KnifeItem;
import moriyashiine.anthropophagy.common.item.TetheredHeartItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(Anthropophagy.MOD_ID, Anthropophagy.MOD_ID), () -> new ItemStack(ModItems.IRON_KNIFE));

	public static final Item WOODEN_KNIFE = new KnifeItem(ToolMaterials.WOOD, settings());
	public static final Item STONE_KNIFE = new KnifeItem(ToolMaterials.STONE, settings());
	public static final Item GOLDEN_KNIFE = new KnifeItem(ToolMaterials.GOLD, settings());
	public static final Item IRON_KNIFE = new KnifeItem(ToolMaterials.IRON, settings());
	public static final Item DIAMOND_KNIFE = new KnifeItem(ToolMaterials.DIAMOND, settings());
	public static final Item NETHERITE_KNIFE = new KnifeItem(ToolMaterials.NETHERITE, settings().fireproof());

	public static final Item FLESH = new FleshItem(settings().food(FoodComponents.BEEF));
	public static final Item COOKED_FLESH = new FleshItem(settings().food(FoodComponents.COOKED_BEEF));
	public static final Item CORRUPT_FLESH = new FleshItem(settings().food(FoodComponents.BEEF));

	public static final Item PIGLUTTON_HEART = new FleshItem(settings().food(FoodComponents.COOKED_BEEF));
	public static final Item TETHERED_HEART = new TetheredHeartItem(settings());

	public static final Item PIGLUTTON_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.PIGLUTTON, 0x7F3D00, 0xC4C4C4, settings());

	private static Item.Settings settings() {
		return new FabricItemSettings().group(GROUP);
	}

	public static void init() {
		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "wooden_knife"), WOODEN_KNIFE);
		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "stone_knife"), STONE_KNIFE);
		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "golden_knife"), GOLDEN_KNIFE);
		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "iron_knife"), IRON_KNIFE);
		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "diamond_knife"), DIAMOND_KNIFE);
		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "netherite_knife"), NETHERITE_KNIFE);

		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "flesh"), FLESH);
		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "cooked_flesh"), COOKED_FLESH);
		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "corrupt_flesh"), CORRUPT_FLESH);

		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "piglutton_heart"), PIGLUTTON_HEART);
		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "tethered_heart"), TETHERED_HEART);

		Registry.register(Registry.ITEM, new Identifier(Anthropophagy.MOD_ID, "piglutton_spawn_egg"), PIGLUTTON_SPAWN_EGG);
	}
}
