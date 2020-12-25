package moriyashiine.anthropophagy.mixin;

import com.mojang.authlib.GameProfile;
import moriyashiine.anthropophagy.api.accessor.CannibalAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}
	
	@Inject(method = "copyFrom", at = @At("TAIL"))
	public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo callbackInfo) {
		if (alive) {
			CannibalAccessor.of(this).ifPresent(cannibalAccessor -> CannibalAccessor.of(oldPlayer).ifPresent(oldCannibalAccessor -> {
				cannibalAccessor.setTethered(oldCannibalAccessor.getTethered());
				cannibalAccessor.setCannibalLevel(oldCannibalAccessor.getCannibalLevel());
				cannibalAccessor.setHungerTimer(oldCannibalAccessor.getHungerTimer());
			}));
		}
	}
}
