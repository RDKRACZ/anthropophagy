/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
	public static class Items {
		public static final TagKey<Item> KNIVES = TagKey.of(Registries.ITEM.getKey(), new Identifier("fabric", "tools/knives"));

		public static final TagKey<Item> FLESH = TagKey.of(Registries.ITEM.getKey(), Anthropophagy.id("flesh"));
	}
}
