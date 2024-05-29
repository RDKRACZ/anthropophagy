/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.anthropophagy.common.entity.ai;

import moriyashiine.anthropophagy.common.tag.ModItemTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class EatFleshGoal extends Goal {
	protected final PathAwareEntity mob;

	private ItemEntity closestFleshItem = null;

	public EatFleshGoal(PathAwareEntity mob) {
		this.mob = mob;
	}

	@Override
	public boolean canStart() {
		List<ItemEntity> drops = mob.getWorld().getEntitiesByType(EntityType.ITEM, mob.getBoundingBox().expand(8, 4, 8), foundEntity -> foundEntity.getStack().isIn(ModItemTags.FLESH));
		if (!drops.isEmpty()) {
			closestFleshItem = drops.getFirst();
			return true;
		}
		closestFleshItem = null;
		return false;
	}

	@Override
	public void tick() {
		if (closestFleshItem != null) {
			mob.getNavigation().startMovingTo(closestFleshItem, 1);
			if (mob.distanceTo(closestFleshItem) < 1.5) {
				if (!mob.getWorld().isClient) {
					if (closestFleshItem.getStack().contains(DataComponentTypes.FOOD)) {
						mob.heal(closestFleshItem.getStack().get(DataComponentTypes.FOOD).nutrition() * 2);
					}
					closestFleshItem.getStack().decrement(1);
					mob.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1, 1);
				} else {
					for (int i = 0; i < 8; i++) {
						mob.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, closestFleshItem.getStack()),
								closestFleshItem.getX() + MathHelper.nextFloat(mob.getRandom(), -0.5F, 0.5F),
								closestFleshItem.getY() + MathHelper.nextFloat(mob.getRandom(), -0.5F, 0.5F),
								closestFleshItem.getZ() + MathHelper.nextFloat(mob.getRandom(), -0.5F, 0.5F), 0, 0, 0);
					}
				}
			}
		}
	}
}
