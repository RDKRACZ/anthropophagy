/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.mixin;

import moriyashiine.anthropophagy.common.registry.ModComponents;
import net.minecraft.entity.player.HungerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
	@ModifyArg(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;add(IF)V"))
	private int anthropophagy$reduceHungerGained(int food) {
		if (ModComponents.playerCannibalLevel != -1) {
			food = Math.round(food * getFoodModifier(ModComponents.playerCannibalLevel));
			ModComponents.playerCannibalLevel = -1;
		}
		return food;
	}

	@ModifyArg(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;add(IF)V"))
	private float anthropophagy$reduceSaturationGained(float saturation) {
		if (ModComponents.playerCannibalLevel != -1) {
			saturation *= getFoodModifier(ModComponents.playerCannibalLevel);
			ModComponents.playerCannibalLevel = -1;
		}
		return saturation;
	}

	@Unique
	private static float getFoodModifier(int level) {
		if (level >= 70) {
			return 0.4F;
		}
		if (level >= 60) {
			return 0.5F;
		}
		if (level >= 50) {
			return 0.6F;
		}
		if (level >= 40) {
			return 0.7F;
		}
		if (level >= 30) {
			return 0.8F;
		}
		if (level >= 20) {
			return 0.9F;
		}
		return 1;
	}
}
