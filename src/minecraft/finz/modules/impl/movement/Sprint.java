package finz.modules.impl.movement;

import com.google.common.eventbus.Subscribe;
import finz.events.impl.EventUpdate;
import finz.modules.Category;
import finz.modules.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Automatically sprints", Keyboard.KEY_NONE, Category.MOVEMENT);
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
