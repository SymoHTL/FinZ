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

public abstract class AModule {

    protected static final EventManager EVENTS = FinZClient.eventManager;
    protected static final MinecraftClient mc = FinZClient.mc;

    public String _name;
    public Category _category;

    public KeyBinding _keybind;

    private final LinkedHashMap<String, ModuleSetting> settings = new LinkedHashMap<>();

    public AModule(String name, Category category) {
        this._name = name;
        this._category = category;

        settings.put("enabled", new BoolSetting("enabled", "Enable", false));
    }

    public void addSetting(ModuleSetting setting) {
        settings.put(setting.getName().toLowerCase(), setting);
    }

    public Collection<ModuleSetting> getSettings() {
        return settings.values();
    }

    public final boolean isEnabled() {
        return ((BoolSetting)settings.get("enabled")).getValue();
    }

    public final void setEnabled(boolean enabled) {
        ((BoolSetting)settings.get("enabled")).setValue(enabled);

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
