package dev.symo.finz.modules;

import dev.symo.finz.FinZClient;
import dev.symo.finz.events.impl.EventManager;
import dev.symo.finz.modules.settings.BoolSetting;
import dev.symo.finz.modules.settings.ModuleSetting;
import dev.symo.finz.util.Category;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Stack;

public abstract class AModule {

    private static final String ENABLED = "enabled";

    protected static final EventManager EVENTS = FinZClient.eventManager;
    protected static final MinecraftClient mc = FinZClient.mc;

    public String _name;
    public Category _category;

    public KeyBinding _keybind;

    private final BoolSetting _enabled = new BoolSetting(ENABLED, "Enable", false);

    private final LinkedHashMap<String, ModuleSetting> settings = new LinkedHashMap<>();

    public AModule(String name, Category category) {
        this._name = name;
        this._category = category;

        settings.put(ENABLED, _enabled);
        _enabled.onChanged(this::checkEnabled);
    }

    public void addSetting(ModuleSetting setting) {
        settings.put(setting.getName().toLowerCase(), setting);
    }

    public Collection<ModuleSetting> getSettings() {
        return settings.values();
    }

    public final boolean isEnabled() {
        return ((BoolSetting)settings.get(ENABLED)).getValue();
    }

    public final void setEnabled(boolean enabled) {
        ((BoolSetting)settings.get(ENABLED)).setValue(enabled);

        if (enabled) onEnable();
        else onDisable();
    }

    public final void checkEnabled() {
        if (isEnabled()) onEnable();
        else onDisable();
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
