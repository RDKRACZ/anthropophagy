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
		TypedActionResult<ItemStack> result;
		ItemStack stack = user.getStackInHand(hand);
		WendigoAccessor wendigoAccessor = (WendigoAccessor) user;
		if (!wendigoAccessor.getTethered()) {
			wendigoAccessor.setTethered(true);
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