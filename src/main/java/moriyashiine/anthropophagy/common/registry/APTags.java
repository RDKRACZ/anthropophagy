package moriyashiine.anthropophagy.common.registry;

import moriyashiine.anthropophagy.common.Anthropophagy;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class APTags {
	public static final Tag<Item> FLESH = TagRegistry.item(new Identifier(Anthropophagy.MODID, "flesh"));
}
