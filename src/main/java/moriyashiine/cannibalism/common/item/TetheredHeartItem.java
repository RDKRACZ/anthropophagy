package moriyashiine.cannibalism.common.item;

import moriyashiine.cannibalism.Cannibalism;
import moriyashiine.cannibalism.common.capability.CannibalCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

public class TetheredHeartItem extends Item {
	public TetheredHeartItem() {
		super(new Item.Properties().group(Cannibalism.group));
	}
	
	@Override
	@Nonnull
	public ActionResultType onItemUse(ItemUseContext context) {
		AtomicReference<ActionResultType> type = new AtomicReference<>(super.onItemUse(context));
		PlayerEntity player = context.getPlayer();
		if (player != null) {
			player.getCapability(CannibalCapability.CAP).ifPresent(c -> {
				if (!c.tethered) {
					c.tethered = true;
					if (!context.getWorld().isRemote) player.sendStatusMessage(new TranslationTextComponent("cannibalism.tether"), false);
					context.getItem().shrink(1);
					type.set(ActionResultType.SUCCESS);
				}
				else {
					if (!context.getWorld().isRemote) player.sendStatusMessage(new TranslationTextComponent("cannibalism.tethered"), false);
					type.set(ActionResultType.FAIL);
				}
			});
		}
		return type.get();
	}
}