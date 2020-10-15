package moriyashiine.wendigoism.common.item;

import moriyashiine.wendigoism.api.accessor.WendigoAccessor;
import moriyashiine.wendigoism.common.Wendigoism;
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
		WendigoAccessor wendigoAccessor = WendigoAccessor.of(user).orElse(null);
		if (wendigoAccessor != null) {
			ItemStack stack = user.getStackInHand(hand);
			if (!wendigoAccessor.getTethered()) {
				wendigoAccessor.setTethered(true);
				if (!world.isClient) {
					user.sendMessage(new TranslatableText("message." + Wendigoism.MODID + ".tether"), true);
				}
				stack.decrement(1);
				return new TypedActionResult<>(ActionResult.success(world.isClient), stack);
			}
			else {
				if (!world.isClient) {
					user.sendMessage(new TranslatableText("message." + Wendigoism.MODID + ".tethered"), true);
				}
				return new TypedActionResult<>(ActionResult.FAIL, stack);
			}
		}
		return super.use(world, user, hand);
	}
}
