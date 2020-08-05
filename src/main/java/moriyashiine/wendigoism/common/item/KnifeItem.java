package moriyashiine.wendigoism.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class KnifeItem extends SwordItem {
	public KnifeItem(ToolMaterial toolMaterial, Settings settings) {
		super(toolMaterial, 0, -2, settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user.isSneaking()) {
			user.attack(user);
			return new TypedActionResult<>(ActionResult.success(world.isClient), user.getStackInHand(hand));
		}
		return super.use(world, user, hand);
	}
}
