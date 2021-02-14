package moriyashiine.anthropophagy.api.accessor;

public interface CannibalAccessor {
	boolean getTethered();
	
	void setTethered(boolean tethered);
	
	int getCannibalLevel();
	
	void setCannibalLevel(int cannibalLevel);
	
	int getHungerTimer();
	
	void setHungerTimer(int hungerTimer);
}
