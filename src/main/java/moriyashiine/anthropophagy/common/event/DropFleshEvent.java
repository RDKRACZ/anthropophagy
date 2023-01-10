/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common.event;

import moriyashiine.anthropophagy.common.ModConfig;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import moriyashiine.anthropophagy.common.item.FleshItem;
import moriyashiine.anthropophagy.common.registry.ModTags;
import moriyashiine.anthropophagy.common.util.FleshDropEntry;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;

public class DropFleshEvent implements ServerLivingEntityEvents.AllowDamage {
	@Override
	public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
		if (!entity.world.isClient && amount >= 2) {
			if (source.getAttacker() instanceof PigluttonEntity || (source.getAttacker() instanceof LivingEntity living && living.getMainHandStack().isIn(ModTags.Items.KNIVES))) {
				for (EntityType<?> entityType : FleshDropEntry.DROP_MAP.keySet()) {
					if (entity.getType() == entityType && entity.world.random.nextFloat() * ModConfig.damageNeededForGuaranteedFleshDrop < amount) {
						FleshDropEntry entry = FleshDropEntry.DROP_MAP.get(entityType);
						ItemStack drop = new ItemStack(entity.getFireTicks() > 0 ? entry.cooked_drop() : entry.raw_drop());
						if (drop.getItem() instanceof FleshItem) {
							drop.getOrCreateNbt().putString("OwnerName", entity.getDisplayName().getString());
						}
						ItemScatterer.spawn(entity.world, entity.getX(), entity.getY(), entity.getZ(), drop);
					}
				}
			}
		}
		return true;
	}
}
