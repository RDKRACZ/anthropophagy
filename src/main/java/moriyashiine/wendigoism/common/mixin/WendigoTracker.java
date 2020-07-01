package moriyashiine.wendigoism.common.mixin;

import moriyashiine.wendigoism.common.misc.WDDataTrackers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(PlayerEntity.class)
public class WendigoTracker {
	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	public void readCustomDataFromTag(CompoundTag tag, CallbackInfo callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			WDDataTrackers.setTethered(thisObj, tag.getBoolean("tethered"));
			WDDataTrackers.setWendigoLevel(thisObj, tag.getInt("wendigoLevel"));
			WDDataTrackers.setHungerTimer(thisObj, tag.getInt("hungerTimer"));
		}
	}
	
	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	public void writeCustomDataToTag(CompoundTag tag, CallbackInfo callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			tag.putBoolean("tethered", WDDataTrackers.getTethered(thisObj));
			tag.putInt("wendigoLevel", WDDataTrackers.getWendigoLevel(thisObj));
			tag.putInt("hungerTimer", WDDataTrackers.getHungerTimer(thisObj));
		}
	}
	
	@Inject(method = "initDataTracker", at = @At("TAIL"))
	public void initDataTracker(CallbackInfo callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			thisObj.getDataTracker().startTracking(WDDataTrackers.TETHERED, false);
			thisObj.getDataTracker().startTracking(WDDataTrackers.WENDIGO_LEVEL, 0);
			thisObj.getDataTracker().startTracking(WDDataTrackers.HUNGER_TIMER, 0);
		}
	}
	
	@Mixin(ServerPlayerEntity.class)
	private static class Server {
		@Inject(method = "copyFrom", at = @At("TAIL"))
		public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo callbackInfo) {
			Object obj = this;
			if (alive && obj instanceof ServerPlayerEntity) {
				ServerPlayerEntity thisObj = (ServerPlayerEntity) obj;
				WDDataTrackers.setTethered(thisObj, WDDataTrackers.getTethered(oldPlayer));
				WDDataTrackers.setWendigoLevel(thisObj, WDDataTrackers.getWendigoLevel(oldPlayer));
				WDDataTrackers.setHungerTimer(thisObj, WDDataTrackers.getHungerTimer(oldPlayer));
			}
		}
	}
}