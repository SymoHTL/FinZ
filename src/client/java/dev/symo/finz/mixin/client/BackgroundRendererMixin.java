package dev.symo.finz.mixin.client;

import dev.symo.finz.modules.Modules;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackgroundRenderer.class)

public abstract class BackgroundRendererMixin {

    @Inject(at = @At("HEAD"),
            method = "getFogModifier(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/client/render/BackgroundRenderer$StatusEffectFogModifier;",
            cancellable = true)
    private static void onGetFogModifier(Entity entity, float tickDelta,
                                         CallbackInfoReturnable<Object> ci) {
        if (Modules.noBlindness.isEnabled())
            ci.setReturnValue(null);
    }
}
