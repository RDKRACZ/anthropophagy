package moriyashiine.wendigoism.common.item;

import com.google.common.collect.Multimap;
import moriyashiine.wendigoism.Wendigoism;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KnifeItem extends SwordItem {
	private static final UUID REACH_MOD = UUID.fromString("b2a5437c-84df-4a4c-9bae-0bfaa5b6a8c1");
	
	public static final List<DropEntry> DROPS = new ArrayList<>();
	
	public KnifeItem(IItemTier tier) {
		super(tier, 0, -2, new Item.Properties().group(Wendigoism.group));
	}
	
	@Override
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
		if (player.isCrouching()) {
			player.attackTargetEntityWithCurrentItem(player);
			return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	@Nonnull
	public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType slot) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot);
		if (slot == EquipmentSlotType.MAINHAND) map.put(PlayerEntity.REACH_DISTANCE.getName(), new AttributeModifier(REACH_MOD, "Weapon modifier", -1, AttributeModifier.Operation.ADDITION));
		return map;
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