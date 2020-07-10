package moriyashiine.wendigoism.common.misc;

public interface WendigoAccessor {
	boolean getTethered();
	
	void setTethered(boolean tethered);
	
	int getWendigoLevel();
	
	void setWendigoLevel(int wendigolevel);
	
	int getHungerTimer();
	
	void setHungerTimer(int hungerTimer);
}