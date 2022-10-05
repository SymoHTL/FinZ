package aids.modules.impl.motion;

import com.google.common.eventbus.Subscribe;
import aids.events.impl.EventUpdate;
import aids.modules.Category;
import aids.modules.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Automatically sprints", Keyboard.KEY_B, Category.MOTION);
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.thePlayer.moveForward > 0 && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isUsingItem())
            mc.thePlayer.setSprinting(true);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(false);
        super.onDisable();
    }
}
