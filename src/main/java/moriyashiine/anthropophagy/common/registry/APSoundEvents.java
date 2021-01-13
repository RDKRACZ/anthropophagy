package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class APSoundEvents {
	private static final Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();
	
	public static final SoundEvent ENTITY_PIGLUTTON_AMBIENT = create("entity.piglutton.ambient");
	public static final SoundEvent ENTITY_PIGLUTTON_HURT = create("entity.piglutton.hurt");
	public static final SoundEvent ENTITY_PIGLUTTON_DEATH = create("entity.piglutton.death");
	
	private static SoundEvent create(String name) {
		Identifier id = new Identifier(Anthropophagy.MODID, name);
		SoundEvent soundEvent = new SoundEvent(id);
		SOUND_EVENTS.put(soundEvent, id);
		return soundEvent;
	}
	
	public static void init() {
		SOUND_EVENTS.keySet().forEach(soundEvent -> Registry.register(Registry.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent));
	}
}
