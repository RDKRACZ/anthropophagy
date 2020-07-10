package moriyashiine.wendigoism.common.entity;

import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.common.registry.WDItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Objects;

public class WendigoEntity extends HostileEntity {
	public static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	
	public WendigoEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, WDConfig.INSTANCE.strongerWendigo ? 120 : 60).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, WDConfig.INSTANCE.strongerWendigo ? 12 : 6).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48);
	}
	
	@Override
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		if (super.canSpawn(world, spawnReason)) {
			if (spawnReason == SpawnReason.NATURAL || spawnReason == SpawnReason.CHUNK_GENERATION) {
				return world.getRandom().nextFloat() < 1 / 3f;
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
		super.dropEquipment(source, lootingMultiplier, allowDrops);
		ItemEntity itemEntity = dropItem(WDItems.WENDIGO_HEART);
		if (itemEntity != null) {
			itemEntity.setCovetedItem();
		}
	}
	
	@Override
	public void setTarget(LivingEntity target) {
		super.setTarget(target);
		dataTracker.set(ATTACKING, target != null);
	}
	
	@Override
	public void tick() {
		super.tick();
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
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(ATTACKING, false);
	}
	
	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new MeleeAttackGoal(this, 1, true));
		goalSelector.add(2, new WanderAroundFarGoal(this, 1));
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new FollowTargetGoal<>(this, LivingEntity.class, 10, true, false, e -> e instanceof PlayerEntity || e instanceof AbstractTraderEntity || e instanceof IllagerEntity || e instanceof WitchEntity));
	}
	
	@Override
	public EntityData initialize(WorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CompoundTag entityTag) {
		MobEntity.createMobAttributes();
		Objects.requireNonNull(getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(WDConfig.INSTANCE.strongerWendigo ? 120 : 60);
		Objects.requireNonNull(getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(WDConfig.INSTANCE.strongerWendigo ? 12 : 6);
		Objects.requireNonNull(getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.45);
		Objects.requireNonNull(getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(0.5);
		Objects.requireNonNull(getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE)).setBaseValue(48);
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}
}