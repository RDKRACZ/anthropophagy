package moriyashiine.wendigoism.common.recipe;

import com.google.gson.JsonObject;
import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.Wendigoism;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class EnableTetheredHeartRecipe implements ICondition {
	private static final ResourceLocation ID = new ResourceLocation(Wendigoism.MODID, "enable_wendigo");
	
	private final boolean value;
	
	public EnableTetheredHeartRecipe(boolean value) {
		this.value = value;
	}
	
	@Override
	public ResourceLocation getID() {
		return ID;
	}
	
	@Override
	public boolean test() {
		return WDConfig.INSTANCE.isWendigoEnabled == value;
	}
	
	public static final IConditionSerializer<EnableTetheredHeartRecipe> SERIALIZER = new IConditionSerializer<EnableTetheredHeartRecipe>() {
		@Override
		public void write(JsonObject json, EnableTetheredHeartRecipe value) {
			json.addProperty("value", value.value);
		}
		
		@Override
		public EnableTetheredHeartRecipe read(JsonObject json) {
			return new EnableTetheredHeartRecipe(json.get("value").getAsBoolean());
		}
		
		@Override
		public ResourceLocation getID() {
			return ID;
		}
	};
}