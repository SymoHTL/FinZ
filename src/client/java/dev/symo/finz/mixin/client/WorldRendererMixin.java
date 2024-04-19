package dev.symo.finz.mixin.client;

import dev.symo.finz.modules.Modules;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(at = @At("HEAD"),
            method = "hasBlindnessOrDarkness(Lnet/minecraft/client/render/Camera;)Z",
            cancellable = true)
    private void onHasBlindnessOrDarkness(Camera camera, CallbackInfoReturnable<Boolean> ci) {
        if (Modules.noBlindness.isEnabled())
            ci.setReturnValue(false);
    }
}
