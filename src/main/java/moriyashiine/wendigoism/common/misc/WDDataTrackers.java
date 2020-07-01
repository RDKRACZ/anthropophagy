package moriyashiine.wendigoism.common.misc;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;

public class WDDataTrackers {
	public static final TrackedData<Boolean> TETHERED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	public static final TrackedData<Integer> WENDIGO_LEVEL = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> HUNGER_TIMER = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
	
	public static void setTethered(PlayerEntity player, boolean tethered) {
		player.getDataTracker().set(TETHERED, tethered);
	}
	
	public static boolean getTethered(PlayerEntity playerEntity) {
		return playerEntity.getDataTracker().get(TETHERED);
	}
	
	public static void setWendigoLevel(PlayerEntity player, int wendigoLevel) {
		player.getDataTracker().set(WENDIGO_LEVEL, wendigoLevel);
	}
	
	public static int getWendigoLevel(PlayerEntity playerEntity) {
		return playerEntity.getDataTracker().get(WENDIGO_LEVEL);
	}
	
	public static void setHungerTimer(PlayerEntity player, int hungerTimer) {
		player.getDataTracker().set(HUNGER_TIMER, hungerTimer);
	}
	
	public static int getHungerTimer(PlayerEntity playerEntity) {
		return playerEntity.getDataTracker().get(HUNGER_TIMER);
	}
}