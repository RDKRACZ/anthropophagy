/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.anthropophagy.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;

public class TetheredComponent implements AutoSyncedComponent {
	private boolean tethered = false;

	@Override
	public void readFromNbt(NbtCompound tag) {
		tethered = tag.getBoolean("Tethered");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putBoolean("Tethered", tethered);
	}

	public boolean isTethered() {
		return tethered;
	}

	public void setTethered(boolean tethered) {
		this.tethered = tethered;
	}
}
