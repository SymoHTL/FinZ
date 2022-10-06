package finz.modules.impl.combat;

import finz.events.impl.EventGetPackets;
import finz.modules.Category;
import finz.modules.Module;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "reduce knockback", Category.COMBAT);
        //setKey(Keyboard.KEY_V);
    }

    @Override
    public void onGetPackets(EventGetPackets packets) {
        if (packets.getPackets() instanceof S12PacketEntityVelocity || packets.getPackets() instanceof S27PacketExplosion) {
            packets.setCancelled(true);
        }
    }
}
