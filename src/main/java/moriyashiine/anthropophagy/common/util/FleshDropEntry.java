/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.anthropophagy.common.util;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public record FleshDropEntry(Item raw_drop, Item cooked_drop) {
	public static final Map<EntityType<?>, FleshDropEntry> DROP_MAP = new HashMap<>();
}
