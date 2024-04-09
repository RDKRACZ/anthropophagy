/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.common.init;

import moriyashiine.anthropophagy.common.Anthropophagy;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
	public static class Items {
		public static final TagKey<Item> KNIVES = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "knives"));

		public static final TagKey<Item> FLESH = TagKey.of(RegistryKeys.ITEM, Anthropophagy.id("flesh"));
	}

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> PIGLUTTON_TARGETS = TagKey.of(RegistryKeys.ENTITY_TYPE, Anthropophagy.id("piglutton_targets"));
	}
}
