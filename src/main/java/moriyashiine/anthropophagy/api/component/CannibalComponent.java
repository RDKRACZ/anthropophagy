package moriyashiine.anthropophagy.api.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import moriyashiine.anthropophagy.common.registry.APComponents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.Optional;

public class CannibalComponent implements ComponentV3, ServerTickingComponent {
	private final PlayerEntity player;
	private boolean tethered = false;
	private int cannibalLevel = 0, hungerTimer = 0;
	
	public CannibalComponent(PlayerEntity player) {
		this.player = player;
	}
	
	public boolean isTethered() {
		return tethered;
	}
	
	public void setTethered(boolean tethered) {
		this.tethered = tethered;
	}
	
	public int getCannibalLevel() {
		return cannibalLevel;
	}
	
	public void setCannibalLevel(int cannibalLevel) {
		this.cannibalLevel = cannibalLevel;
	}
	
	public int getHungerTimer() {
		return hungerTimer;
	}
	
	public void setHungerTimer(int hungerTimer) {
		this.hungerTimer = hungerTimer;
	}
	
	@Override
	public void readFromNbt(NbtCompound tag) {
		setTethered(tag.getBoolean("Tethered"));
		setCannibalLevel(tag.getInt("CannibalLevel"));
		setHungerTimer(tag.getInt("HungerTimer"));
	}
	
	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putBoolean("Tethered", isTethered());
		tag.putInt("CannibalLevel", getCannibalLevel());
		tag.putInt("HungerTimer", getHungerTimer());
	}
	
	@Override
	public void serverTick() {
		if (getCannibalLevel() >= 30) {
			player.dropStack(player.getEquippedStack(EquipmentSlot.LEGS).split(1));
		}
		if (getCannibalLevel() >= 50) {
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 410, 0, true, false));
			player.dropStack(player.getEquippedStack(EquipmentSlot.HEAD).split(1));
		}
		if (getCannibalLevel() >= 70) {
			player.dropStack(player.getEquippedStack(EquipmentSlot.FEET).split(1));
		}
		if (getCannibalLevel() >= 90) {
			player.dropStack(player.getEquippedStack(EquipmentSlot.CHEST).split(1));
		}
		if (getHungerTimer() > 0) {
			setHungerTimer(getHungerTimer() - 1);
			player.getHungerManager().setFoodLevel(Math.max(player.getHungerManager().getFoodLevel() - 1, 0));
		}
	}
	
	public static CannibalComponent get(PlayerEntity player) {
		return APComponents.CANNIBAL_COMPONENT.get(player);
	}
	
	public static Optional<CannibalComponent> maybeGet(PlayerEntity player) {
		return APComponents.CANNIBAL_COMPONENT.maybeGet(player);
	}
}
