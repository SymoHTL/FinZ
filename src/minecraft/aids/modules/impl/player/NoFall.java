package aids.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import aids.events.impl.EventUpdate;
import aids.modules.Category;
import aids.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "prevent fall damage", Category.PLAYER);
        setKey(Keyboard.KEY_O);
    }

    @Subscribe
    public void onUpdate(EventUpdate p) {
        if (mc.thePlayer.fallDistance > 2.5f) {
            mc.thePlayer.setVelocity(mc.thePlayer.getVelocityX(), -0.46f, mc.thePlayer.getVelocityZ());
            silentSendPacket(new C03PacketPlayer(true));
            mc.thePlayer.fallDistance = 0;
        }
    }
}
