package moriyashiine.anthropophagy.mixin;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import moriyashiine.anthropophagy.common.registry.ModComponents;
import moriyashiine.anthropophagy.common.registry.ModEntityTypes;
import moriyashiine.anthropophagy.common.registry.ModItems;
import moriyashiine.anthropophagy.common.registry.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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
		if (!world.isClient) {
			ModComponents.CANNIBAL_COMPONENT.maybeGet(this).ifPresent(cannibalComponent -> {
				if (stack.isFood()) {
					if (stack.getItem() == ModItems.CORRUPT_FLESH) {
						addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 1));
					}
					if (ModTags.FLESH.contains(stack.getItem())) {
						if (!cannibalComponent.isTethered()) {
							cannibalComponent.setCannibalLevel(Math.min(cannibalComponent.getCannibalLevel() + 2, 300));
						}
						if (cannibalComponent.getCannibalLevel() == 20 || cannibalComponent.getCannibalLevel() == 21) {
							addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
							addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200));
						}
						if (Anthropophagy.config.enablePiglutton) {
							float chance = 0;
							if (cannibalComponent.getCannibalLevel() >= 40) {
								if (cannibalComponent.getCannibalLevel() >= 70) {
									chance = 1 / 10F;
								} else if (cannibalComponent.getCannibalLevel() >= 60) {
									chance = 1 / 15F;
								} else if (cannibalComponent.getCannibalLevel() >= 50) {
									chance = 1 / 20F;
								} else {
									chance = 1 / 25F;
								}
							}
							if (random.nextFloat() < chance) {
								PigluttonEntity piglutton = ModEntityTypes.PIGLUTTON.create(world);
								if (piglutton != null) {
									boolean valid = false;
									for (int i = 0; i < 8; i++) {
										if (piglutton.teleport(getBlockPos().getX() + MathHelper.nextInt(random, -16, 16), getBlockPos().getY() + MathHelper.nextInt(random, -6, 6), getBlockPos().getZ() + MathHelper.nextInt(random, -16, 16), false)) {
											valid = true;
											break;
										}
									}
									if (valid) {
										world.spawnEntity(piglutton);
										piglutton.setTarget(this);
										world.playSoundFromEntity(null, piglutton, SoundEvents.ENTITY_HOGLIN_CONVERTED_TO_ZOMBIFIED, SoundCategory.HOSTILE, 1, 1);
									}
								}
							}
						}
					} else {
						if (!cannibalComponent.isTethered()) {
							cannibalComponent.setCannibalLevel(Math.max(cannibalComponent.getCannibalLevel() - 1, 0));
						}
						if (cannibalComponent.getCannibalLevel() >= 20) {
							FoodComponent food = stack.getItem().getFoodComponent();
							if (food != null) {
								cannibalComponent.setHungerTimer((int) (cannibalComponent.getHungerTimer() + (food.getHunger() * getFoodModifier(cannibalComponent.getCannibalLevel()))));
							}
						}
					}
					cannibalComponent.updateAttributes(PlayerEntity.class.cast(this));
				}
			});
		}
	}

	@Unique
	private static float getFoodModifier(int level) {
		if (level >= 70) {
			return 1.6F;
		}
		if (level >= 60) {
			return 1.5F;
		}
		if (level >= 50) {
			return 1.4F;
		}
		if (level >= 40) {
			return 1.3F;
		}
		if (level >= 30) {
			return 1.2F;
		}
		if (level >= 20) {
			return 1.1F;
		}
		return 1;
	}
}
