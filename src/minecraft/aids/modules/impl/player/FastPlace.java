package aids.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import aids.events.impl.EventUpdate;
import aids.modules.Category;
import aids.modules.Module;
import org.lwjgl.input.Keyboard;

public class FastPlace extends Module {

    public FastPlace() {
        super("FastPlace", "place blocks faster", Keyboard.KEY_L, Category.PLAYER);
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        mc.rightClickDelayTimer = 0;
    }

    @Override
    public void onDisable() {
        mc.rightClickDelayTimer = 4;
        super.onDisable();
    }
}
