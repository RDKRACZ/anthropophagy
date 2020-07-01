package moriyashiine.wendigoism.common.item;

import moriyashiine.wendigoism.WDConfig;
import moriyashiine.wendigoism.common.entity.WendigoEntity;
import moriyashiine.wendigoism.common.misc.WDDataTrackers;
import moriyashiine.wendigoism.common.registry.WDEntityTypes;
import moriyashiine.wendigoism.common.registry.WDItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
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
		if (!world.isClient && user instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) user;
			if (stack.getItem() == WDItems.corrupt_flesh) {
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 1));
			}
			if (!WDDataTrackers.getTethered(player)) {
				WDDataTrackers.setWendigoLevel(player, Math.min(WDDataTrackers.getWendigoLevel(player) + 10, 300));
			}
			if (WDDataTrackers.getWendigoLevel(player) == 100) {
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200));
			}
			if (WDConfig.INSTANCE.enableWendigo) {
				attemptSpawnWendigo(world, user, WDDataTrackers.getWendigoLevel(player));
			}
		}
		return super.finishUsing(stack, world, user);
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
		Random random = world.random;
		if (random.nextFloat() < chance) {
			WendigoEntity wendigo = WDEntityTypes.wendigo.create(world);
			if (wendigo != null) {
				boolean valid = false;
				BlockPos pos = target.getBlockPos();
				for (int i = 0; i < 8; i++) {
					if (wendigo.teleport(pos.getX() + MathHelper.nextInt(random, -16, 16), pos.getY() + MathHelper.nextInt(random, -6, 6), pos.getZ() + MathHelper.nextInt(random, -16, 16), false)) {
						valid = true;
						break;
					}
				}
				if (valid) {
					world.spawnEntity(wendigo);
					wendigo.setTarget(target);
				}
			}
		}
	}
}