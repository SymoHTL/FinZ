package dev.symo.finz.modules;

import dev.symo.finz.FinZClient;
import dev.symo.finz.events.KeyEvent;
import dev.symo.finz.events.impl.EventManager;
import dev.symo.finz.events.listeners.HudRenderListener;
import dev.symo.finz.events.listeners.TickListener;
import dev.symo.finz.events.listeners.WorldRenderListener;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;

public class ModuleManager {

    public static ArrayList<AModule> _modules = new ArrayList<>();

    private static KeyBinding _openGuiKey;

    public static KeyBinding specialKey;


    public static void init() {
        _modules.addAll(Modules.all);


        specialKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.finz.specialKey", InputUtil.Type.MOUSE, 2, "category.finz.config"));
        // register gui keybind
        _openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.finz.opengui",
                InputUtil.Type.KEYSYM, 77, "category.finz.config"));

        for (AModule module : _modules)
            if (module._keybind != null)
                KeyBindingHelper.registerKeyBinding(module._keybind);


        // register events
        KeyEvent.KEY_EVENT.register(() -> {
            if (_openGuiKey.wasPressed()) {
                FinZClient.showConfigScreen();
            }
            return ActionResult.PASS;
        });
        ClientTickEvents.END_CLIENT_TICK.register(mc ->
                EventManager.fire(TickListener.UpdateEvent.INSTANCE));
        WorldRenderEvents.END.register(context ->
                EventManager.fire(new WorldRenderListener.RenderEvent(context.matrixStack(), context.tickDelta(), context)));
        HudRenderCallback.EVENT.register((c, t) ->
                EventManager.fire(new HudRenderListener.HudRenderEvent(c, t)));
    }

    public static AModule getModule(String name) {
        return _modules.stream().filter(module -> module._name.equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

}
