package moriyashiine.wendigoism.common.registry;

import moriyashiine.wendigoism.Wendigoism;
import moriyashiine.wendigoism.common.item.FleshItem;
import moriyashiine.wendigoism.common.item.KnifeItem;
import moriyashiine.wendigoism.common.item.TetheredHeartItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

/** File created by mason on 4/18/20 **/
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class WDItems {
	//knife
	public static final Item wooden_knife = create("wooden_knife", new KnifeItem(ItemTier.WOOD, gen()));
	public static final Item stone_knife = create("stone_knife", new KnifeItem(ItemTier.STONE, gen()));
	public static final Item iron_knife = create("iron_knife", new KnifeItem(ItemTier.IRON, gen()));
	public static final Item golden_knife = create("golden_knife", new KnifeItem(ItemTier.GOLD, gen()));
	public static final Item diamond_knife = create("diamond_knife", new KnifeItem(ItemTier.DIAMOND, gen()));
	//flesh
	public static final Item flesh = create("flesh", new FleshItem(gen().food(new Food.Builder().hunger(3).saturation(0.5f).build())));
	public static final Item cooked_flesh = create("cooked_flesh", new FleshItem(gen().food(new Food.Builder().hunger(6).saturation(0.5f).build())));
	public static final Item corrupt_flesh = create("corrupt_flesh", new FleshItem(gen().food(new Food.Builder().hunger(3).saturation(0.5f).build())));
	//wendigo
	public static final Item wendigo_heart = create("wendigo_heart", new FleshItem(gen().food(new Food.Builder().hunger(3).saturation(0.5f).build())));
	public static final Item tethered_heart = create("tethered_heart", new TetheredHeartItem(gen()));
	public static final Item wendigo_spawn_egg = create("wendigo_spawn_egg", new SpawnEggItem(WDEntityTypes.wendigo, 0x7f3d00, 0xc4c4c4, gen()));
	
	private static Item.Properties gen() {
		return new Item.Properties().group(Wendigoism.proxy.group);
	}
	
	private static <T extends Item> T create(String name, T item) {
		item.setRegistryName(name);
		return item;
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		//knife
		registry.registerAll(wooden_knife, stone_knife, iron_knife, golden_knife, diamond_knife);
		//flesh
		registry.registerAll(flesh, cooked_flesh, corrupt_flesh);
		//wendigo
		registry.registerAll(wendigo_heart, tethered_heart, wendigo_spawn_egg);
	}
}