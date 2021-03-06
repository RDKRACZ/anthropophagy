package moriyashiine.anthropophagy.mixin;

import com.google.gson.JsonElement;
import moriyashiine.anthropophagy.common.Anthropophagy;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
	@Inject(method = "apply", at = @At("HEAD"))
	private void apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo callbackInfo) {
		if (Anthropophagy.SILVER_KNIFE_SMITHING_RECIPE != null) {
			map.put(new Identifier(Anthropophagy.MODID, "silver_knife_smithing"), Anthropophagy.SILVER_KNIFE_SMITHING_RECIPE);
		}
	}
}
