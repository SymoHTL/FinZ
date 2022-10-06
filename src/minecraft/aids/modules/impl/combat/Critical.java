package aids.modules.impl.combat;

import aids.events.impl.EventMotion;
import aids.modules.Category;
import aids.modules.Module;
import aids.util.Timer;

public class Critical extends Module {
    public Timer timer = new Timer();

    public Critical() {
        super("Critical", "Always lands a crit", Category.COMBAT);
        //setKey(Keyboard.KEY_C);
    }

    // TODO this is shit
    public void onMotion(EventMotion event) {
        if (mc.thePlayer.isDead) {
            return;
        }

        if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
            if (timer.hasTimeElapsed(1000 / 10, true)) {
                mc.thePlayer.motionY = 0.1;
                mc.thePlayer.onGround = false;
            }
        }
    }
}
