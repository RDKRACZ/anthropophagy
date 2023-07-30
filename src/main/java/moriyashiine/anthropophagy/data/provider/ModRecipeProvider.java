package moriyashiine.anthropophagy.data.provider;

import moriyashiine.anthropophagy.common.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
	public ModRecipeProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.WOODEN_KNIFE).input('I', ItemTags.PLANKS).input('S', Items.STICK).pattern(" I").pattern("S ").criterion("has_stick", VanillaRecipeProvider.conditionsFromItem(Items.STICK)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STONE_KNIFE).input('I', ItemTags.STONE_TOOL_MATERIALS).input('S', Items.STICK).pattern(" I").pattern("S ").criterion("has_stone", VanillaRecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.IRON_KNIFE).input('I', ConventionalItemTags.IRON_INGOTS).input('S', Items.STICK).pattern(" I").pattern("S ").criterion("has_iron_ingot", VanillaRecipeProvider.conditionsFromTag(ConventionalItemTags.IRON_INGOTS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GOLDEN_KNIFE).input('I', ConventionalItemTags.GOLD_INGOTS).input('S', Items.STICK).pattern(" I").pattern("S ").criterion("has_gold_ingot", VanillaRecipeProvider.conditionsFromTag(ConventionalItemTags.GOLD_INGOTS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DIAMOND_KNIFE).input('I', ConventionalItemTags.DIAMONDS).input('S', Items.STICK).pattern(" I").pattern("S ").criterion("has_diamond", VanillaRecipeProvider.conditionsFromTag(ConventionalItemTags.DIAMONDS)).offerTo(exporter);
		RecipeProvider.offerNetheriteUpgradeRecipe(exporter, ModItems.DIAMOND_KNIFE, RecipeCategory.COMBAT, ModItems.NETHERITE_KNIFE);
		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.TETHERED_HEART).input('E', Items.ENDER_PEARL).input('I', ConventionalItemTags.IRON_INGOTS).input('H', ModItems.PIGLUTTON_HEART).pattern("EIE").pattern("IHI").pattern("EIE").criterion("has_piglutton_heart", VanillaRecipeProvider.conditionsFromItem(ModItems.PIGLUTTON_HEART)).offerTo(exporter);
		CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(ModItems.FLESH), RecipeCategory.FOOD, ModItems.COOKED_FLESH, 0.35F, 200).criterion("has_flesh", VanillaRecipeProvider.conditionsFromItem(ModItems.FLESH)).offerTo(exporter);
		RecipeProvider.offerFoodCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, 100, ModItems.FLESH, ModItems.COOKED_FLESH, 0.35F);
		RecipeProvider.offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, 600, ModItems.FLESH, ModItems.COOKED_FLESH, 0.35F);
	}
}
