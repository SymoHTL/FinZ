package finz.modules.impl.visual.ESP;

import finz.events.impl.EventRender;
import finz.modules.Category;
import finz.modules.Module;
import finz.util.esp.EntityESPUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import org.lwjgl.input.Keyboard;

public class AnimalESP extends Module {

    public AnimalESP() {
        super("FriendlyESP", "Highlights friends", Category.VISUAL);
        setKey(Keyboard.KEY_NUMPAD3);
    }

    @Override
    public void onRender(EventRender r) {
        for (Entity e : mc.theWorld.getLoadedEntityList()) {
            if (e instanceof EntityAnimal) {
                EntityESPUtil.drawESP((EntityAnimal)e);
            }
        }
    }
}
