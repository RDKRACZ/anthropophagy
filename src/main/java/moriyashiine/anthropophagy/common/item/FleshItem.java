/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class FleshItem extends Item {
	public FleshItem(Settings settings) {
		super(settings);
	}

	@Override
	public Text getName(ItemStack stack) {
		Text name = super.getName(stack);
		if (stack.hasNbt()) {
			String ownerName = stack.getNbt().getString("OwnerName");
			if (!ownerName.isEmpty()) {
				name = Text.translatable(getTranslationKey(stack) + "_owned", ownerName);
			}
		}
		return name;
	}
}
