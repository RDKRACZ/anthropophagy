package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
	public static final SoundEvent ENTITY_PIGLUTTON_AMBIENT = new SoundEvent(new Identifier(Anthropophagy.MOD_ID, "entity.piglutton.ambient"));
	public static final SoundEvent ENTITY_PIGLUTTON_HURT = new SoundEvent(new Identifier(Anthropophagy.MOD_ID, "entity.piglutton.hurt"));
	public static final SoundEvent ENTITY_PIGLUTTON_DEATH = new SoundEvent(new Identifier(Anthropophagy.MOD_ID, "entity.piglutton.death"));

	public static void init() {
		Registry.register(Registry.SOUND_EVENT, ENTITY_PIGLUTTON_AMBIENT.getId(), ENTITY_PIGLUTTON_AMBIENT);
		Registry.register(Registry.SOUND_EVENT, ENTITY_PIGLUTTON_HURT.getId(), ENTITY_PIGLUTTON_HURT);
		Registry.register(Registry.SOUND_EVENT, ENTITY_PIGLUTTON_DEATH.getId(), ENTITY_PIGLUTTON_DEATH);
	}
}
