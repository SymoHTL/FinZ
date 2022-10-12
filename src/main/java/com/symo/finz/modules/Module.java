package com.symo.finz.modules;

import com.symo.finz.FinZ;
import com.symo.finz.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import org.lwjgl.input.Keyboard;


public class Module {

    protected Minecraft mc = FinZ.mc;
    protected KeyBinding keyBind;
    protected boolean enabled;

    public Module(String description, int key, String category) {
        keyBind = new KeyBinding(description, key, category);
        //ClientRegistry.registerKeyBinding(keyBind);
    }

    public Module(String description, String category) {
        keyBind = new KeyBinding(description, Keyboard.KEY_NONE, category);
        //ClientRegistry.registerKeyBinding(new KeyBinding(description, Keyboard.KEY_NONE, category));
    }

    @Override
    public String toString() {
        return keyBind.getKeyDescription() + " " + Keyboard.getKeyName(keyBind.getKeyCode());
    }

    public void setKey(int key) {
        this.keyBind.setKeyCode(key);
    }

    public void removeKey() {
        this.keyBind.setKeyCode(Keyboard.KEY_NONE);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        enabled = true;
        ChatUtils.sendMessage("Enabled " + keyBind.getKeyDescription());
        onEnable();
    }

    public void disable() {
        enabled = false;
        ChatUtils.sendMessage("Disabled " + keyBind.getKeyDescription());
        onDisable();
    }

    public void toggle() {
        if (enabled)
            disable();
        else
            enable();
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onServerJoin() {
    }

    public void onServerLeave() {
    }

    public void onRender() {
    }

    public void onKey() {
    }

    public void onUpdate() {
    }

    public void onJump() {
    }

    public void onMillisecond() {
    }


    public void sendPacket(Packet packet) {
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

    public void silentSendPacket(Packet packet) {
        mc.thePlayer.sendQueue.getNetworkManager().sendPacket(packet);
    }


}
