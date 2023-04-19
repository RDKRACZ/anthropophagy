/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.mixin;

import moriyashiine.anthropophagy.common.registry.ModEntityComponents;
import moriyashiine.anthropophagy.common.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "dropEquipment", at = @At("HEAD"))
	private void anthropophagy$dropTetheredHeart(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
		ModEntityComponents.TETHERED.maybeGet(this).ifPresent(tetheredComponent -> {
			if (tetheredComponent.isTethered()) {
				dropStack(new ItemStack(ModItems.PIGLUTTON_HEART));
			}
		});
	}
}
