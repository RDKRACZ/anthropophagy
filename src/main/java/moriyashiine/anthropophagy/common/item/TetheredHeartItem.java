package moriyashiine.anthropophagy.common.item;

import moriyashiine.anthropophagy.api.accessor.CannibalAccessor;
import moriyashiine.anthropophagy.common.Anthropophagy;
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
		CannibalAccessor cannibalAccessor = CannibalAccessor.of(user).orElse(null);
		if (cannibalAccessor != null) {
			ItemStack stack = user.getStackInHand(hand);
			if (!cannibalAccessor.getTethered()) {
				cannibalAccessor.setTethered(true);
				if (!world.isClient) {
					user.sendMessage(new TranslatableText("message." + Anthropophagy.MODID + ".tether"), true);
				}
				stack.decrement(1);
				return new TypedActionResult<>(ActionResult.success(world.isClient), stack);
			}
			else {
				if (!world.isClient) {
					user.sendMessage(new TranslatableText("message." + Anthropophagy.MODID + ".tethered"), true);
				}
				return new TypedActionResult<>(ActionResult.FAIL, stack);
			}
		}
		return super.use(world, user, hand);
	}
}
