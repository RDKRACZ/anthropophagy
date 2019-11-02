package moriyashiine.cannibalism.common;

import moriyashiine.cannibalism.Cannibalism;
import moriyashiine.cannibalism.Config;
import moriyashiine.cannibalism.common.capability.CannibalCapability;
import moriyashiine.cannibalism.common.item.FleshItem;
import moriyashiine.cannibalism.common.item.KnifeItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class Handler {
	@SubscribeEvent
	public void livingDamage(LivingDamageEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.world;
		if (!world.isRemote) {
			if (event.getSource().getImmediateSource() instanceof LivingEntity && ((LivingEntity) event.getSource().getImmediateSource()).getHeldItemMainhand().getItem() instanceof KnifeItem) {
				KnifeItem.DROPS.stream().filter(e -> e.type == entity.getType()).findFirst().ifPresent(c -> {
					if (world.rand.nextFloat() * Config.COMMON.damagedNeeded.get() < event.getAmount()) {
						ItemStack drop = new ItemStack(entity.getFireTimer() > 0 ? c.fireDrop : c.normalDrop);
						if (drop.getItem() instanceof FleshItem) drop.getOrCreateTag().putString("name", entity.getDisplayName().getString());
						world.addEntity(new ItemEntity(world, entity.posX, entity.posY, entity.posZ, drop));
					}
				});
			}
		}
	}
	
	@SubscribeEvent
	public void livingDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.world;
		if (!world.isRemote) {
			entity.getCapability(CannibalCapability.CAP).ifPresent(c -> {
				c.level = 0;
				c.hungerTimer = 0;
				if (c.tethered) entity.entityDropItem(Cannibalism.RegistryEvents.wendigo_heart);
				c.tethered = false;
			});
		}
	}
	
	@SubscribeEvent
	public void livingUpdate(LivingEvent.LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.world;
		if (!world.isRemote) {
			entity.getCapability(CannibalCapability.CAP).ifPresent(c -> {
				if (entity.ticksExisted % 200 == 0) for (EffectInstance effect : getValidEffects(c.level)) entity.addPotionEffect(effect);
				if (c.hungerTimer > 0) {
					c.hungerTimer--;
					if (entity instanceof PlayerEntity) {
						FoodStats foodStats = ((PlayerEntity) entity).getFoodStats();
						foodStats.setFoodLevel(Math.max(foodStats.getFoodLevel() - 1, 0));
					}
				}
			});
		}
	}
	
	@SubscribeEvent
	public void finishUsingItem(LivingEntityUseItemEvent.Finish event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.world;
		if (!world.isRemote) {
			entity.getCapability(CannibalCapability.CAP).ifPresent(c -> {
				ItemStack stack = event.getItem();
				if (stack.isFood()) {
					if (!(stack.getItem() instanceof FleshItem) && stack.getItem() != Cannibalism.RegistryEvents.wendigo_heart) {
						if (!c.tethered) c.level = Math.max(c.level - 10, 0);
						if (c.level >= 40) {
							Food food = stack.getItem().getFood();
							if (food != null) c.hungerTimer += (food.getHealing() * getFoodModifier(c.level));
						}
					}
				}
			});
		}
	}
	
	@SubscribeEvent
	public void equipmentChange(LivingEquipmentChangeEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.world;
		if (!world.isRemote) {
			entity.getCapability(CannibalCapability.CAP).ifPresent(c -> {
				if (c.level >= 100) dropSlot(entity, EquipmentSlotType.LEGS);
				if (c.level >= 150) dropSlot(entity, EquipmentSlotType.HEAD);
				if (c.level >= 170) dropSlot(entity, EquipmentSlotType.FEET);
				if (c.level >= 240) dropSlot(entity, EquipmentSlotType.CHEST);
			});
		}
	}
	
	public static void dropSlot(LivingEntity entity, EquipmentSlotType type) {
		for (ItemStack stack : entity.getArmorInventoryList()) if (stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getEquipmentSlot() == type) entity.entityDropItem(stack.split(1));
	}
	
	private List<EffectInstance> getValidEffects(int level) {
		List<EffectInstance> fin = new ArrayList<>();
		if (level >= 30) fin.add(new EffectInstance(Effects.SPEED, 600));
		if (level >= 50) {
			fin.add(new EffectInstance(Effects.SPEED, 600, 1));
			fin.add(new EffectInstance(Effects.JUMP_BOOST, 600));
		}
		if (level >= 100) fin.add(new EffectInstance(Effects.SPEED, 600, 2));
		if (level >= 150) fin.add(new EffectInstance(Effects.STRENGTH, 600, 1));
		if (level >= 240) {
			fin.add(new EffectInstance(Effects.RESISTANCE, 600));
			fin.add(new EffectInstance(Effects.NIGHT_VISION, 600));
		}
		if (level >= 270) fin.add(new EffectInstance(Effects.STRENGTH, 600, 2));
		return fin;
	}
	
	private float getFoodModifier(int level) {
		if (level >= 300) return 1.6f;
		if (level >= 250) return 1.5f;
		if (level >= 200) return 1.4f;
		if (level >= 150) return 1.3f;
		if (level >= 100) return 1.2f;
		if (level >= 50) return 1.1f;
		return 1;
	}
}