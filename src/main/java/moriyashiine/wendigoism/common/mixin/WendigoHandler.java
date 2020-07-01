package moriyashiine.wendigoism.common.mixin;

import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.common.item.FleshItem;
import moriyashiine.wendigoism.common.item.KnifeItem;
import moriyashiine.wendigoism.common.misc.WDDataTrackers;
import moriyashiine.wendigoism.common.registry.WDItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Mixin(LivingEntity.class)
public class WendigoHandler {
	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			if (!thisObj.world.isClient) {
				int wendigoLevel = WDDataTrackers.getWendigoLevel(thisObj);
				if (wendigoLevel >= 100) {
					thisObj.dropStack(thisObj.getEquippedStack(EquipmentSlot.LEGS));
				}
				if (wendigoLevel >= 150) {
					thisObj.dropStack(thisObj.getEquippedStack(EquipmentSlot.HEAD));
				}
				if (wendigoLevel >= 170) {
					thisObj.dropStack(thisObj.getEquippedStack(EquipmentSlot.FEET));
				}
				if (wendigoLevel >= 240) {
					thisObj.dropStack(thisObj.getEquippedStack(EquipmentSlot.CHEST));
				}
				if (thisObj.age % 200 == 0) {
					for (StatusEffectInstance effect : getValidEffects(wendigoLevel)) {
						thisObj.addStatusEffect(effect);
					}
				}
				int hungerTimer = WDDataTrackers.getHungerTimer(thisObj);
				if (hungerTimer > 0) {
					WDDataTrackers.setHungerTimer(thisObj, --hungerTimer);
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
					if (!(stack.getItem() instanceof FleshItem) && stack.getItem() != WDItems.wendigo_heart) {
						int wendigoLevel = WDDataTrackers.getWendigoLevel(thisObj);
						if (!WDDataTrackers.getTethered(thisObj)) {
							WDDataTrackers.setWendigoLevel(thisObj, Math.max(wendigoLevel - 10, 0));
						}
						if (wendigoLevel >= 40) {
							FoodComponent food = stack.getItem().getFoodComponent();
							if (food != null) {
								int hungerTimer = WDDataTrackers.getHungerTimer(thisObj);
								WDDataTrackers.setHungerTimer(thisObj, (int) (hungerTimer + (food.getHunger() * getFoodModifier(wendigoLevel))));
							}
						}
					}
				}
			}
		}
	}
	
	@Inject(method = "damage", at = @At("HEAD"))
	private void dropFlesh(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		Object obj = this;
		if (obj instanceof LivingEntity) {
			LivingEntity thisObj = (LivingEntity) obj;
			World world = thisObj.world;
			if (!world.isClient) {
				if (source.getAttacker() instanceof LivingEntity && ((LivingEntity) source.getAttacker()).getMainHandStack().getItem() instanceof KnifeItem) {
					KnifeItem.DROPS.stream().filter(e -> e.type == thisObj.getType()).findFirst().ifPresent(c -> {
						if (world.random.nextFloat() * WDConfig.INSTANCE.damageNeeded < amount) {
							ItemStack drop = new ItemStack(thisObj.getFireTicks() > 0 ? c.fireDrop : c.normalDrop);
							if (drop.getItem() instanceof FleshItem) {
								drop.getOrCreateTag().putString("name", thisObj.getDisplayName().getString());
							}
							BlockPos pos = thisObj.getBlockPos();
							world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), drop));
						}
					});
				}
			}
		}
	}
	
	@Inject(method = "onDeath", at = @At("HEAD"))
	private void dropHeart(DamageSource source, CallbackInfo callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			World world = thisObj.world;
			if (!world.isClient) {
				if (WDDataTrackers.getTethered(thisObj)) {
					thisObj.dropStack(new ItemStack(WDItems.wendigo_heart));
					
				}
			}
		}
	}
	
	private List<StatusEffectInstance> getValidEffects(int level) {
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
	
	private float getFoodModifier(int level) {
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
}