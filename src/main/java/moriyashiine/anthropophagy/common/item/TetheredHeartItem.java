/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common.item;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.component.entity.TetheredComponent;
import moriyashiine.anthropophagy.common.registry.ModComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TetheredHeartItem extends Item {
	public TetheredHeartItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		if (!world.isClient) {
			TetheredComponent tetheredComponent = ModComponents.TETHERED.get(user);
			if (!tetheredComponent.isTethered()) {
				tetheredComponent.setTethered(true);
				stack.decrement(1);
				user.sendMessage(new TranslatableText(Anthropophagy.MOD_ID + ".message.tether"), true);
			} else {
				user.sendMessage(new TranslatableText(Anthropophagy.MOD_ID + ".message.tethered"), true);
			}
		}
		return TypedActionResult.success(stack);
	}
}
