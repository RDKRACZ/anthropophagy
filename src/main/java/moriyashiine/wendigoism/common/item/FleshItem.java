package moriyashiine.wendigoism.common.item;

import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.common.capability.CannibalCapability;
import moriyashiine.wendigoism.common.entity.WendigoEntity;
import moriyashiine.wendigoism.common.handler.WDHandler;
import moriyashiine.wendigoism.common.registry.WDEntityTypes;
import moriyashiine.wendigoism.common.registry.WDItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class FleshItem extends Item {
	public FleshItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	@Nonnull
	public ITextComponent getDisplayName(@Nonnull ItemStack stack) {
		ITextComponent fin = super.getDisplayName(stack);
		if (stack.hasTag()) {
			String name = Objects.requireNonNull(stack.getTag()).getString("name");
			if (!name.isEmpty()) {
				fin = new TranslationTextComponent(getTranslationKey(stack) + "_owned", name);
			}
		}
		return fin;
	}
	
	@Override
	@Nonnull
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull LivingEntity entity) {
		if (!world.isRemote) {
			if (stack.getItem() == WDItems.corrupt_flesh) {
				entity.addPotionEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1));
			}
			entity.getCapability(CannibalCapability.CAP).ifPresent(c -> {
				if (!c.tethered) {
					c.level = Math.min(c.level + 10, 300);
				}
				if (c.level == 100) {
					entity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200));
					entity.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 200));
					WDHandler.dropSlot(entity, EquipmentSlotType.LEGS);
				}
				if (c.level == 150) {
					WDHandler.dropSlot(entity, EquipmentSlotType.HEAD);
				}
				if (c.level == 170) {
					WDHandler.dropSlot(entity, EquipmentSlotType.FEET);
				}
				if (c.level == 240) {
					WDHandler.dropSlot(entity, EquipmentSlotType.CHEST);
				}
				if (WDConfig.INSTANCE.enableWendigo.get()) {
					attemptSpawnWendigo(world, entity, c.level);
				}
			});
		}
		return super.onItemUseFinish(stack, world, entity);
	}
	
	private void attemptSpawnWendigo(World world, LivingEntity target, int level) {
		float chance = 0;
		if (level > 0) {
			if (level >= 300) {
				chance = 0.6f;
			}
			else if (level >= 290) {
				chance = 0.5f;
			}
			else if (level >= 280) {
				chance = 0.4f;
			}
			else if (level >= 270) {
				chance = 0.3f;
			}
			else if (level >= 260) {
				chance = 0.2f;
			}
			else {
				chance = 0.1f;
			}
		}
		Random rand = world.rand;
		if (rand.nextFloat() < chance) {
			WendigoEntity wendigo = WDEntityTypes.wendigo.create(world);
			if (wendigo != null) {
				boolean valid = false;
				BlockPos pos = target.getPosition();
				for (int i = 0; i < 8; i++) {
					if (wendigo.attemptTeleport(pos.getX() + MathHelper.nextInt(rand, -16, 16), pos.getY() + MathHelper.nextInt(rand, -6, 6), pos.getZ() + MathHelper.nextInt(rand, -16, 16), false)) {
						valid = true;
						break;
					}
				}
				if (valid) {
					world.addEntity(wendigo);
					wendigo.setAttackTarget(target);
				}
			}
		}
	}
}