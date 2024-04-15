package dev.symo.finz.ui.components;

import dev.symo.finz.modules.settings.BoolSetting;
import dev.symo.finz.modules.settings.DoubleSetting;
import dev.symo.finz.modules.settings.ModuleSetting;
import dev.symo.finz.modules.settings.StringSetting;
import dev.symo.finz.ui.ParentUiComponent;
import dev.symo.finz.ui.UiComponent;
import dev.symo.finz.ui.widgets.SliderWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModuleComponent extends BoxComponent {

    private int width;
    private int height;

    public ModuleComponent(String title, int x, int y, int width, int height, boolean draggable, boolean scrollable, ParentUiComponent parent, Collection<ModuleSetting> settings) {
        super(title, x, y, width, height, draggable, scrollable, parent);

        for (ModuleSetting setting : settings) {
            //this.width = Math.max(width, component.getWidth() + 10 + textRenderer.getWidth(setting.getName()));
            //this.height += component.getHeight() + 20;
            var widget = getComponentForSetting(setting);
            var component = new OptionComponent(setting.getName(), x, y, width, height, false, parent, widget);
            children.add(component);
            y += component.getHeight();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (UiComponent child : children) {
            child.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    private ClickableWidget getComponentForSetting(ModuleSetting setting) {
        ClickableWidget widget;
        var text = Text.literal(setting.getName());
        switch (setting.getType()) {
            case TEXT -> {
                if (!(setting instanceof StringSetting stringSetting))
                    throw new IllegalArgumentException("Setting is not a StringSetting");
                var textField = new TextFieldWidget(textRenderer, 0, 0, 100, 20, text);
                textField.setChangedListener(stringSetting::setValue);
                widget = textField;
            }
            //case NUMBER -> {
            //}
            case BOOLEAN -> {
                if (!(setting instanceof BoolSetting boolSetting))
                    throw new IllegalArgumentException("Setting is not a BoolSetting");
                widget = CheckboxWidget.builder(text, textRenderer)
                        .pos(x, y)
                        .callback((w, b) -> boolSetting.setValue(b))
                        .build();
            }
            //case SELECT -> {
            //}
            case DECIMAL -> {
                if (!(setting instanceof DoubleSetting doubleSetting))
                    throw new IllegalArgumentException("Setting is not a DoubleSetting");
                var textField = new TextFieldWidget(textRenderer, 0, 0, 100, 20, text);
                textField.setChangedListener(s -> {
                    try {
                        doubleSetting.setValue(Double.parseDouble(s));
                    } catch (NumberFormatException ignored) {
                        // clear the text field
                        textField.setText("0");
                    }
                });
                widget = textField;
            }
            //case COLOR -> {
            //}
            case DECIMAL_SLIDER -> {
                if (!(setting instanceof DoubleSetting doubleSetting))
                    throw new IllegalArgumentException("Setting is not a DoubleSetting");
                var slider = new SliderWidget(0, 0, 100, 20, text,
                        doubleSetting.getValue(), doubleSetting.getMin(), doubleSetting.getMax());
                slider.setChangedListener(doubleSetting::setValue);
                widget = slider;
            }
            case PERCENT_SLIDER -> {
                if (!(setting instanceof DoubleSetting doubleSetting))
                    throw new IllegalArgumentException("Setting is not a DoubleSetting");
                var slider = new SliderWidget(0, 0, 100, 20, text,
                        doubleSetting.getValue(), doubleSetting.getMin(), doubleSetting.getMax());
                slider.setChangedListener(doubleSetting::setValue);
                widget = slider;
            }
            default -> throw new IllegalStateException("Unexpected value: " + setting.getType());
        }
        return widget;
    }
}
