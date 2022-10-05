package aids.events.impl;

import aids.events.Event;
import net.minecraft.network.Packet;

public class EventGetPackets extends Event {
    public Packet packets;

    public EventGetPackets(Packet packet) {
        this.packets = packet;
    }

    public Packet getPackets() {
        return packets;
    }
}
