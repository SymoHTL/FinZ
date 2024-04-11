package dev.symo.finz.mixin.client;

import dev.symo.finz.modules.ModuleManager;
import dev.symo.finz.modules.impl.Zoom;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class ZoomMixin implements  AutoCloseable{
    @Inject(at = @At(value = "RETURN", ordinal = 1),
            method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D",
            cancellable = true)    private void onGetFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        var zoomModule = (Zoom) ModuleManager.getModule("Zoom");
        cir.setReturnValue(zoomModule.zoom(cir.getReturnValueD()));
    }

    @Override
    public void close() throws Exception {

    }
}