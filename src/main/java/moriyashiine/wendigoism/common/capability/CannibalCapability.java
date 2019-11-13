package moriyashiine.wendigoism.common.capability;

import moriyashiine.wendigoism.Wendigoism;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CannibalCapability implements ICapabilitySerializable<CompoundNBT> {
	@CapabilityInject(CannibalCapability.class)
	public static Capability<CannibalCapability> CAP = null;
	
	public int level = 0, hungerTimer = 0;
	public boolean tethered = false;
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		return CAP.orEmpty(cap, LazyOptional.of(() -> this));
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		return (CompoundNBT) CAP.writeNBT(this, null);
	}
	
	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		CAP.readNBT(this, null, nbt);
	}
	
	public static void register() {
		CapabilityManager.INSTANCE.register(CannibalCapability.class, new CannibalCapability.Storage(), CannibalCapability::new);
		MinecraftForge.EVENT_BUS.register(new Handler());
	}
	
	private static class Storage implements Capability.IStorage<CannibalCapability> {
		@Nullable
		@Override
		public INBT writeNBT(Capability<CannibalCapability> capability, CannibalCapability instance, Direction side) {
			CompoundNBT tag = new CompoundNBT();
			tag.putInt("level", instance.level);
			tag.putInt("hungerTimer", instance.hungerTimer);
			tag.putBoolean("tethered", instance.tethered);
			return tag;
		}
		
		@Override
		public void readNBT(Capability<CannibalCapability> capability, CannibalCapability instance, Direction side, INBT nbt) {
			CompoundNBT tag = (CompoundNBT) nbt;
			instance.level = tag.getInt("level");
			instance.hungerTimer = tag.getInt("hungerTimer");
			instance.tethered = tag.getBoolean("tethered");
		}
	}
	
	private static class Handler {
		private static final ResourceLocation KEY = new ResourceLocation(Wendigoism.MODID, "cannibal_data");
		
		@SubscribeEvent
		public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof PlayerEntity) event.addCapability(KEY, new CannibalCapability());
		}
		
		@SubscribeEvent
		public void clonePlayer(PlayerEvent.Clone event) {
			event.getPlayer().getCapability(CAP).ifPresent(c -> event.getOriginal().getCapability(CAP).ifPresent(e -> c.deserializeNBT(e.serializeNBT())));
		}
	}
}