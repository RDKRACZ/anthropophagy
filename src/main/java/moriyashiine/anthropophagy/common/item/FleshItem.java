/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.common.item;

import moriyashiine.anthropophagy.common.Anthropophagy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

public class FleshItem extends Item {
	public FleshItem(Settings settings) {
		super(settings);
	}

	@Override
	public Text getName(ItemStack stack) {
		Text name = super.getName(stack);
		NbtCompound nbt = stack.getSubNbt(Anthropophagy.MOD_ID);
		if (nbt != null) {
			String ownerName = nbt.getString("OwnerName");
			if (!ownerName.isEmpty()) {
				return Text.translatable(getTranslationKey(stack) + "_owned", ownerName);
			}
		}
		return name;
	}
}
