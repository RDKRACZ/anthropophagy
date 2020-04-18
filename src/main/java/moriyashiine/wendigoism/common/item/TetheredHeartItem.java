package moriyashiine.wendigoism.common.item;

import moriyashiine.wendigoism.Wendigoism;
import moriyashiine.wendigoism.common.capability.CannibalCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

/** File created by mason on 4/18/20 **/
public class TetheredHeartItem extends Item {
	public TetheredHeartItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	@Nonnull
	public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
		AtomicReference<ActionResultType> type = new AtomicReference<>(super.onItemUse(context));
		PlayerEntity player = context.getPlayer();
		if (player != null) {
			player.getCapability(CannibalCapability.CAP).ifPresent(c -> {
				if (!c.tethered) {
					c.tethered = true;
					if (!context.getWorld().isRemote) {
						player.sendStatusMessage(new TranslationTextComponent("message." + Wendigoism.MODID + ".tether"), false);
					}
					context.getItem().shrink(1);
					type.set(ActionResultType.SUCCESS);
				}
				else {
					if (!context.getWorld().isRemote) {
						player.sendStatusMessage(new TranslationTextComponent("message." + Wendigoism.MODID + ".tethered"), false);
					}
					type.set(ActionResultType.FAIL);
				}
			});
		}
		return type.get();
	}
}