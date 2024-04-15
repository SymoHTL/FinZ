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
        var parent = new ParentUiComponent("FinZ Config", 0, 0,
                FinZClient.mc.getWindow().getWidth(), FinZClient.mc.getWindow().getHeight(),
                false, false, null, 0);
        AModule[] modules = Modules.all.toArray(new AModule[0]);
        for (int i = 0; i < modules.length; i++) {
            var comp = new ModuleComponent(modules[i]._name, i * 250, 0, 200, 200, true, false, parent, modules[i].getSettings());
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
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        var flag = false;
        for (Element element : this.children()) {
            if (element.mouseClicked(mouseX, mouseY, button)) {
                this.setFocused(element);
                if (button == 0) {
                    this.setDragging(true);
                }

                flag = true;
                if (!(element instanceof ParentUiComponent))
                    return true;
            }
            if (element instanceof ParentUiComponent parent) {
                if (parent.isDraggable() && parent.isMouseOver(mouseX, mouseY)) {
                    this.setFocused(parent);
                    if (button == 0) this.setDragging(true);
                    return true;
                }
                if (flag){
                    return true;
                }
            }
        }

        return false;
    }
}
