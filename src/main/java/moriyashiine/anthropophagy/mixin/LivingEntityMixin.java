/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.mixin;

import moriyashiine.anthropophagy.common.ModConfig;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import moriyashiine.anthropophagy.common.item.FleshItem;
import moriyashiine.anthropophagy.common.registry.ModEntityComponents;
import moriyashiine.anthropophagy.common.registry.ModItems;
import moriyashiine.anthropophagy.common.registry.ModTags;
import moriyashiine.anthropophagy.common.util.FleshDropEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "damage", at = @At("RETURN"))
	private void anthropophagy$dropFleshWhenDamaged(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValueZ() && !world.isClient) {
			if (source.getAttacker() instanceof PigluttonEntity || (source.getAttacker() instanceof LivingEntity living && living.getMainHandStack().isIn(ModTags.Items.KNIVES))) {
				for (EntityType<?> entityType : FleshDropEntry.DROP_MAP.keySet()) {
					if (getType() == entityType && world.random.nextFloat() * ModConfig.damageNeededForGuaranteedFleshDrop < amount) {
						FleshDropEntry entry = FleshDropEntry.DROP_MAP.get(entityType);
						ItemStack drop = new ItemStack(getFireTicks() > 0 ? entry.cooked_drop() : entry.raw_drop());
						if (drop.getItem() instanceof FleshItem) {
							drop.getOrCreateNbt().putString("OwnerName", getDisplayName().getString());
						}
						ItemScatterer.spawn(world, getX(), getY(), getZ(), drop);
					}
				}
			}
		}
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
