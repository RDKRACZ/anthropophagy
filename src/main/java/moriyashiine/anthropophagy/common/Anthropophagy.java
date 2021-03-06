package moriyashiine.anthropophagy.common;

import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import moriyashiine.anthropophagy.common.registry.APEntityTypes;
import moriyashiine.anthropophagy.common.registry.APItems;
import moriyashiine.anthropophagy.common.registry.APRecipeTypes;
import moriyashiine.anthropophagy.common.registry.APSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Anthropophagy implements ModInitializer {
	public static final String MODID = "anthropophagy";
	
	public static APConfig config;
	
	public static boolean isBewitchmentLoaded;
	
	public static JsonObject SILVER_KNIFE_SMITHING_RECIPE = null;
	
	@Override
	public void onInitialize() {
		isBewitchmentLoaded = FabricLoader.getInstance().isModLoaded("bewitchment");
		AutoConfig.register(APConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(APConfig.class).getConfig();
		APItems.init();
		APEntityTypes.init();
		APRecipeTypes.init();
		APSoundEvents.init();
		if (isBewitchmentLoaded) {
			SILVER_KNIFE_SMITHING_RECIPE = createSilverKnifeRecipe();
		}
	}
	
	private static JsonObject createSilverKnifeRecipe() {
		JsonObject json = new JsonObject();
		json.addProperty("type", "minecraft:smithing");
		JsonObject base = new JsonObject();
		base.addProperty("item", MODID + ":iron_knife");
		json.add("base", base);
		JsonObject addition = new JsonObject();
		addition.addProperty("tag", "c:silver_ingots");
		json.add("addition", addition);
		JsonObject result = new JsonObject();
		result.addProperty("item", MODID + ":silver_knife");
		json.add("result", result);
		return json;
	}
}
