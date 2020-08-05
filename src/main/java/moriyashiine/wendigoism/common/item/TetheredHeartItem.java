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

import java.util.concurrent.atomic.AtomicReference;

public class TetheredHeartItem extends Item {
	public TetheredHeartItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		AtomicReference<TypedActionResult<ItemStack>> result = new AtomicReference<>(super.use(world, user, hand));
		ItemStack stack = user.getStackInHand(hand);
		WendigoAccessor.get(user).ifPresent(wendigoAccessor -> {
			if (!wendigoAccessor.getTethered()) {
				wendigoAccessor.setTethered(true);
				if (!world.isClient) {
					user.sendMessage(new TranslatableText("message." + Wendigoism.MODID + ".tether"), true);
				}
				stack.decrement(1);
				result.set(new TypedActionResult<>(ActionResult.success(world.isClient), stack));
			}
			else {
				if (!world.isClient) {
					user.sendMessage(new TranslatableText("message." + Wendigoism.MODID + ".tethered"), true);
				}
				result.set(new TypedActionResult<>(ActionResult.FAIL, stack));
			}
		});
		return result.get();
	}
}