package finz.modules.impl.movement;

import finz.modules.Category;
import finz.modules.Module;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {
    public Fly() {
        super("Fly", "Flyhacks", Keyboard.KEY_F, Category.MOVEMENT);
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
