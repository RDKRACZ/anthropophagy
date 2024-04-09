/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.common.entity;

import moriyashiine.anthropophagy.common.ModConfig;
import moriyashiine.anthropophagy.common.init.ModEntityTypes;
import moriyashiine.anthropophagy.common.init.ModSoundEvents;
import moriyashiine.anthropophagy.common.init.ModTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.List;

public class PigluttonEntity extends HostileEntity {
	public PigluttonEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, ModConfig.strongerPiglutton ? 200 : 100).add(EntityAttributes.GENERIC_ARMOR, 10).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, ModConfig.strongerPiglutton ? 16 : 8).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48);
	}

	public static boolean canSpawn(EntityType<PigluttonEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return random.nextInt(8) == 0 && HostileEntity.canSpawnInDark(type, world, spawnReason, pos, random);
	}

	@Override
	public void tick() {
		super.tick();
		if (!dead && age % 5 == 0) {
			List<ItemEntity> drops = getWorld().getEntitiesByType(EntityType.ITEM, getBoundingBox().expand(8, 4, 8), foundEntity -> foundEntity.getStack().isIn(ModTags.Items.FLESH));
			if (!drops.isEmpty()) {
				ItemEntity item = drops.get(0);
				if (item != null) {
					getNavigation().startMovingTo(item, 1);
					if (distanceTo(item) < 1.5) {
						if (!getWorld().isClient) {
							FoodComponent food = item.getStack().getItem().getFoodComponent();
							if (food != null) {
								heal(food.getHunger() * 2);
							}
							item.getStack().decrement(1);
							playSound(SoundEvents.ENTITY_GENERIC_EAT, 1, 1);
						} else {
							for (int i = 0; i < 8; i++) {
								getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, item.getStack()), item.getX() + MathHelper.nextFloat(random, -0.5F, 0.5F), item.getY() + MathHelper.nextFloat(random, -0.5F, 0.5F), item.getZ() + MathHelper.nextFloat(random, -0.5F, 0.5F), 0, 0, 0);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (!getWorld().isClient && horizontalCollision && getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
			Box box = getBoundingBox().expand(0.2);
			for (BlockPos pos : BlockPos.iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY), MathHelper.floor(box.minZ), MathHelper.floor(box.maxX), MathHelper.floor(box.maxY), MathHelper.floor(box.maxZ))) {
				float hardness = getWorld().getBlockState(pos).getHardness(getWorld(), pos);
				if (hardness >= 0 && hardness < 0.5F) {
					getWorld().breakBlock(pos, true);
				}
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ENTITY_PIGLUTTON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSoundEvents.ENTITY_PIGLUTTON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENTITY_PIGLUTTON_DEATH;
	}

	@Override
	public boolean tryAttack(Entity target) {
		boolean flag = super.tryAttack(target);
		if (flag) {
			swingHand(Hand.MAIN_HAND);
		}
		return flag;
	}

	@Override
	public boolean cannotDespawn() {
		return true;
	}

	@Override
	public boolean disablesShield() {
		return true;
	}

	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new MeleeAttackGoal(this, 1, true));
		goalSelector.add(2, new WanderAroundFarGoal(this, 1));
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new ActiveTargetGoal<>(this, LivingEntity.class, 10, true, false, living -> living.getType().isIn(ModTags.EntityTypes.PIGLUTTON_TARGETS)));
	}

	public static void attemptSpawn(LivingEntity living, int cannibalLevel) {
		if (living.getWorld().isClient) {
			return;
		}
		float chance = 0;
		if (cannibalLevel >= 40) {
			if (cannibalLevel >= 70) {
				chance = 1 / 10F;
			} else if (cannibalLevel >= 60) {
				chance = 1 / 15F;
			} else if (cannibalLevel >= 50) {
				chance = 1 / 20F;
			} else {
				chance = 1 / 25F;
			}
		}
		if (living.getRandom().nextFloat() < chance) {
			PigluttonEntity piglutton = ModEntityTypes.PIGLUTTON.create(living.getWorld());
			if (piglutton != null) {
				for (int i = 0; i < 8; i++) {
					if (piglutton.teleport(living.getBlockPos().getX() + MathHelper.nextInt(living.getRandom(), -16, 16), living.getBlockPos().getY() + MathHelper.nextInt(living.getRandom(), -6, 6), living.getBlockPos().getZ() + MathHelper.nextInt(living.getRandom(), -16, 16), false)) {
						living.getWorld().spawnEntity(piglutton);
						piglutton.setTarget(living);
						living.getWorld().playSoundFromEntity(null, piglutton, ModSoundEvents.ENTITY_PIGLUTTON_SPAWN, SoundCategory.HOSTILE, 1, 1);
						return;
					}
				}
			}
		}
	}
}
