package moriyashiine.cannibalism.common.entity;

import moriyashiine.cannibalism.Cannibalism;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WendigoEntity extends MonsterEntity {
	public static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(WendigoEntity.class, DataSerializers.BOOLEAN);
	
	public WendigoEntity(EntityType<? extends MonsterEntity> type, World world) {
		super(type, world);
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		getDataManager().register(ATTACKING, false);
	}
	
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50);
		getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.66);
		getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45);
		getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
	}
	
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new SwimGoal(this));
		goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, true));
		goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1));
		goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8));
		goalSelector.addGoal(3, new LookRandomlyGoal(this));
		targetSelector.addGoal(0, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, e -> e instanceof PlayerEntity || e instanceof AbstractVillagerEntity || e instanceof AbstractIllagerEntity || e instanceof WitchEntity));
	}
	
	@Override
	public boolean canSpawn(@Nonnull IWorld world, SpawnReason reason) {
		return super.canSpawn(world, reason) && world.getRandom().nextFloat() < 1 / 3f;
	}
	
	@Override
	protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
		super.dropSpecialItems(source, looting, recentlyHit);
		entityDropItem(Cannibalism.RegistryEvents.wendigo_heart);
	}
	
	@Override
	public void setAttackTarget(@Nullable LivingEntity entity) {
		super.setAttackTarget(entity);
		getDataManager().set(ATTACKING, entity != null);
	}
	
	@Override
	public void tick() {
		super.tick();
		if (collidedHorizontally && ForgeEventFactory.getMobGriefingEvent(world, this)) {
			AxisAlignedBB box = getBoundingBox().grow(0.2);
			for (BlockPos pos : BlockPos.getAllInBoxMutable(MathHelper.floor(box.minX), MathHelper.floor(box.minY), MathHelper.floor(box.minZ), MathHelper.floor(box.maxX), MathHelper.floor(box.maxY), MathHelper.floor(box.maxZ)))
				if (world.getBlockState(pos).getBlockHardness(world, pos) < 0.5f) world.destroyBlock(pos, true);
		}
	}
}