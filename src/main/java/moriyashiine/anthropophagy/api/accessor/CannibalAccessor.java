package moriyashiine.anthropophagy.api.accessor;

import net.minecraft.entity.Entity;

import java.util.Optional;

public interface CannibalAccessor {
	static Optional<CannibalAccessor> of(Entity entity) {
		if (entity instanceof CannibalAccessor) {
			return Optional.of(((CannibalAccessor) entity));
		}
		return Optional.empty();
	}
	
	boolean getTethered();
	
	void setTethered(boolean tethered);
	
	int getCannibalLevel();
	
	void setCannibalLevel(int cannibalLevel);
	
	int getHungerTimer();
	
	void setHungerTimer(int hungerTimer);
}
