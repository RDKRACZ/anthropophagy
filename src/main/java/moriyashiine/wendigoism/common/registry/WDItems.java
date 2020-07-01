package moriyashiine.wendigoism.common.registry;

import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.Wendigoism;
import moriyashiine.wendigoism.common.item.FleshItem;
import moriyashiine.wendigoism.common.item.KnifeItem;
import moriyashiine.wendigoism.common.item.TetheredHeartItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WDItems {
	public static final ItemGroup group = FabricItemGroupBuilder.build(new Identifier(Wendigoism.MODID, Wendigoism.MODID), () -> new ItemStack(WDItems.iron_knife));
	
	public static final Item wooden_knife = new KnifeItem(ToolMaterials.WOOD, gen());
	public static final Item stone_knife = new KnifeItem(ToolMaterials.STONE, gen());
	public static final Item iron_knife = new KnifeItem(ToolMaterials.IRON, gen());
	public static final Item golden_knife = new KnifeItem(ToolMaterials.GOLD, gen());
	public static final Item diamond_knife = new KnifeItem(ToolMaterials.DIAMOND, gen());
	public static final Item netherite_knife = new KnifeItem(ToolMaterials.NETHERITE, gen());
	
	public static final Item flesh = new FleshItem(gen().food(FoodComponents.BEEF));
	public static final Item cooked_flesh = new FleshItem(gen().food(FoodComponents.COOKED_BEEF));
	public static final Item corrupt_flesh = new FleshItem(gen().food(FoodComponents.BEEF));
	
	public static final Item wendigo_heart = new FleshItem(gen().food(FoodComponents.COOKED_BEEF));
	public static final Item tethered_heart = new TetheredHeartItem(gen());
	public static final Item wendigo_spawn_egg = new SpawnEggItem(WDEntityTypes.wendigo, 0x7f3d00, 0xc4c4c4, gen());
	
	private static Item.Settings gen() {
		return new Item.Settings().group(group);
	}
	
	public static void init() {
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "wooden_knife"), wooden_knife);
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "stone_knife"), stone_knife);
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "iron_knife"), iron_knife);
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "golden_knife"), golden_knife);
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "diamond_knife"), diamond_knife);
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "netherite_knife"), netherite_knife);
		
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "flesh"), flesh);
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "cooked_flesh"), cooked_flesh);
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "corrupt_flesh"), corrupt_flesh);
		
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "wendigo_heart"), wendigo_heart);
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "tethered_heart"), tethered_heart);
		Registry.register(Registry.ITEM, new Identifier(Wendigoism.MODID, "wendigo_spawn_egg"), wendigo_spawn_egg);
		
		for (String string : WDConfig.INSTANCE.dropMap) {
			String[] parts = string.split("/");
			if (parts.length != 3) {
				throw new IllegalArgumentException("Failed to parse " + string + ", there must be 2 '/' characters.");
			}
			EntityType<?> type = null;
			Item normal = null, fire = null;
			for (int i = 0; i < parts.length; i++) {
				String part = parts[i];
				if (i == 0) {
					type = Registry.ENTITY_TYPE.get(new Identifier(part));
				}
				else if (i == 1) {
					normal = Registry.ITEM.get(new Identifier(part));
				}
				else {
					fire = Registry.ITEM.get(new Identifier(part));
				}
			}
			KnifeItem.DROPS.add(new KnifeItem.DropEntry(type, normal, fire));
		}
	}
}