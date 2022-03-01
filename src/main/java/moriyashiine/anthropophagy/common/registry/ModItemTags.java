/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItemTags {
	public static final TagKey<Item> KNIVES = TagKey.of(Registry.ITEM_KEY, new Identifier("fabric", "tools/knives"));

	public static final TagKey<Item> FLESH = TagKey.of(Registry.ITEM_KEY, new Identifier(Anthropophagy.MOD_ID, "flesh"));
}
