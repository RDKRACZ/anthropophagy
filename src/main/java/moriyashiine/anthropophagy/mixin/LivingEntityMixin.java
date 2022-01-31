package moriyashiine.anthropophagy.mixin;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import moriyashiine.anthropophagy.common.item.FleshItem;
import moriyashiine.anthropophagy.common.registry.ModComponents;
import moriyashiine.anthropophagy.common.registry.ModItems;
import moriyashiine.anthropophagy.common.registry.ModRecipeTypes;
import moriyashiine.anthropophagy.common.registry.ModTags;
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
		if (cir.getReturnValue() && !world.isClient) {
			if (source.getAttacker() instanceof PigluttonEntity || (source.getAttacker() instanceof LivingEntity living && ModTags.KNIVES.contains(living.getMainHandStack().getItem()))) {
				world.getRecipeManager().listAllOfType(ModRecipeTypes.FLESH_DROP_RECIPE_TYPE).forEach(recipe -> {
					if (recipe.entity_type == getType() && world.random.nextFloat() * Anthropophagy.config.damageNeededForGuaranteedFleshDrop < amount) {
						ItemStack drop = new ItemStack(getFireTicks() > 0 ? recipe.cooked_drop : recipe.raw_drop);
						if (drop.getItem() instanceof FleshItem) {
							drop.getOrCreateNbt().putString("OwnerName", getDisplayName().getString());
						}
						ItemScatterer.spawn(world, getX(), getY(), getZ(), drop);
					}
				});
			}
		}
	}

	@Inject(method = "dropEquipment", at = @At("HEAD"))
	private void anthropophagy$dropTetheredHeart(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
		ModComponents.CANNIBAL_COMPONENT.maybeGet(this).ifPresent(cannibalComponent -> {
			if (cannibalComponent.isTethered()) {
				dropStack(new ItemStack(ModItems.PIGLUTTON_HEART));
			}
		});
	}
}
