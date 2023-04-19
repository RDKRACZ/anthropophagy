/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.common.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
	public static final FoodComponent FLESH = new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().build();
	public static final FoodComponent COOKED_FLESH = new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).meat().build();
	public static final FoodComponent CORRUPT_FLESH = new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 1), 1).build();
}
