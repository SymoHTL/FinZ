package com.symo.finz.modules;

import com.symo.finz.FinZ;
import com.symo.finz.modules.impl.combat.Aimbot;
import com.symo.finz.modules.impl.combat.Critical;
import com.symo.finz.modules.impl.combat.MobFarmer;
import com.symo.finz.modules.impl.misc.PickUpItems;
import com.symo.finz.modules.impl.misc.Timer;
import com.symo.finz.modules.impl.movement.*;
import com.symo.finz.modules.impl.player.AutoEat;
import com.symo.finz.modules.impl.player.FastPlace;
import com.symo.finz.modules.impl.player.NoFall;
import com.symo.finz.modules.impl.visual.FullBright;
import com.symo.finz.modules.impl.visual.esp.*;
import com.symo.finz.utils.ChatUtils;
import com.symo.finz.utils.MillisecondEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import sun.security.provider.ConfigFile;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public static List<Module> all = new ArrayList<>();

    private static final List<Module> EnabledModules = new ArrayList<>();

    public static void enableModule(Module module) {
        EnabledModules.add(module);
    }

    public static void disableModule(Module module) {
        EnabledModules.remove(module);
    }


    //TODO onMotion, onPacketSent, onPacketReceived, onPickBlock,

    public void init(){
        // Combat
        all.add(new Aimbot());
        all.add(new Critical());
        all.add(new MobFarmer());
        // Misc
        all.add(new PickUpItems());
        all.add(new Timer());
        // Movement
        all.add(new Fly());
        all.add(new GoDown());
        all.add(new GoUp());
        all.add(new JetPack());
        all.add(new ScaffoldWalk());
        all.add(new Spider());
        all.add(new Sprint());
        // Player
        all.add(new AutoEat());
        all.add(new FastPlace());
        all.add(new NoFall());
        // Visual
        all.add(new FullBright());
        all.add(new AnimalESP());
        all.add(new MobESP());
        all.add(new PlayerESP());
        all.add(new BlockRenderESP());
        all.add(new FindOreESP());

        for (Module module : all) {
            ClientRegistry.registerKeyBinding(module.keyBind);
        }
    }

    @SubscribeEvent
    public void onMillisecond(MillisecondEvent event) {
        EnabledModules.forEach(module -> module.onMillisecond(event));
    }

    @SubscribeEvent
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        FinZ.mc.addScheduledTask(() -> EnabledModules.forEach(m -> m.onServerJoin(event)));
    }

    @SubscribeEvent
    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        FinZ.mc.addScheduledTask(() -> EnabledModules.forEach(m -> m.onServerLeave(event)));
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        EnabledModules.forEach(m -> m.onRender(event));
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        all.stream().filter(module -> module.keyBind.isPressed()).forEach(Module::toggle);
        EnabledModules.forEach(m -> m.onKey(event));
    }

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.entityLiving == FinZ.mc.thePlayer)
            EnabledModules.forEach(m -> m.onJump(event));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        EnabledModules.forEach(m -> m.onTick(event));
    }
}
