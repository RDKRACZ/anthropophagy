/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common.recipe;

import com.google.gson.JsonObject;
import moriyashiine.anthropophagy.common.registry.ModRecipeTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class FleshDropRecipe implements Recipe<Inventory> {
	public final Identifier id;
	public final EntityType<?> entity_type;
	public final Item raw_drop;
	public final Item cooked_drop;

	public FleshDropRecipe(Identifier id, EntityType<?> entity_type, Item raw_drop, Item cooked_drop) {
		this.id = id;
		this.entity_type = entity_type;
		this.raw_drop = raw_drop;
		this.cooked_drop = cooked_drop;
	}

	@Override
	public ItemStack craft(Inventory inv) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean matches(Inventory inv, World world) {
		return false;
	}

	@Override
	public boolean fits(int width, int height) {
		return false;
	}

	@Override
	public Identifier getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeTypes.FLESH_DROP_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.FLESH_DROP_RECIPE_TYPE;
	}

	public static class Serializer implements RecipeSerializer<FleshDropRecipe> {
		@Override
		public FleshDropRecipe read(Identifier id, JsonObject json) {
			return new FleshDropRecipe(id, Registry.ENTITY_TYPE.get(new Identifier(JsonHelper.getString(json, "entity_type"))), JsonHelper.getItem(json, "raw_drop"), JsonHelper.getItem(json, "cooked_drop"));
		}

		@Override
		public FleshDropRecipe read(Identifier id, PacketByteBuf buf) {
			return new FleshDropRecipe(id, Registry.ENTITY_TYPE.get(new Identifier(buf.readString())), Registry.ITEM.get(new Identifier(buf.readString())), Registry.ITEM.get(new Identifier(buf.readString())));
		}

		@Override
		public void write(PacketByteBuf buf, FleshDropRecipe recipe) {
			buf.writeString(Registry.ENTITY_TYPE.getId(recipe.entity_type).toString());
			buf.writeString(Registry.ITEM.getId(recipe.raw_drop).toString());
			buf.writeString(Registry.ITEM.getId(recipe.cooked_drop).toString());
		}
	}
}
