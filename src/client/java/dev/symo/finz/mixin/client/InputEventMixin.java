package dev.symo.finz.mixin.client;

import dev.symo.finz.events.KeyEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class InputEventMixin {

    @Inject(at = @At("TAIL"), method = "handleInputEvents")
    public void onKeyInput(CallbackInfo ci) {
        KeyEvent.KEY_EVENT.invoker().onKey();
    }
}
