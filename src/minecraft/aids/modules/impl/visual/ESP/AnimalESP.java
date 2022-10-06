package aids.modules.impl.visual.ESP;

import aids.events.impl.EventRender;
import aids.modules.Category;
import aids.modules.Module;
import aids.util.esp.EntityESPUtil;
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
