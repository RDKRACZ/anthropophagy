/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.mixin;

import moriyashiine.anthropophagy.common.ModConfig;
import moriyashiine.anthropophagy.common.component.entity.CannibalLevelComponent;
import moriyashiine.anthropophagy.common.component.entity.TetheredComponent;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import moriyashiine.anthropophagy.common.init.ModEntityComponents;
import moriyashiine.anthropophagy.common.init.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "eatFood", at = @At("HEAD"))
	private void anthropophagy$handleCannibalFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
		if (stack.isFood()) {
			CannibalLevelComponent cannibalLevelComponent = getComponent(ModEntityComponents.CANNIBAL_LEVEL);
			TetheredComponent tetheredComponent = getComponent(ModEntityComponents.TETHERED);
			if (stack.isIn(ModTags.Items.FLESH)) {
				if (!tetheredComponent.isTethered()) {
					if (cannibalLevelComponent.getCannibalLevel() < CannibalLevelComponent.MAX_LEVEL) {
						cannibalLevelComponent.setCannibalLevel(Math.min(cannibalLevelComponent.getCannibalLevel() + 2, CannibalLevelComponent.MAX_LEVEL));
						cannibalLevelComponent.updateAttributes();
					}
					if (!world.isClient && cannibalLevelComponent.getCannibalLevel() == 20 || cannibalLevelComponent.getCannibalLevel() == 21) {
						addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
						addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200));
					}
				}
				if (!world.isClient && ModConfig.enablePiglutton) {
					PigluttonEntity.attemptSpawn(this, cannibalLevelComponent.getCannibalLevel());
				}
			} else {
				if (!tetheredComponent.isTethered() && cannibalLevelComponent.getCannibalLevel() > 0) {
					cannibalLevelComponent.setCannibalLevel(Math.max(cannibalLevelComponent.getCannibalLevel() - 1, 0));
					cannibalLevelComponent.updateAttributes();
				}
				if (!world.isClient && cannibalLevelComponent.getCannibalLevel() >= 20) {
					ModEntityComponents.playerCannibalLevel = cannibalLevelComponent.getCannibalLevel();
				}
			}
		}
	}

	@Inject(method = "canEquip", at = @At("HEAD"), cancellable = true)
	private void anthropophagy$preventArmorDispensing(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (!getComponent(ModEntityComponents.CANNIBAL_LEVEL).canEquip(MobEntity.getPreferredEquipmentSlot(stack))) {
			cir.setReturnValue(false);
		}
	}
}
