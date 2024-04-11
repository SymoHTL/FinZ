package dev.symo.finz.modules;

import dev.symo.finz.FinZClient;
import dev.symo.finz.events.*;
import dev.symo.finz.modules.impl.MaterialScanner;
import dev.symo.finz.modules.impl.PathTracer;
import dev.symo.finz.modules.impl.WhatTheFAmILookingAt;
import dev.symo.finz.modules.impl.Zoom;
import dev.symo.finz.modules.impl.esp.ItemESP;
import dev.symo.finz.modules.impl.esp.MobEsp;
import dev.symo.finz.modules.impl.esp.PlayerESP;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;

public class ModuleManager {

    public static ArrayList<AModule> _modules = new ArrayList<>();

    private static KeyBinding _openGuiKey;

    public static KeyBinding specialKey;


    public static void init() {
        _modules.add(new Zoom());
        _modules.add(new WhatTheFAmILookingAt());
        _modules.add(new PathTracer());
        _modules.add(new MaterialScanner());
        _modules.add(new PlayerESP());
        _modules.add(new MobEsp());
        _modules.add(new ItemESP());


        specialKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.finz.specialKey", InputUtil.Type.MOUSE, 2, "category.finz.config"));
        // register gui keybind
        _openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.finz.opengui",
                InputUtil.Type.KEYSYM, 77, "category.finz.config"));


        for (AModule module : _modules)
            if (module._keybind != null)
                KeyBindingHelper.registerKeyBinding(module._keybind);



        // register events
        ClientTickEvents.END_CLIENT_TICK.register(ModuleManager::HandleTickEvent);
        KeyEvent.KEY_EVENT.register(() -> {
            HandleKeyEvent();
            return ActionResult.PASS;
        });
        ConfigChangeEvent.CHANGE.register(() -> {
            HandleConfigChangeEvent();
            return ActionResult.PASS;
        });
        WorldRenderEvents.END.register(ModuleManager::RenderWorld);
        HudRenderCallback.EVENT.register(ModuleManager::RenderHud);
    }

    private static void HandleConfigChangeEvent() {
        for (AModule module : _modules)
            module.onConfigChange();
    }

    private static void HandleKeyEvent() {
        if (_openGuiKey.wasPressed()) {
            FinZClient.showConfigScreen();
        }
    }

    public static AModule getModule(String name) {
        return _modules.stream().filter(module -> module._name.equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public static void HandleTickEvent(MinecraftClient minecraftClient) {
        for (AModule module : _modules)
            module.onTick();
    }

    public static void RenderWorld(WorldRenderContext worldRenderContext) {
        for (AModule module : _modules)
            module.onWorldRender(worldRenderContext.matrixStack(), worldRenderContext.tickDelta());
    }

    private static void RenderHud(DrawContext drawContext, float tickDelta) {
        for (AModule module : _modules)
            module.onHudRender(drawContext, tickDelta);
    }


}
