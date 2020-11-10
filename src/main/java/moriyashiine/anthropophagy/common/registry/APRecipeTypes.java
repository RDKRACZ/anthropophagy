package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.recipe.FleshDropRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class APRecipeTypes {
	private static final Map<RecipeSerializer<?>, Identifier> RECIPE_SERIALIZERS = new LinkedHashMap<>();
	private static final Map<DummyRecipeType<?>, Identifier> RECIPE_TYPES = new LinkedHashMap<>();
	
	public static final RecipeSerializer<FleshDropRecipe> flesh_drop_serializer = create("flesh_drop", new FleshDropRecipe.Serializer());
	public static final DummyRecipeType<FleshDropRecipe> flesh_drop_type = create("flesh_drop");
	
	private static <T extends Recipe<?>> DummyRecipeType<T> create(String name) {
		DummyRecipeType<T> type = new DummyRecipeType<>();
		RECIPE_TYPES.put(type, new Identifier(Anthropophagy.MODID, name));
		return type;
	}
	
	private static <T extends Recipe<?>> RecipeSerializer<T> create(String name, RecipeSerializer<T> serializer) {
		RECIPE_SERIALIZERS.put(serializer, new Identifier(Anthropophagy.MODID, name));
		return serializer;
	}
	
	public static void init() {
		RECIPE_TYPES.keySet().forEach(type -> Registry.register(Registry.RECIPE_TYPE, RECIPE_TYPES.get(type), type));
		RECIPE_SERIALIZERS.keySet().forEach(serializer -> Registry.register(Registry.RECIPE_SERIALIZER, RECIPE_SERIALIZERS.get(serializer), serializer));
	}
	
	private static class DummyRecipeType<T extends Recipe<?>> implements RecipeType<T> {
		@Override
		public String toString() {
			return Objects.requireNonNull(Registry.RECIPE_TYPE.getKey(this)).toString();
		}
	}
}
