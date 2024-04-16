package dev.symo.finz.ui;

import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.Modules;
import dev.symo.finz.ui.components.ModuleRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
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
        AModule[] modules = Modules.all.toArray(new AModule[0]);
        var x = 10; // Initial X position
        var y = 10; // Initial Y position
        int maxWidthInColumn = 0; // Track the maximum width in the current column

        for (AModule module : modules) {
            var renderer = new ModuleRenderer(Text.literal(module._name), x, y, module.getSettings(), this);
            y += renderer.getHeight() + 10; // Move down after placing each module

            // Update the maxWidthInColumn with the widest renderer in this column
            if (renderer.getWidth() > maxWidthInColumn) {
                maxWidthInColumn = renderer.getWidth();
            }

            // Check if adding another module would exceed the window height
            if (y + renderer.getHeight() > this.height) {
                x += maxWidthInColumn + 10; // Move to a new column to the right
                y = 10; // Reset Y to top
                maxWidthInColumn = 0; // Reset the maxWidth for the new column
            }
        }
    }


    public <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement) {
        return super.addDrawableChild(drawableElement);
    }

    public <T extends Drawable> T addDrawable(T drawable) {
        super.addDrawable(drawable);
        return drawable;
    }

    @Override
    public boolean shouldPause() {
        return false;
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
                if (!(element instanceof ModuleRenderer))
                    return true;
            }
            if (element instanceof ModuleRenderer parent) {
                if (parent.isMouseOver(mouseX, mouseY)) {
                    this.setFocused(parent);
                    if (button == 0) this.setDragging(true);
                    return true;
                }
                if (flag) {
                    return true;
                }
            }
        }

        return false;
    }
}
