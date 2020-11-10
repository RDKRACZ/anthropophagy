package moriyashiine.anthropophagy.common.item;

import moriyashiine.anthropophagy.api.accessor.CannibalAccessor;
import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import moriyashiine.anthropophagy.common.registry.APEntityTypes;
import moriyashiine.anthropophagy.common.registry.APItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Random;

public class FleshItem extends Item {
	public FleshItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public Text getName(ItemStack stack) {
		Text fin = super.getName(stack);
		if (stack.hasTag()) {
			String name = Objects.requireNonNull(stack.getTag()).getString("name");
			if (!name.isEmpty()) {
				fin = new TranslatableText(getTranslationKey(stack) + "_owned", name);
			}
		}
		return fin;
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (!world.isClient) {
			CannibalAccessor.of(user).ifPresent(cannibalAccessor -> {
				int cannibalLevel = cannibalAccessor.getCannibalLevel();
				if (stack.getItem() == APItems.CORRUPT_FLESH) {
					user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 1));
				}
				if (!cannibalAccessor.getTethered()) {
					cannibalAccessor.setCannibalLevel(Math.min(cannibalLevel + 10, 300));
				}
				if (cannibalLevel == 100) {
					user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
					user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200));
				}
				if (Anthropophagy.CONFIG.enablePiglutton) {
					attemptSpawnPiglutton(world, user, cannibalLevel);
				}
			});
		}
		return super.finishUsing(stack, world, user);
	}
	
	private void attemptSpawnPiglutton(World world, LivingEntity target, int level) {
		float chance = 0;
		if (level > 100) {
			if (level >= 300) {
				chance = 1 / 10f;
			}
			else if (level >= 290) {
				chance = 1 / 15f;
			}
			else if (level >= 280) {
				chance = 1 / 20f;
			}
			else if (level >= 270) {
				chance = 1 / 25f;
			}
			else if (level >= 260) {
				chance = 1 / 30f;
			}
			else {
				chance = 1 / 35f;
			}
		}
		Random random = world.random;
		if (random.nextFloat() < chance) {
			PigluttonEntity piglutton = APEntityTypes.PIGLUTTON.create(world);
			if (piglutton != null) {
				boolean valid = false;
				BlockPos pos = target.getBlockPos();
				for (int i = 0; i < 8; i++) {
					if (piglutton.teleport(pos.getX() + MathHelper.nextInt(random, -16, 16), pos.getY() + MathHelper.nextInt(random, -6, 6), pos.getZ() + MathHelper.nextInt(random, -16, 16), false)) {
						valid = true;
						break;
					}
				}
				if (valid) {
					world.spawnEntity(piglutton);
					piglutton.setTarget(target);
				}
			}
		}
	}
}
