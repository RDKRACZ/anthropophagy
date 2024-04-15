/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.anthropophagy.common.item.FleshItem;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
	@WrapOperation(method = "canAcceptRecipeOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;areItemsEqual(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z"))
	private static boolean anthropophagy$compareFlesh(ItemStack left, ItemStack right, Operation<Boolean> original, DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots) {
		boolean allow = original.call(left, right);
		if (allow) {
			ItemStack toCook = slots.get(0);
			if (toCook.getItem() instanceof FleshItem && !FleshItem.getOwnerName(toCook).equals(FleshItem.getOwnerName(left))) {
				return false;
			}
		}
		return allow;
	}

	@ModifyVariable(method = "craftRecipe", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/recipe/Recipe;getOutput(Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;"), ordinal = 1)
	private static ItemStack anthropophagy$persistFleshOwner(ItemStack value, DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots) {
		if (value.getItem() instanceof FleshItem) {
			ItemStack toCook = slots.get(0);
			if (toCook.getItem() instanceof FleshItem) {
				value = value.copy();
				FleshItem.setOwner(value, FleshItem.getOwnerName(toCook), FleshItem.isOwnerPlayer(toCook));
			}
		}
		return value;
	}
}
