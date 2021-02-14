package moriyashiine.anthropophagy.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@SuppressWarnings("ConstantConditions")
public class FleshItem extends Item {
	public FleshItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public Text getName(ItemStack stack) {
		Text fin = super.getName(stack);
		if (stack.hasTag()) {
			String name = stack.getTag().getString("OwnerName");
			if (!name.isEmpty()) {
				fin = new TranslatableText(getTranslationKey(stack) + "_owned", name);
			}
		}
		return fin;
	}
}
