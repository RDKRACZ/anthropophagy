package moriyashiine.wendigoism.common.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class KnifeItem extends SwordItem {
	public static final List<DropEntry> DROPS = new ArrayList<>();
	
	public KnifeItem(ToolMaterial toolMaterial, Settings settings) {
		super(toolMaterial, 0, -2, settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user.isSneaking()) {
			user.attack(user);
			return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
		}
		return super.use(world, user, hand);
	}
	
	public static class DropEntry {
		public final EntityType<?> type;
		public final Item normalDrop, fireDrop;
		
		public DropEntry(EntityType<?> type, Item normalDrop, Item fireDrop) {
			this.type = type;
			this.normalDrop = normalDrop;
			this.fireDrop = fireDrop;
		}
	}
}