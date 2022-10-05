package aids.modules.impl.motion;

import aids.modules.Category;
import aids.modules.Module;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {
    public Fly() {
        super("Fly", "Flyhacks", Keyboard.KEY_F, Category.MOTION);
    }

    @Override
    public void onEnable() {
        mc.thePlayer.capabilities.allowFlying = true;
    }

    @Override
    public void onDisable() {
        if (!mc.thePlayer.capabilities.isCreativeMode)
            mc.thePlayer.capabilities.allowFlying = false;
    }
}
