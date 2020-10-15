package moriyashiine.wendigoism.api.accessor;

import net.minecraft.entity.Entity;

import java.util.Optional;

public interface WendigoAccessor {
	static Optional<WendigoAccessor> of(Entity entity) {
		if (entity instanceof WendigoAccessor) {
			return Optional.of(((WendigoAccessor) entity));
		}
		return Optional.empty();
	}
	
	boolean getTethered();
	
	void setTethered(boolean tethered);
	
	int getWendigoLevel();
	
	void setWendigoLevel(int wendigolevel);
	
	int getHungerTimer();
	
	void setHungerTimer(int hungerTimer);
}
