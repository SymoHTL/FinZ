package com.symo.finz.modules;

import com.symo.finz.FinZ;
import com.symo.finz.modules.impl.combat.Aimbot;
import com.symo.finz.modules.impl.combat.Critical;
import com.symo.finz.modules.impl.combat.MobFarmer;
import com.symo.finz.modules.impl.gui.BlockInfo;
import com.symo.finz.modules.impl.gui.HUD;
import com.symo.finz.modules.impl.misc.PickUpItems;
import com.symo.finz.modules.impl.movement.*;
import com.symo.finz.modules.impl.player.AutoEat;
import com.symo.finz.modules.impl.player.FastPlace;
import com.symo.finz.modules.impl.player.NoFall;
import com.symo.finz.modules.impl.visual.BlockHighlighter;
import com.symo.finz.modules.impl.visual.FullBright;
import com.symo.finz.modules.impl.visual.esp.*;
import com.symo.finz.utils.MillisecondEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public static final List<Module> modules = new ArrayList<>();


    //TODO onMotion, onPacketSent, onPacketReceived, onPickBlock,

    public void init() {
        // Combat
        modules.add(new Aimbot());
        modules.add(new Critical());
        modules.add(new MobFarmer());
        // Misc
        modules.add(new PickUpItems());
        // Movement
        modules.add(new Fly());
        modules.add(new GoDown());
        modules.add(new GoUp());
        modules.add(new JetPack());
        modules.add(new ScaffoldWalk());
        modules.add(new Spider());
        modules.add(new Sprint());
        // Player
        modules.add(new AutoEat());
        modules.add(new FastPlace());
        modules.add(new NoFall());
        // Visual
        modules.add(new FullBright());
        modules.add(new AnimalESP());
        modules.add(new MobESP());
        modules.add(new PlayerESP());
        modules.add(new BlockRenderESP());
        modules.add(new FindOreESP());
        modules.add(new BlockHighlighter());
        // ui
        modules.add(new HUD());
        modules.add(new BlockInfo());

        for (Module module : modules) {
            ClientRegistry.registerKeyBinding(module.keyBind);
        }
    }

    @SubscribeEvent
    public void onMillisecond(MillisecondEvent event) {
        modules.forEach(Module::onMillisecond);
    }

    @SubscribeEvent
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        FinZ.mc.addScheduledTask(() -> modules.forEach(Module::onServerJoin));
    }

    @SubscribeEvent
    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        FinZ.mc.addScheduledTask(() -> modules.forEach(Module::onServerLeave));
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        modules.stream().filter(m -> m.enabled).forEach(Module::onRender);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        modules.stream().filter(module -> module.keyBind.isPressed()).forEach(Module::toggle);
        modules.stream().filter(m -> m.enabled).forEach(Module::onKey);
    }

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.entityLiving == FinZ.mc.thePlayer)
            modules.stream().filter(m -> m.enabled).forEach(Module::onJump);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        modules.stream().filter(m -> m.enabled).forEach(Module::onUpdate);
    }

    public Module getModule(String name) {
        for (Module module : modules)
            if (module.keyBind.getKeyDescription().equalsIgnoreCase(name))
                return module;
        return null;
    }


}
