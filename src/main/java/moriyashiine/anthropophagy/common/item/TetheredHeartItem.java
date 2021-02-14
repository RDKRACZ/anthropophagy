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
		boolean client = world.isClient;
		ItemStack stack = user.getStackInHand(hand);
		if (!((CannibalAccessor) user).getTethered()) {
			((CannibalAccessor) user).setTethered(true);
			if (!client) {
				user.sendMessage(new TranslatableText("message." + Anthropophagy.MODID + ".tether"), true);
			}
			stack.decrement(1);
			return new TypedActionResult<>(ActionResult.success(client), stack);
		}
		if (!client) {
			user.sendMessage(new TranslatableText("message." + Anthropophagy.MODID + ".tethered"), true);
		}
		return new TypedActionResult<>(ActionResult.FAIL, stack);
	}
}
