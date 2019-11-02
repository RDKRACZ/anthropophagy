package moriyashiine.cannibalism.common.item;

import moriyashiine.cannibalism.Cannibalism;
import moriyashiine.cannibalism.common.Handler;
import moriyashiine.cannibalism.common.capability.CannibalCapability;
import moriyashiine.cannibalism.common.entity.WendigoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class FleshItem extends Item {
	public FleshItem(int hunger) {
		super(new Item.Properties().group(Cannibalism.group).food(new Food.Builder().hunger(hunger).saturation(0.5f).build()));
	}
	
	@Override
	@Nonnull
	public ITextComponent getDisplayName(ItemStack stack) {
		String name = "Human";
		if (stack.hasTag()) name = Objects.requireNonNull(stack.getTag()).getString("name");
		return new TranslationTextComponent(getTranslationKey(stack), name);
	}
	
	@Override
	@Nonnull
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull LivingEntity entity) {
		if (!world.isRemote) {
			if (stack.getItem() == Cannibalism.RegistryEvents.corrupt_flesh) entity.addPotionEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1));
			entity.getCapability(CannibalCapability.CAP).ifPresent(c -> {
				if (!c.tethered) c.level = Math.min(c.level + 10, 300);
				if (c.level == 100) {
					entity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200));
					entity.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 200));
					Handler.dropSlot(entity, EquipmentSlotType.LEGS);
				}
				if (c.level == 150) Handler.dropSlot(entity, EquipmentSlotType.HEAD);
				if (c.level == 170) Handler.dropSlot(entity, EquipmentSlotType.FEET);
				if (c.level == 240) Handler.dropSlot(entity, EquipmentSlotType.CHEST);
				attemptSpawnWendigo(world, entity, c.level);
			});
		}
		return super.onItemUseFinish(stack, world, entity);
	}
	
	private void attemptSpawnWendigo(World world, LivingEntity target, int level) {
		float chance = 0;
		if (level > 0) {
			if (level >= 300) chance = 0.6f;
			else if (level >= 290) chance = 0.5f;
			else if (level >= 280) chance = 0.4f;
			else if (level >= 270) chance = 0.3f;
			else if (level >= 260) chance = 0.2f;
			else chance = 0.1f;
		}
		Random rand = world.rand;
		if (rand.nextFloat() < chance) {
			WendigoEntity wendigo = Cannibalism.RegistryEvents.wendigo.create(world);
			if (wendigo != null) {
				boolean valid = false;
				for (int i = 0; i < 8; i++) {
					if (wendigo.attemptTeleport(target.posX + MathHelper.nextInt(rand, -16, 16), target.posY + MathHelper.nextInt(rand, -6, 6), target.posZ + MathHelper.nextInt(rand, -16, 16), false)) {
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