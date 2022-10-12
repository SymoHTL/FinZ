package com.symo.finz.modules;

import com.symo.finz.FinZ;
import com.symo.finz.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import org.lwjgl.input.Keyboard;

import java.util.Objects;


public class Module {

    protected Minecraft mc = FinZ.mc;
    protected KeyBinding keyBind;
    protected boolean enabled;

    protected boolean shouldReactivate = false;

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

    public void enable(String message) {
        enabled = true;
        if (!Objects.equals(message, "")) ChatUtils.sendMessage("Enabled " + keyBind.getKeyDescription() + " -- reason: " + message);
        else ChatUtils.sendMessage("Enabled " + keyBind.getKeyDescription());
        onEnable();
    }

    public void disable(String message) {
        enabled = false;
        if (!Objects.equals(message, "")) ChatUtils.sendMessage("Disabled " + keyBind.getKeyDescription() + " -- reason: " + message);
        else ChatUtils.sendMessage("Disabled " + keyBind.getKeyDescription());
        onDisable();
    }

    public void toggle() {
        if (enabled)
            disable("");
        else
            enable("");
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onServerJoin() {
        enabled = shouldReactivate;
    }

    public void onServerLeave() {
        shouldReactivate = enabled;
        enabled = false;
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
