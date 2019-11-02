package moriyashiine.cannibalism;

import moriyashiine.cannibalism.common.Handler;
import moriyashiine.cannibalism.common.capability.CannibalCapability;
import moriyashiine.cannibalism.common.entity.WendigoEntity;
import moriyashiine.cannibalism.common.item.FleshItem;
import moriyashiine.cannibalism.common.item.KnifeItem;
import moriyashiine.cannibalism.common.item.TetheredHeartItem;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Cannibalism.MODID)
public class Cannibalism {
	public static final String MODID = "cannibalism";
	
	public static ItemGroup group = new ItemGroup(MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(RegistryEvents.iron_knife);
		}
	};
	
	public Cannibalism() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		CannibalCapability.register();
		MinecraftForge.EVENT_BUS.register(new Handler());
		for (String string : Config.COMMON.dropMap.get()) {
			String[] parts = string.split("/");
			if (parts.length != 3) throw new IllegalArgumentException("Failed to parse " + string + ", there must be 2 '/' characters.");
			EntityType type = null;
			Item normal = null, fire = null;
			for (int i = 0; i < parts.length; i++) {
				String part = parts[i];
				if (i == 0) type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(part));
				else if (i == 1) normal = ForgeRegistries.ITEMS.getValue(new ResourceLocation(part));
				else fire = ForgeRegistries.ITEMS.getValue(new ResourceLocation(part));
			}
			KnifeItem.DROPS.add(new KnifeItem.DropEntry(type, normal, fire));
		}
		for (Biome biome : ForgeRegistries.BIOMES) if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.COLD)) biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(RegistryEvents.wendigo, 1, 1, 1));
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		static final Item iron_knife = new KnifeItem(ItemTier.IRON).setRegistryName("iron_knife");
		public static final Item corrupt_flesh = new FleshItem(3).setRegistryName("corrupt_flesh");
		public static final Item wendigo_heart = new Item(new Item.Properties().group(group).food(new Food.Builder().hunger(6).saturation(0.5f).build())).setRegistryName("wendigo_heart");
		public static final EntityType<WendigoEntity> wendigo = EntityType.Builder.create(WendigoEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).size(1, 3).build(MODID + ":wendigo");
		
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			event.getRegistry().registerAll(new KnifeItem(ItemTier.WOOD).setRegistryName("wooden_knife"), new KnifeItem(ItemTier.STONE).setRegistryName("stone_knife"), iron_knife, new KnifeItem(ItemTier.GOLD).setRegistryName("golden_knife"), new KnifeItem(ItemTier.DIAMOND).setRegistryName("diamond_knife"));
			event.getRegistry().registerAll(new FleshItem(3).setRegistryName("flesh"), new FleshItem(6).setRegistryName("cooked_flesh"), corrupt_flesh);
			event.getRegistry().register(wendigo_heart);
			event.getRegistry().register(new TetheredHeartItem().setRegistryName("tethered_heart"));
			event.getRegistry().register(new SpawnEggItem(wendigo, 0x7f3d00, 0xc4c4c4, new Item.Properties().group(group)).setRegistryName("wendigo_spawn_egg"));
		}
		
		@SubscribeEvent
		public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
			wendigo.setRegistryName("wendigo");
			event.getRegistry().register(wendigo);
		}
	}
}
