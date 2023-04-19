/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.common.reloadlisteners;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.util.FleshDropEntry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class FleshDropsReloadListener extends JsonDataLoader implements IdentifiableResourceReloadListener {
	private static final Identifier ID = Anthropophagy.id("flesh_drops");

	public FleshDropsReloadListener(Gson gson, String dataType) {
		super(gson, dataType);
	}

	@Override
	public Identifier getFabricId() {
		return ID;
	}

	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		FleshDropEntry.DROP_MAP.clear();
		prepared.forEach((identifier, jsonElement) -> {
			JsonObject object = JsonHelper.asObject(jsonElement, identifier.toString());
			EntityType<?> entity_type = Registries.ENTITY_TYPE.get(identifier);
			Item raw_drop = Registries.ITEM.get(new Identifier(JsonHelper.getString(object, "raw_drop")));
			Item cooked_drop = Registries.ITEM.get(new Identifier(JsonHelper.getString(object, "cooked_drop")));
			FleshDropEntry.DROP_MAP.put(entity_type, new FleshDropEntry(raw_drop, cooked_drop));
		});
	}
}