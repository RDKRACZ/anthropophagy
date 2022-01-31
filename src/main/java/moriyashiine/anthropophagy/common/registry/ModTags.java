/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ModTags {
	public static final Tag<Item> KNIVES = TagFactory.ITEM.create(new Identifier("fabric", "tools/knives"));

	public static final Tag<Item> FLESH = TagFactory.ITEM.create(new Identifier(Anthropophagy.MOD_ID, "flesh"));
}
