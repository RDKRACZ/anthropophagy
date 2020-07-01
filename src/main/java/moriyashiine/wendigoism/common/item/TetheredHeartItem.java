package moriyashiine.wendigoism.common.item;

import moriyashiine.wendigoism.Wendigoism;
import moriyashiine.wendigoism.common.misc.WDDataTrackers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TetheredHeartItem extends Item {
	public TetheredHeartItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		TypedActionResult<ItemStack> result;
		ItemStack stack = user.getStackInHand(hand);
		boolean tethered = WDDataTrackers.getTethered(user);
		if (!tethered) {
			WDDataTrackers.setTethered(user, true);
			if (!world.isClient) {
				user.sendMessage(new TranslatableText("message." + Wendigoism.MODID + ".tether"), false);
			}
			stack.decrement(1);
			result = new TypedActionResult<>(ActionResult.SUCCESS, stack);
		}
		else {
			if (!world.isClient) {
				user.sendMessage(new TranslatableText("message." + Wendigoism.MODID + ".tethered"), false);
			}
			result = new TypedActionResult<>(ActionResult.FAIL, stack);
		}
		return result;
	}
}