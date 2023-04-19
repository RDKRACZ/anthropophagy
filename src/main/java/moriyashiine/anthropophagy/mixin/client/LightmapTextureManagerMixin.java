/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.mixin.client;

import moriyashiine.anthropophagy.common.registry.ModEntityComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
	@Shadow
	@Final
	private MinecraftClient client;

	@ModifyVariable(method = "update", at = @At("STORE"), ordinal = 6)
	private float anthropophagy$cannibalNightVision(float value) {
		if (client.player != null && client.player.getComponent(ModEntityComponents.CANNIBAL_LEVEL).getCannibalLevel() >= 50) {
			return Math.max(1, value);
		}
		return value;
	}
}