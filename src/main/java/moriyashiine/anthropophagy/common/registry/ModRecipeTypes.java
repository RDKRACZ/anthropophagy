package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.recipe.FleshDropRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipeTypes {
	public static final RecipeSerializer<FleshDropRecipe> FLESH_DROP_SERIALIZER = new FleshDropRecipe.Serializer();
	public static final RecipeType<FleshDropRecipe> FLESH_DROP_RECIPE_TYPE = new RecipeType<>() {
		@Override
		public String toString() {
			return "flesh_drop";
		}
	};
	
	public static void init() {
		Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Anthropophagy.MOD_ID, FLESH_DROP_RECIPE_TYPE.toString()), FLESH_DROP_SERIALIZER);
		Registry.register(Registry.RECIPE_TYPE, new Identifier(Anthropophagy.MOD_ID, FLESH_DROP_RECIPE_TYPE.toString()), FLESH_DROP_RECIPE_TYPE);
	}
}
