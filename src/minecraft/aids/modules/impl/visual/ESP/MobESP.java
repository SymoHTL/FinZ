package aids.modules.impl.visual.ESP;

import aids.events.impl.EventRender;
import aids.modules.Category;
import aids.modules.Module;
import aids.util.esp.EntityESPUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import org.lwjgl.input.Keyboard;

public class MobESP extends Module {
    public MobESP() {
        super("MobESP", "Highlights mobs", Category.VISUAL);
        setKey(Keyboard.KEY_NUMPAD2);
    }

    @Override
    public void onRender(EventRender r) {
        for (Entity e : mc.theWorld.getLoadedEntityList()) {
            if (e instanceof EntityMob) {
                EntityESPUtil.drawESP((EntityMob)e);
            }
        }
    }


}
