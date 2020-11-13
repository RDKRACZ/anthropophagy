package moriyashiine.anthropophagy.common.entity;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.item.FleshItem;
import moriyashiine.anthropophagy.common.registry.APItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.List;

public class PigluttonEntity extends HostileEntity {
	public PigluttonEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, Anthropophagy.config.strongerPiglutton ? 200 : 100).add(EntityAttributes.GENERIC_ARMOR, 10).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, Anthropophagy.config.strongerPiglutton ? 16 : 8).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_HOGLIN_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_HOGLIN_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_HOGLIN_DEATH;
	}
	
	@Override
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		if (super.canSpawn(world, spawnReason)) {
			if (spawnReason == SpawnReason.NATURAL || spawnReason == SpawnReason.CHUNK_GENERATION) {
				return world.getRandom().nextFloat() < 1 / 5f;
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
		super.dropEquipment(source, lootingMultiplier, allowDrops);
		ItemEntity itemEntity = dropItem(APItems.PIGLUTTON_HEART);
		if (itemEntity != null) {
			itemEntity.setCovetedItem();
		}
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
	public void tick() {
		super.tick();
		if (!dead && age % 5 == 0) {
			List<ItemEntity> drops = world.getEntitiesByType(EntityType.ITEM, getBoundingBox().expand(8, 4, 8), e -> e.getStack().getItem() instanceof FleshItem);
			if (!drops.isEmpty()) {
				ItemEntity item = drops.get(0);
				if (item != null) {
					getNavigation().startMovingTo(item, 1);
					if (distanceTo(item) < 1.5) {
						playSound(SoundEvents.ENTITY_GENERIC_EAT, 1, 1);
						for (int i = 0; i < 8; i++) {
							world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, item.getStack()), item.getX() + MathHelper.nextFloat(random, -0.5f, 0.5f), item.getY() + MathHelper.nextFloat(random, -0.5f, 0.5f), item.getZ() + MathHelper.nextFloat(random, -0.5f, 0.5f), 0, 0, 0);
						}
						FoodComponent food = item.getStack().getItem().getFoodComponent();
						if (food != null) {
							heal(food.getHunger() * 2);
						}
						item.getStack().decrement(1);
					}
				}
			}
		}
		if (horizontalCollision && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
			Box box = getBoundingBox().expand(0.2);
			for (BlockPos pos : BlockPos.iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY), MathHelper.floor(box.minZ), MathHelper.floor(box.maxX), MathHelper.floor(box.maxY), MathHelper.floor(box.maxZ))) {
				if (world.getBlockState(pos).getHardness(world, pos) < 0.5f) {
					world.breakBlock(pos, true);
				}
			}
		}
	}
	
	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new MeleeAttackGoal(this, 1, true));
		goalSelector.add(2, new WanderAroundFarGoal(this, 1));
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new FollowTargetGoal<>(this, LivingEntity.class, 10, true, false, e -> e instanceof PlayerEntity || e instanceof MerchantEntity || e instanceof IllagerEntity || e instanceof WitchEntity));
	}
}
