package aids.modules.impl.combat;

import aids.events.impl.EventGetPackets;
import aids.modules.Category;
import aids.modules.Module;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "reduce knockback", 0, Category.COMBAT);
        setKey(Keyboard.KEY_V);
    }

    @Override
    public void onGetPackets(EventGetPackets packets) {
        if (packets.getPackets() instanceof S12PacketEntityVelocity || packets.getPackets() instanceof S27PacketExplosion) {
            packets.setCancelled(true);
        }
    }
}
