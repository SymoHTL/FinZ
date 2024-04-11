package dev.symo.finz.modules;

import dev.symo.finz.FinZClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;

public abstract class AModule {

    protected static MinecraftClient mc;

    static {
        mc = FinZClient.mc;
    }

    public String _name;
    public String _category;

    public KeyBinding _keybind;

    public AModule(String name, String category) {
        this._name = name;
        this._category = category;
    }

    public abstract boolean IsEnabled();

    public abstract void SetEnabled(boolean enabled);

    public void AfterEnableChange() {
        if (IsEnabled())
            onEnable();
        else
            onDisable();
    }

    public abstract void onConfigChange();

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onTick() {
    }

    public void onWorldRender(MatrixStack matrices, float partialTicks) {

    }

    public void onHudRender(DrawContext drawContext, float tickDelta){

    }
}
