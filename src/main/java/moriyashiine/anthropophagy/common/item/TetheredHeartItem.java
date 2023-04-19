/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.common.item;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.component.entity.TetheredComponent;
import moriyashiine.anthropophagy.common.registry.ModEntityComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
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
		TetheredComponent tetheredComponent = user.getComponent(ModEntityComponents.TETHERED);
		if (!tetheredComponent.isTethered()) {
			tetheredComponent.setTethered(true);
			user.incrementStat(Stats.USED.getOrCreateStat(this));
			if (!user.isCreative()) {
				stack.decrement(1);
			}
			user.sendMessage(Text.translatable(Anthropophagy.MOD_ID + ".message.tether"), true);
		} else {
			user.sendMessage(Text.translatable(Anthropophagy.MOD_ID + ".message.tethered"), true);
		}
		return TypedActionResult.success(stack);
	}
}
