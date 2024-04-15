package dev.symo.finz.ui.components;

import dev.symo.finz.modules.settings.ModuleSetting;
import dev.symo.finz.ui.ParentUiComponent;
import dev.symo.finz.ui.UiComponent;
import net.minecraft.client.gui.DrawContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModuleComponent extends BoxComponent {
    private HashMap<ModuleSetting, UiComponent> settings;

    public ModuleComponent(String title, int x, int y, int width, int height, boolean draggable, boolean scrollable, ParentUiComponent parent, Collection<ModuleSetting> settings) {
        super(title, x, y, width, height, draggable, scrollable, parent);
        this.settings = new HashMap<>();
        for (ModuleSetting setting : settings) {
            this.settings.put(setting, getComponentForSetting(setting));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int i = 0;
        var x = getX() + 10;
        var y = getY() + 20;
        for (Map.Entry<ModuleSetting, UiComponent> entry : settings.entrySet()) {
            y = getY() + 20 + i * 20;
            var setting = entry.getKey();
            var res = context.drawTextWithShadow(textRenderer, setting.getName(), x, y, 0xFFFFFFFF);
            var component = entry.getValue();
            component.setXSanitized(x + 10);
            component.setYSanitized(y);
            component.render(context, mouseX, mouseY, delta);
            i++;
        }
    }


    private UiComponent getComponentForSetting(ModuleSetting setting) {
        return new BoxComponent(setting.getName(), 0, 0, 100, 20, false, false, null);
    }
}
