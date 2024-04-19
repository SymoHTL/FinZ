package dev.symo.finz.mixin.client;

import dev.symo.finz.FinZClient;
import dev.symo.finz.mixininterfaces.IKeyBind;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(net.fabricmc.api.EnvType.CLIENT)
@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin implements IKeyBind {
    @Shadow
    private InputUtil.Key boundKey;

    @Override
    public boolean isActallyPressed() {
        long handle = FinZClient.mc.getWindow().getHandle();
        int code = boundKey.getCode();
        return InputUtil.isKeyPressed(handle, code);
    }

    @Override
    public void resetPressedState() {
        setPressed(isActallyPressed());
    }

    @Shadow
    public abstract void setPressed(boolean pressed);
}
