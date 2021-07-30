package moriyashiine.anthropophagy.mixin;

import moriyashiine.anthropophagy.api.component.CannibalComponent;
import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import moriyashiine.anthropophagy.common.registry.APEntityTypes;
import moriyashiine.anthropophagy.common.registry.APItems;
import moriyashiine.anthropophagy.common.registry.APTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	private static final EntityAttributeModifier ATTACK_DAMAGE_MODIFIER_7 = new EntityAttributeModifier(UUID.fromString("cce7af90-8887-4b43-a3b0-3265ab5a1b27"), "Cannibal modifier", 6, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ATTACK_SPEED_MODIFIER_7 = new EntityAttributeModifier(UUID.fromString("b7657ce5-e362-4cac-baba-9661ca780047"), "Cannibal modifier", 1, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ARMOR_MODIFIER_7 = new EntityAttributeModifier(UUID.fromString("4fd0fcf3-a827-4ad0-8318-07fafaf3126e"), "Cannibal modifier", 14, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier MOVEMENT_SPEED_MODIFIER_7 = new EntityAttributeModifier(UUID.fromString("006731ce-988c-4d8d-921a-0e812ff6e52a"), "Cannibal modifier", 1 / 20f, EntityAttributeModifier.Operation.ADDITION);
	
	private static final EntityAttributeModifier ATTACK_DAMAGE_MODIFIER_6 = new EntityAttributeModifier(UUID.fromString("500d09c9-efbe-4ac4-95f0-0535a087e4b6"), "Cannibal modifier", 5, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ATTACK_SPEED_MODIFIER_6 = new EntityAttributeModifier(UUID.fromString("b15887df-6a68-4446-9b0e-c6b45744255c"), "Cannibal modifier", 0.8, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ARMOR_MODIFIER_6 = new EntityAttributeModifier(UUID.fromString("29e088db-9aa8-4c8a-88b6-f519ef822326"), "Cannibal modifier", 12, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier MOVEMENT_SPEED_MODIFIER_6 = new EntityAttributeModifier(UUID.fromString("7c2d047f-f666-43a1-882b-ad751e7d5800"), "Cannibal modifier", 1 / 25f, EntityAttributeModifier.Operation.ADDITION);
	
	private static final EntityAttributeModifier ATTACK_DAMAGE_MODIFIER_5 = new EntityAttributeModifier(UUID.fromString("519e0e77-b8a8-4b76-980b-4f407b5afca7"), "Cannibal modifier", 4, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ATTACK_SPEED_MODIFIER_5 = new EntityAttributeModifier(UUID.fromString("6fa06abe-f5df-4542-8ed5-d478a3b268d7"), "Cannibal modifier", 0.6, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ARMOR_MODIFIER_5 = new EntityAttributeModifier(UUID.fromString("301ccf45-24c8-4a79-9506-28bf6da92046"), "Cannibal modifier", 10, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier MOVEMENT_SPEED_MODIFIER_5 = new EntityAttributeModifier(UUID.fromString("e4be7101-30a6-417c-917b-4a54ccc53f33"), "Cannibal modifier", 1 / 30f, EntityAttributeModifier.Operation.ADDITION);
	
	private static final EntityAttributeModifier ATTACK_DAMAGE_MODIFIER_4 = new EntityAttributeModifier(UUID.fromString("31187db9-4b70-4f79-98c8-2aec225dcae1"), "Cannibal modifier", 3, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ATTACK_SPEED_MODIFIER_4 = new EntityAttributeModifier(UUID.fromString("2ba3906b-5d20-4729-a3df-aa59cef97667"), "Cannibal modifier", 0.4, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ARMOR_MODIFIER_4 = new EntityAttributeModifier(UUID.fromString("b58654a4-9229-4f05-aa89-02d7fa5f98d5"), "Cannibal modifier", 8, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier MOVEMENT_SPEED_MODIFIER_4 = new EntityAttributeModifier(UUID.fromString("26f4947b-9cd6-42c2-ac0d-a35f53f00c8a"), "Cannibal modifier", 1 / 40f, EntityAttributeModifier.Operation.ADDITION);
	
	private static final EntityAttributeModifier ATTACK_DAMAGE_MODIFIER_3 = new EntityAttributeModifier(UUID.fromString("26f4947b-9cd6-42c2-ac0d-a35f53f00c8a"), "Cannibal modifier", 2, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ATTACK_SPEED_MODIFIER_3 = new EntityAttributeModifier(UUID.fromString("34c73848-3a6b-405a-b0b3-eabfd7d93dbd"), "Cannibal modifier", 0.2, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ARMOR_MODIFIER_3 = new EntityAttributeModifier(UUID.fromString("144088ca-bfaa-4f80-8de4-abbf5bd7bfec"), "Cannibal modifier", 6, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier MOVEMENT_SPEED_MODIFIER_3 = new EntityAttributeModifier(UUID.fromString("d8534bfb-ed44-4317-8c91-f5228f8487ed"), "Cannibal modifier", 1 / 50f, EntityAttributeModifier.Operation.ADDITION);
	
	private static final EntityAttributeModifier ATTACK_DAMAGE_MODIFIER_2 = new EntityAttributeModifier(UUID.fromString("95258be5-d00d-4687-b130-80824b67536f"), "Cannibal modifier", 1, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ARMOR_MODIFIER_2 = new EntityAttributeModifier(UUID.fromString("0922bf38-ba6f-4b8c-af14-c49f436c234e"), "Cannibal modifier", 4, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier MOVEMENT_SPEED_MODIFIER_2 = new EntityAttributeModifier(UUID.fromString("329b23b3-4c34-4478-8dd2-1ac2d83abd0b"), "Cannibal modifier", 1 / 60f, EntityAttributeModifier.Operation.ADDITION);
	
	private static final EntityAttributeModifier ARMOR_MODIFIER_1 = new EntityAttributeModifier(UUID.fromString("80788884-36e7-46c2-bdc6-9c901c8368b3"), "Cannibal modifier", 2, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier MOVEMENT_SPEED_MODIFIER_1 = new EntityAttributeModifier(UUID.fromString("44806f98-91fb-41df-9554-662930ef838e"), "Cannibal modifier", 1 / 70f, EntityAttributeModifier.Operation.ADDITION);
	
	private static final EntityAttributeModifier MOVEMENT_SPEED_MODIFIER_0 = new EntityAttributeModifier(UUID.fromString("92118505-869f-437e-981f-fc238ed8633c"), "Cannibal modifier", 1 / 80f, EntityAttributeModifier.Operation.ADDITION);
	
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(method = "eatFood", at = @At("HEAD"))
	private void eatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> callbackInfo) {
		if (!world.isClient) {
			CannibalComponent.maybeGet((PlayerEntity) (Object) this).ifPresent(cannibalComponent -> {
				if (stack.isFood()) {
					if (stack.getItem() == APItems.CORRUPT_FLESH) {
						addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 1));
					}
					if (APTags.FLESH.contains(stack.getItem())) {
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
									chance = 1 / 10f;
								}
								else if (cannibalComponent.getCannibalLevel() >= 60) {
									chance = 1 / 15f;
								}
								else if (cannibalComponent.getCannibalLevel() >= 50) {
									chance = 1 / 20f;
								}
								else {
									chance = 1 / 25f;
								}
							}
							if (random.nextFloat() < chance) {
								PigluttonEntity piglutton = APEntityTypes.PIGLUTTON.create(world);
								if (piglutton != null) {
									boolean valid = false;
									BlockPos pos = getBlockPos();
									for (int i = 0; i < 8; i++) {
										if (piglutton.teleport(pos.getX() + MathHelper.nextInt(random, -16, 16), pos.getY() + MathHelper.nextInt(random, -6, 6), pos.getZ() + MathHelper.nextInt(random, -16, 16), false)) {
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
					}
					else {
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
					updateAttributes((PlayerEntity) (Object) this, cannibalComponent);
				}
			});
		}
	}
	
	private static float getFoodModifier(int level) {
		if (level >= 70) {
			return 1.6f;
		}
		if (level >= 60) {
			return 1.5f;
		}
		if (level >= 50) {
			return 1.4f;
		}
		if (level >= 40) {
			return 1.3f;
		}
		if (level >= 30) {
			return 1.2f;
		}
		if (level >= 20) {
			return 1.1f;
		}
		return 1;
	}
	
	@SuppressWarnings("ConstantConditions")
	private static void updateAttributes(PlayerEntity player, CannibalComponent cannibalComponent) {
		EntityAttributeInstance attackDamageAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		EntityAttributeInstance attackSpeedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
		EntityAttributeInstance armorAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
		EntityAttributeInstance movementSpeedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		boolean shouldHave = cannibalComponent.getCannibalLevel() >= 90;
		if (shouldHave) {
			if (!attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_7)) {
				attackDamageAttribute.addPersistentModifier(ATTACK_DAMAGE_MODIFIER_7);
				attackSpeedAttribute.addPersistentModifier(ATTACK_SPEED_MODIFIER_7);
				armorAttribute.addPersistentModifier(ARMOR_MODIFIER_7);
				movementSpeedAttribute.addPersistentModifier(MOVEMENT_SPEED_MODIFIER_7);
			}
		}
		else if (attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_7)) {
			attackDamageAttribute.removeModifier(ATTACK_DAMAGE_MODIFIER_7);
			attackSpeedAttribute.removeModifier(ATTACK_SPEED_MODIFIER_7);
			armorAttribute.removeModifier(ARMOR_MODIFIER_7);
			movementSpeedAttribute.removeModifier(MOVEMENT_SPEED_MODIFIER_7);
		}
		shouldHave = cannibalComponent.getCannibalLevel() >= 80 && cannibalComponent.getCannibalLevel() < 90;
		if (shouldHave) {
			if (!attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_6)) {
				attackDamageAttribute.addPersistentModifier(ATTACK_DAMAGE_MODIFIER_6);
				attackSpeedAttribute.addPersistentModifier(ATTACK_SPEED_MODIFIER_6);
				armorAttribute.addPersistentModifier(ARMOR_MODIFIER_6);
				movementSpeedAttribute.addPersistentModifier(MOVEMENT_SPEED_MODIFIER_6);
			}
		}
		else if (attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_6)) {
			attackDamageAttribute.removeModifier(ATTACK_DAMAGE_MODIFIER_6);
			attackSpeedAttribute.removeModifier(ATTACK_SPEED_MODIFIER_6);
			armorAttribute.removeModifier(ARMOR_MODIFIER_6);
			movementSpeedAttribute.removeModifier(MOVEMENT_SPEED_MODIFIER_6);
		}
		shouldHave = cannibalComponent.getCannibalLevel() >= 70 && cannibalComponent.getCannibalLevel() < 80;
		if (shouldHave) {
			if (!attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_5)) {
				attackDamageAttribute.addPersistentModifier(ATTACK_DAMAGE_MODIFIER_5);
				attackSpeedAttribute.addPersistentModifier(ATTACK_SPEED_MODIFIER_5);
				armorAttribute.addPersistentModifier(ARMOR_MODIFIER_5);
				movementSpeedAttribute.addPersistentModifier(MOVEMENT_SPEED_MODIFIER_5);
			}
		}
		else if (attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_5)) {
			attackDamageAttribute.removeModifier(ATTACK_DAMAGE_MODIFIER_5);
			attackSpeedAttribute.removeModifier(ATTACK_SPEED_MODIFIER_5);
			armorAttribute.removeModifier(ARMOR_MODIFIER_5);
			movementSpeedAttribute.removeModifier(MOVEMENT_SPEED_MODIFIER_5);
		}
		shouldHave = cannibalComponent.getCannibalLevel() >= 60 && cannibalComponent.getCannibalLevel() < 70;
		if (shouldHave) {
			if (!attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_4)) {
				attackDamageAttribute.addPersistentModifier(ATTACK_DAMAGE_MODIFIER_4);
				attackSpeedAttribute.addPersistentModifier(ATTACK_SPEED_MODIFIER_4);
				armorAttribute.addPersistentModifier(ARMOR_MODIFIER_4);
				movementSpeedAttribute.addPersistentModifier(MOVEMENT_SPEED_MODIFIER_4);
			}
		}
		else if (attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_4)) {
			attackDamageAttribute.removeModifier(ATTACK_DAMAGE_MODIFIER_4);
			attackSpeedAttribute.removeModifier(ATTACK_SPEED_MODIFIER_4);
			armorAttribute.removeModifier(ARMOR_MODIFIER_4);
			movementSpeedAttribute.removeModifier(MOVEMENT_SPEED_MODIFIER_4);
		}
		shouldHave = cannibalComponent.getCannibalLevel() >= 50 && cannibalComponent.getCannibalLevel() < 60;
		if (shouldHave) {
			if (!attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_3)) {
				attackDamageAttribute.addPersistentModifier(ATTACK_DAMAGE_MODIFIER_3);
				attackSpeedAttribute.addPersistentModifier(ATTACK_SPEED_MODIFIER_3);
				armorAttribute.addPersistentModifier(ARMOR_MODIFIER_3);
				movementSpeedAttribute.addPersistentModifier(MOVEMENT_SPEED_MODIFIER_3);
			}
		}
		else if (attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_3)) {
			attackDamageAttribute.removeModifier(ATTACK_DAMAGE_MODIFIER_3);
			attackSpeedAttribute.removeModifier(ATTACK_SPEED_MODIFIER_3);
			armorAttribute.removeModifier(ARMOR_MODIFIER_3);
			movementSpeedAttribute.removeModifier(MOVEMENT_SPEED_MODIFIER_3);
		}
		shouldHave = cannibalComponent.getCannibalLevel() >= 40 && cannibalComponent.getCannibalLevel() < 50;
		if (shouldHave && !attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_2)) {
			attackDamageAttribute.addPersistentModifier(ATTACK_DAMAGE_MODIFIER_2);
			armorAttribute.addPersistentModifier(ARMOR_MODIFIER_2);
			movementSpeedAttribute.addPersistentModifier(MOVEMENT_SPEED_MODIFIER_2);
		}
		else if (!shouldHave && attackDamageAttribute.hasModifier(ATTACK_DAMAGE_MODIFIER_2)) {
			attackDamageAttribute.removeModifier(ATTACK_DAMAGE_MODIFIER_2);
			armorAttribute.removeModifier(ARMOR_MODIFIER_2);
			movementSpeedAttribute.removeModifier(MOVEMENT_SPEED_MODIFIER_2);
		}
		shouldHave = cannibalComponent.getCannibalLevel() >= 30 && cannibalComponent.getCannibalLevel() < 40;
		if (shouldHave && !armorAttribute.hasModifier(ARMOR_MODIFIER_1)) {
			armorAttribute.addPersistentModifier(ARMOR_MODIFIER_1);
			movementSpeedAttribute.addPersistentModifier(MOVEMENT_SPEED_MODIFIER_1);
		}
		else if (!shouldHave && armorAttribute.hasModifier(ARMOR_MODIFIER_1)) {
			armorAttribute.removeModifier(ARMOR_MODIFIER_1);
			movementSpeedAttribute.removeModifier(MOVEMENT_SPEED_MODIFIER_1);
		}
		shouldHave = cannibalComponent.getCannibalLevel() >= 20 && cannibalComponent.getCannibalLevel() < 30;
		if (shouldHave && !movementSpeedAttribute.hasModifier(MOVEMENT_SPEED_MODIFIER_0)) {
			movementSpeedAttribute.addPersistentModifier(MOVEMENT_SPEED_MODIFIER_0);
		}
		else if (!shouldHave && movementSpeedAttribute.hasModifier(MOVEMENT_SPEED_MODIFIER_0)) {
			movementSpeedAttribute.removeModifier(MOVEMENT_SPEED_MODIFIER_0);
		}
	}
}
