package finz.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import finz.events.impl.EventUpdate;
import finz.modules.Category;
import finz.modules.Module;
import org.lwjgl.input.Keyboard;

public class FastPlace extends Module {

    public FastPlace() {
        super("FastPlace", "place blocks faster", Keyboard.KEY_NONE, Category.PLAYER);
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
