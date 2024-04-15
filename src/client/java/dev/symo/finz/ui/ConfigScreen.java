package dev.symo.finz.ui;

import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.impl.Modules;
import dev.symo.finz.ui.components.BoxComponent;
import dev.symo.finz.ui.components.ModuleComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ConfigScreen extends Screen {

    public ConfigScreen() {
        super(Text.literal("FinZ Config"));
        FinZClient.isConfigOpen = true;
    }

    @Override
    public void close() {
        FinZClient.isConfigOpen = false;
        super.close();
    }

    @Override
    protected void init() {
        var parent = new ParentUiComponent("FinZ Config", 0, 0, FinZClient.mc.getWindow().getWidth(),
                FinZClient.mc.getWindow().getHeight(), false, false, null);
        AModule[] modules = Modules.all.toArray(new AModule[0]);
        for (int i = 0; i < modules.length; i++) {
            var comp = new ModuleComponent(modules[i]._name, i * 120, 0, 100, 100, true, false, parent, modules[i].getSettings());
            addDrawableChild(comp);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (!super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            for (Element child : children())
                if (child.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) return true;
        } else return true;
        return false;
    }
}
