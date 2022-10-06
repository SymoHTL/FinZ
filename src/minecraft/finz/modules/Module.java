package finz.modules;

import finz.events.impl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class Module {
    public String name;
    public String description;
    public int key;
    public boolean enabled;
    public Category category;
    public Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String description, int key, Category category) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.enabled = false;
        this.category = category;
    }

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.enabled = false;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled)
            onEnable();
         else
            onDisable();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (enabled)
            onEnable();
        else
            onDisable();
        this.enabled = enabled;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void onEnable() {
        // BaseHiv.INSTANCE.getBus().register(this);
        // System.out.println("Module " + name + " enabled!");
    }

    public void onDisable() {
        // BaseHiv.INSTANCE.getBus().unregister(this);
        // System.out.println("Module " + name + " disabled!");
    }

    public void onRender(EventRender e) {

    }

    public void on2D(Event2D e) {

    }

    public void onGetPackets(EventGetPackets e) {

    }

    public void onPickBlock(EventPickBlock e) {

    }

    public void onPacketSent(EventSentPacket e) {

    }

    public void onUpdate(EventUpdate e) {

    }

    public void onMotion(EventMotion e) {

    }

    public void onKey(EventKey e) {

    }

    public void sendPacket(Packet packet) {
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

    public void silentSendPacket(Packet packet) {
        mc.thePlayer.sendQueue.addToSentQueueSilent(packet);
    }
}

