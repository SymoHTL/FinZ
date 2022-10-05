package aids.modules.impl.misc;


import aids.modules.Category;
import aids.modules.Module;
import org.lwjgl.input.Keyboard;

public class Timer extends Module {
    public Timer() {
        super("Timer", "super hot", Keyboard.KEY_N, Category.MISC);
    }

    @Override
    public void onEnable() {
        mc.timer.timerSpeed = 5f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}
