package moriyashiine.wendigoism.api.accessor;

public interface WendigoAccessor {
	boolean getTethered();
	
	void setTethered(boolean tethered);
	
	int getWendigoLevel();
	
	void setWendigoLevel(int wendigolevel);
	
	int getHungerTimer();
	
	void setHungerTimer(int hungerTimer);
}