package moriyashiine.anthropophagy.mixin;

import com.mojang.authlib.GameProfile;
import moriyashiine.anthropophagy.api.accessor.CannibalAccessor;
import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import moriyashiine.anthropophagy.common.item.FleshItem;
import moriyashiine.anthropophagy.common.item.KnifeItem;
import moriyashiine.anthropophagy.common.registry.APItems;
import moriyashiine.anthropophagy.common.registry.APRecipeTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class AnthropophagyHandler extends Entity implements CannibalAccessor {
	private static final TrackedData<Boolean> TETHERED = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	
	private static final TrackedData<Integer> CANNIBAL_LEVEL = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> HUNGER_TIMER = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
	
	public AnthropophagyHandler(EntityType<?> type, World world) {
		super(type, world);
	}
	
	@Override
	public boolean getTethered() {
		return dataTracker.get(TETHERED);
	}
	
	@Override
	public void setTethered(boolean tethered) {
		dataTracker.set(TETHERED, tethered);
	}
	
	@Override
	public int getCannibalLevel() {
		return dataTracker.get(CANNIBAL_LEVEL);
	}
	
	@Override
	public void setCannibalLevel(int cannibalLevel) {
		dataTracker.set(CANNIBAL_LEVEL, cannibalLevel);
	}
	
	@Override
	public int getHungerTimer() {
		return dataTracker.get(HUNGER_TIMER);
	}
	
	@Override
	public void setHungerTimer(int hungerTimer) {
		dataTracker.set(HUNGER_TIMER, hungerTimer);
	}
	
	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot slot);
	
	@SuppressWarnings("UnusedReturnValue")
	@Shadow
	public abstract boolean addStatusEffect(StatusEffectInstance effect);
	
	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		if (!world.isClient) {
			int cannibalLevel = getCannibalLevel();
			if (cannibalLevel >= 100) {
				dropStack(getEquippedStack(EquipmentSlot.LEGS).split(1));
			}
			if (cannibalLevel >= 150) {
				dropStack(getEquippedStack(EquipmentSlot.HEAD).split(1));
			}
			if (cannibalLevel >= 170) {
				dropStack(getEquippedStack(EquipmentSlot.FEET).split(1));
			}
			if (cannibalLevel >= 240) {
				dropStack(getEquippedStack(EquipmentSlot.CHEST).split(1));
			}
			if (age % 200 == 0) {
				if (cannibalLevel >= 30) {
					addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600));
				}
				if (cannibalLevel >= 50) {
					addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1));
					addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 600));
				}
				if (cannibalLevel >= 100) {
					addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 2));
				}
				if (cannibalLevel >= 150) {
					addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 1));
				}
				if (cannibalLevel >= 240) {
					addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600));
					addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 600));
				}
				if (cannibalLevel >= 270) {
					addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 2));
				}
			}
			Object obj = this;
			if (obj instanceof PlayerEntity) {
				PlayerEntity thisObj = (PlayerEntity) obj;
				int hungerTimer = getHungerTimer();
				if (hungerTimer > 0) {
					setHungerTimer(--hungerTimer);
					HungerManager hungerManager = thisObj.getHungerManager();
					hungerManager.setFoodLevel(Math.max(hungerManager.getFoodLevel() - 1, 0));
				}
			}
		}
	}
	
	@Inject(method = "eatFood", at = @At("HEAD"))
	private void handleHunger(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> callbackInfo) {
		if (!world.isClient) {
			if (stack.isFood()) {
				if (!(stack.getItem() instanceof FleshItem) && stack.getItem() != APItems.PIGLUTTON_HEART) {
					int cannibalLevel = getCannibalLevel();
					if (!getTethered()) {
						setCannibalLevel(Math.max(cannibalLevel - 10, 0));
					}
					if (cannibalLevel >= 40) {
						FoodComponent food = stack.getItem().getFoodComponent();
						if (food != null) {
							int hungerTimer = getHungerTimer();
							setHungerTimer((int) (hungerTimer + (food.getHunger() * getFoodModifier(cannibalLevel))));
						}
					}
				}
			}
		}
	}
	
	@Inject(method = "damage", at = @At("HEAD"))
	private void dropFlesh(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (!world.isClient) {
			if (source.getAttacker() instanceof PigluttonEntity || (source.getAttacker() instanceof LivingEntity && ((LivingEntity) source.getAttacker()).getMainHandStack().getItem() instanceof KnifeItem)) {
				world.getRecipeManager().listAllOfType(APRecipeTypes.flesh_drop_type).forEach(recipe -> {
					if (recipe.entity_type == getType()) {
						if (world.random.nextFloat() * Anthropophagy.CONFIG.damageNeededForGuaranteedFleshDrop < amount) {
							ItemStack drop = new ItemStack(getFireTicks() > 0 ? recipe.cooked_drop : recipe.raw_drop);
							if (drop.getItem() instanceof FleshItem) {
								drop.getOrCreateTag().putString("name", getDisplayName().getString());
							}
							BlockPos pos = getBlockPos();
							world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), drop));
						}
					}
				});
			}
		}
	}
	
	@Inject(method = "onDeath", at = @At("HEAD"))
	private void dropHeart(DamageSource source, CallbackInfo callbackInfo) {
		if (!world.isClient) {
			if (getTethered()) {
				dropStack(new ItemStack(APItems.PIGLUTTON_HEART));
			}
		}
	}
	
	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	private void readCustomDataFromTag(CompoundTag tag, CallbackInfo callbackInfo) {
		setTethered(tag.getBoolean("Tethered"));
		setCannibalLevel(tag.getInt("CannibalLevel"));
		setHungerTimer(tag.getInt("HungerTimer"));
	}
	
	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	private void writeCustomDataToTag(CompoundTag tag, CallbackInfo callbackInfo) {
		tag.putBoolean("Tethered", getTethered());
		tag.putInt("CannibalLevel", getCannibalLevel());
		tag.putInt("HungerTimer", getHungerTimer());
	}
	
	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void initDataTracker(CallbackInfo callbackInfo) {
		dataTracker.startTracking(TETHERED, false);
		dataTracker.startTracking(CANNIBAL_LEVEL, 0);
		dataTracker.startTracking(HUNGER_TIMER, 0);
	}
	
	private static float getFoodModifier(int level) {
		if (level >= 300) {
			return 1.6f;
		}
		if (level >= 250) {
			return 1.5f;
		}
		if (level >= 200) {
			return 1.4f;
		}
		if (level >= 150) {
			return 1.3f;
		}
		if (level >= 100) {
			return 1.2f;
		}
		if (level >= 50) {
			return 1.1f;
		}
		return 1;
	}
	
	@Mixin(ServerPlayerEntity.class)
	private static abstract class Server extends PlayerEntity {
		public Server(World world, BlockPos pos, float yaw, GameProfile profile) {
			super(world, pos, yaw, profile);
		}
		
		@Inject(method = "copyFrom", at = @At("TAIL"))
		public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo callbackInfo) {
			if (alive) {
				CannibalAccessor.of(this).ifPresent(cannibalAccessor -> CannibalAccessor.of(oldPlayer).ifPresent(oldCannibalAccessor -> {
					cannibalAccessor.setTethered(oldCannibalAccessor.getTethered());
					cannibalAccessor.setCannibalLevel(oldCannibalAccessor.getCannibalLevel());
					cannibalAccessor.setHungerTimer(oldCannibalAccessor.getHungerTimer());
				}));
			}
		}
	}
}
