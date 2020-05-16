package moriyashiine.wendigoism.proxy;

import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.Wendigoism;
import moriyashiine.wendigoism.common.capability.CannibalCapability;
import moriyashiine.wendigoism.common.handler.WDHandler;
import moriyashiine.wendigoism.common.item.KnifeItem;
import moriyashiine.wendigoism.common.registry.WDEntityTypes;
import moriyashiine.wendigoism.common.registry.WDItems;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Set;

public class ServerProxy {
	public ItemGroup group = new ItemGroup(Wendigoism.MODID) {
		@Override
		@Nonnull
		public ItemStack createIcon() {
			return new ItemStack(WDItems.iron_knife);
		}
	};
	
	public void registerListeners(IEventBus bus) {
		bus.addListener(this::setup);
	}
	
	private void setup(FMLCommonSetupEvent event) {
		CannibalCapability.register();
		MinecraftForge.EVENT_BUS.register(new WDHandler());
		for (String string : WDConfig.INSTANCE.dropMap.get()) {
			String[] parts = string.split("/");
			if (parts.length != 3) {
				throw new IllegalArgumentException("Failed to parse " + string + ", there must be 2 '/' characters.");
			}
			EntityType<?> type = null;
			Item normal = null, fire = null;
			for (int i = 0; i < parts.length; i++) {
				String part = parts[i];
				if (i == 0) {
					type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(part));
				}
				else if (i == 1) {
					normal = ForgeRegistries.ITEMS.getValue(new ResourceLocation(part));
				}
				else {
					fire = ForgeRegistries.ITEMS.getValue(new ResourceLocation(part));
				}
			}
			KnifeItem.DROPS.add(new KnifeItem.DropEntry(type, normal, fire));
		}
		if (WDConfig.INSTANCE.enableWendigo.get()) {
			for (Biome biome : ForgeRegistries.BIOMES) {
				Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);
				if (!biomeTypes.contains(BiomeDictionary.Type.NETHER) && !biomeTypes.contains(BiomeDictionary.Type.END) && biomeTypes.contains(BiomeDictionary.Type.COLD) && biomeTypes.contains(BiomeDictionary.Type.FOREST)) {
					biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(WDEntityTypes.wendigo, 1, 1, 1));
				}
			}
		}
	}
}