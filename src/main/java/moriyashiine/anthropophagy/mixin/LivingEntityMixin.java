package moriyashiine.anthropophagy.mixin;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.entity.PigluttonEntity;
import moriyashiine.anthropophagy.common.item.FleshItem;
import moriyashiine.anthropophagy.common.item.KnifeItem;
import moriyashiine.anthropophagy.common.registry.APRecipeTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	@Inject(method = "damage", at = @At("RETURN"))
	private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (callbackInfo.getReturnValue() && !world.isClient) {
			if (source.getAttacker() instanceof PigluttonEntity || (source.getAttacker() instanceof LivingEntity && ((LivingEntity) source.getAttacker()).getMainHandStack().getItem() instanceof KnifeItem)) {
				world.getRecipeManager().listAllOfType(APRecipeTypes.FLESH_DROP_RECIPE_TYPE).forEach(recipe -> {
					if (recipe.entity_type == getType()) {
						if (world.random.nextFloat() * Anthropophagy.config.damageNeededForGuaranteedFleshDrop < amount) {
							ItemStack drop = new ItemStack(getFireTicks() > 0 ? recipe.cooked_drop : recipe.raw_drop);
							if (drop.getItem() instanceof FleshItem) {
								drop.getOrCreateTag().putString("OwnerName", getDisplayName().getString());
							}
							ItemScatterer.spawn(world, getX(), getY(), getZ(), drop);
						}
					}
				});
			}
		}
	}
}
