package aids.modules.impl.visual.ESP;

import aids.events.impl.EventRender;
import aids.modules.Category;
import aids.modules.Module;
import aids.util.esp.EntityESPUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

public class PlayerESP extends Module {

    public PlayerESP() {
        super("PlayerESP", "Highlights players", Category.VISUAL);
        setKey(Keyboard.KEY_NUMPAD1);
    }

    @Override
    public void onRender(EventRender r){
        for (Entity e : mc.theWorld.getLoadedEntityList()) {
            if (e instanceof EntityPlayer && e != mc.thePlayer) {
                EntityESPUtil.drawESP((EntityPlayer)e);
            }
        }
    }
}
