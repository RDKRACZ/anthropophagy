/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.common.item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.UUID;

public class KnifeItem extends SwordItem {
	private static final EntityAttributeModifier REACH_MODIFIER = new EntityAttributeModifier(UUID.fromString("036c3108-e940-4e06-93f0-8f826c7c4877"), "Weapon modifier", -0.5, EntityAttributeModifier.Operation.ADDITION);

	private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	public KnifeItem(ToolMaterial toolMaterial, Settings settings) {
		super(toolMaterial, 0, -2, settings);
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND) {
			if (attributeModifiers == null) {
				attributeModifiers = LinkedHashMultimap.create(super.getAttributeModifiers(slot));
				attributeModifiers.put(ReachEntityAttributes.ATTACK_RANGE, REACH_MODIFIER);
			}
			return attributeModifiers;
		}
		return super.getAttributeModifiers(slot);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user.isSneaking()) {
			user.attack(user);
			return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
		}
		return super.use(world, user, hand);
	}
}
