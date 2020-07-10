package moriyashiine.wendigoism.common.mixin;

import com.mojang.authlib.GameProfile;
import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.common.item.FleshItem;
import moriyashiine.wendigoism.common.item.KnifeItem;
import moriyashiine.wendigoism.common.misc.WendigoAccessor;
import moriyashiine.wendigoism.common.registry.WDItems;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
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

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class WendigoHandler extends Entity implements WendigoAccessor {
	private boolean tethered = false;
	
	private int wendigoLevel = 0, hungerTimer = 0;
	
	public WendigoHandler(EntityType<?> type, World world) {
		super(type, world);
	}
	
	@Override
	public boolean getTethered() {
		return tethered;
	}
	
	@Override
	public void setTethered(boolean tethered) {
		this.tethered = tethered;
	}
	
	@Override
	public int getWendigoLevel() {
		return wendigoLevel;
	}
	
	@Override
	public void setWendigoLevel(int wendigoLevel) {
		this.wendigoLevel = wendigoLevel;
	}
	
	@Override
	public int getHungerTimer() {
		return hungerTimer;
	}
	
	@Override
	public void setHungerTimer(int hungerTimer) {
		this.hungerTimer = hungerTimer;
	}
	
	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot slot);
	
	@SuppressWarnings("UnusedReturnValue")
	@Shadow
	public abstract boolean addStatusEffect(StatusEffectInstance effect);
	
	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		if (!world.isClient) {
			int wendigoLevel = getWendigoLevel();
			if (wendigoLevel >= 100) {
				dropStack(getEquippedStack(EquipmentSlot.LEGS));
			}
			if (wendigoLevel >= 150) {
				dropStack(getEquippedStack(EquipmentSlot.HEAD));
			}
			if (wendigoLevel >= 170) {
				dropStack(getEquippedStack(EquipmentSlot.FEET));
			}
			if (wendigoLevel >= 240) {
				dropStack(getEquippedStack(EquipmentSlot.CHEST));
			}
			if (age % 200 == 0) {
				getValidEffects(wendigoLevel).forEach(this::addStatusEffect);
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
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			if (!world.isClient) {
				if (stack.isFood()) {
					if (!(stack.getItem() instanceof FleshItem) && stack.getItem() != WDItems.WENDIGO_HEART) {
						int wendigoLevel = getWendigoLevel();
						if (!getTethered()) {
							setWendigoLevel(Math.max(wendigoLevel - 10, 0));
						}
						if (wendigoLevel >= 40) {
							FoodComponent food = stack.getItem().getFoodComponent();
							if (food != null) {
								int hungerTimer = getHungerTimer();
								setHungerTimer((int) (hungerTimer + (food.getHunger() * getFoodModifier(wendigoLevel))));
							}
						}
					}
				}
			}
		}
	}
	
	@Inject(method = "damage", at = @At("HEAD"))
	private void dropFlesh(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (!world.isClient) {
			if (source.getAttacker() instanceof LivingEntity && ((LivingEntity) source.getAttacker()).getMainHandStack().getItem() instanceof KnifeItem) {
				KnifeItem.DROPS.stream().filter(e -> e.type == getType()).findFirst().ifPresent(c -> {
					if (world.random.nextFloat() * WDConfig.INSTANCE.damageNeeded < amount) {
						ItemStack drop = new ItemStack(getFireTicks() > 0 ? c.fireDrop : c.normalDrop);
						if (drop.getItem() instanceof FleshItem) {
							drop.getOrCreateTag().putString("name", getDisplayName().getString());
						}
						BlockPos pos = getBlockPos();
						world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), drop));
					}
				});
			}
		}
	}
	
	@Inject(method = "onDeath", at = @At("HEAD"))
	private void dropHeart(DamageSource source, CallbackInfo callbackInfo) {
		if (!world.isClient) {
			if (getTethered()) {
				dropStack(new ItemStack(WDItems.WENDIGO_HEART));
			}
		}
	}
	
	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	public void readCustomDataFromTag(CompoundTag tag, CallbackInfo callbackInfo) {
		setTethered(tag.getBoolean("Tethered"));
		setWendigoLevel(tag.getInt("WendigoLevel"));
		setHungerTimer(tag.getInt("HungerTimer"));
	}
	
	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	public void writeCustomDataToTag(CompoundTag tag, CallbackInfo callbackInfo) {
		tag.putBoolean("Tethered", getTethered());
		tag.putInt("WendigoLevel", getWendigoLevel());
		tag.putInt("HungerTimer", getHungerTimer());
	}
	
	private static List<StatusEffectInstance> getValidEffects(int level) {
		List<StatusEffectInstance> fin = new ArrayList<>();
		if (level >= 30) {
			fin.add(new StatusEffectInstance(StatusEffects.SPEED, 600));
		}
		if (level >= 50) {
			fin.add(new StatusEffectInstance(StatusEffects.SPEED, 600, 1));
			fin.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 600));
		}
		if (level >= 100) {
			fin.add(new StatusEffectInstance(StatusEffects.SPEED, 600, 2));
		}
		if (level >= 150) {
			fin.add(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 1));
		}
		if (level >= 240) {
			fin.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 600));
			fin.add(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 600));
		}
		if (level >= 270) {
			fin.add(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 2));
		}
		return fin;
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
	private static abstract class Server extends PlayerEntity implements WendigoAccessor {
		public Server(World world, BlockPos blockPos, GameProfile gameProfile) {
			super(world, blockPos, gameProfile);
		}
		
		@Inject(method = "copyFrom", at = @At("TAIL"))
		public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo callbackInfo) {
			if (alive) {
				WendigoAccessor oldWendigo = (WendigoAccessor) oldPlayer;
				setTethered(oldWendigo.getTethered());
				setWendigoLevel(oldWendigo.getWendigoLevel());
				setHungerTimer(oldWendigo.getHungerTimer());
			}
		}
	}
}