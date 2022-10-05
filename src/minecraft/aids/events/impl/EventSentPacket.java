package aids.events.impl;

import aids.events.Event;
import net.minecraft.network.Packet;

public class EventSentPacket extends Event {
    public Packet packets;

    public EventSentPacket(Packet packet) {
        this.packets = packet;
    }

    public Packet getPackets() {
        return packets;
    }
}
