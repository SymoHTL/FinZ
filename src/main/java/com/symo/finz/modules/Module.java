package com.symo.finz.modules;

import com.symo.finz.FinZ;
import com.symo.finz.utils.ChatUtils;
import com.symo.finz.utils.MillisecondEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;

import java.util.Objects;
import java.util.stream.Collectors;

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
        ModuleManager.enableModule(ModuleManager.all.stream().filter(module ->
                Objects.equals(module.keyBind.getKeyDescription(), keyBind.getKeyDescription()))
                .collect(Collectors.toList()).get(0));
    }
    public void onDisable() {
        ModuleManager.disableModule(ModuleManager.all.stream().filter(module ->
                        Objects.equals(module.keyBind.getKeyDescription(), keyBind.getKeyDescription()))
                .collect(Collectors.toList()).get(0));
    }

    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {    }

    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {    }

    public void onRender(TickEvent.RenderTickEvent event) {    }

    public void onKey(InputEvent.KeyInputEvent event) {   }

    public void onTick(TickEvent.ClientTickEvent event) {    }

    public void onJump(LivingEvent.LivingJumpEvent event) {    }

    public void onMillisecond(MillisecondEvent event) {    }


    public void sendPacket(Packet packet) {
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

    public void silentSendPacket(Packet packet) {
        mc.thePlayer.sendQueue.getNetworkManager().sendPacket(packet);
    }


}
