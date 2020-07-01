package moriyashiine.wendigoism.common.mixin;

import moriyashiine.wendigoism.common.registry.WDEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultAttributeRegistry.class)
public class WDEntityAttributes
{
	@Inject(method = "get", at = @At("HEAD"), cancellable = true)
	private static void get(EntityType<? extends LivingEntity> type, CallbackInfoReturnable<DefaultAttributeContainer> info)
	{
		DefaultAttributeContainer container = WDEntityTypes.ATTRIBUTES.get(type);
		if (container != null)
		{
			info.setReturnValue(container);
		}
	}
	
	@Inject(method = "hasDefinitionFor", at = @At("HEAD"), cancellable = true)
	private static void hasDefinitionFor(EntityType<?> type, CallbackInfoReturnable<Boolean> info)
	{
		if (WDEntityTypes.ATTRIBUTES.containsKey(type))
		{
			info.setReturnValue(true);
		}
	}
}