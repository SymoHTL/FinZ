package aids.modules.impl.misc;


import aids.modules.Category;
import aids.modules.Module;

public class Timer extends Module {
    public Timer() {
        super("Timer", "super hot", Category.MISC);
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
