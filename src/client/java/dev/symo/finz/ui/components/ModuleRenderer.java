package dev.symo.finz.ui.components;

import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.Modules;
import dev.symo.finz.modules.settings.*;
import dev.symo.finz.ui.ConfigScreen;
import dev.symo.finz.ui.widgets.SliderWidget;
import dev.symo.finz.util.ColorUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModuleRenderer implements Drawable, Element, Selectable, ParentElement {
    private int x;
    private int y;

    private boolean dragging;
    private int dragOffsetX;
    private int dragOffsetY;

    private final int parentWidth = FinZClient.mc.getWindow().getWidth();
    private final int parentHeight = FinZClient.mc.getWindow().getHeight();

    private final TextRenderer textRenderer = FinZClient.mc.textRenderer;
    private final ConfigScreen scr;

    @Nullable
    private Element focused;

    private final ArrayList<Element> children = new ArrayList<>();

    private final GridWidget gridWidget = new GridWidget();

    private final Text text;

    private final int headerHeight;

    private final int minWidth;

    public ModuleRenderer(Text text, int x, int y, Collection<ModuleSetting> settings, ConfigScreen screen) {
        this.text = text;
        this.scr = screen;
        headerHeight = (int) (textRenderer.fontHeight * 1.5 + 4);
        var settingWidth = settings.stream()
                .mapToInt(setting -> textRenderer.getWidth(setting.getName())).max().orElse(0) + 5;
        minWidth = Math.max(settingWidth, textRenderer.getWidth(text) + 5);

        gridWidget.getMainPositioner().margin(5);
        GridWidget.Adder adder = gridWidget.createAdder(1);
        for (ModuleSetting setting : settings) {
            var widget = getComponentForSetting(setting);
            adder.add(widget);
            children.add(widget);
        }
        setXSanitized(x);
        setYSanitized(y);
        gridWidget.refreshPositions();
        gridWidget.forEachChild(screen::addDrawableChild);
        screen.addDrawableChild(this);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fillGradient(x, y, x + getWidth(), y + getHeight(), 0x80000000, 0x08000000);

        int color;
        if (Modules.settings.isRainbow()) {
            color = ColorUtil.GetRainbowColor();
        } else {
            color = Modules.settings.getColorFromHex();
        }
        context.drawBorder(x, y, getWidth(), getHeight(), color);

        context.drawCenteredTextWithShadow(textRenderer, text,
                x + getWidth() / 2, y + 4, 0xFFFFFFFF);
    }

    public final int getWidth() {
        return Math.max(minWidth, gridWidget.getWidth());
    }

    public final int getHeight() {
        return gridWidget.getHeight() + headerHeight;
    }

    public final int getX() {
        return Math.max(0, Math.min(x, parentWidth / 2 - getWidth()));
    }

    public final int getY() {
        return Math.max(0, Math.min(y, parentHeight / 2 - getHeight()));
    }

    public void setXSanitized(int x) {
        this.x = x;
        this.x = this.getX();
        gridWidget.setX(this.x);
    }

    public void setYSanitized(int y) {
        this.y = y;
        this.y = this.getY();
        gridWidget.setY(this.y + headerHeight);
    }

    @Override
    public List<? extends Element> children() {
        return children;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (!isMouseOver(mouseX, mouseY)) return false;

        if (!isDragging()) {
            setDragging(true);
            dragOffsetX = (int) (getX() - mouseX);
            dragOffsetY = (int) (getY() - mouseY);
        }

        if (isDragging()) {
            setXSanitized(((int) mouseX + dragOffsetX));
            setYSanitized((int) mouseY + dragOffsetY);
            return true;
        }

        return false;
    }

    @Override
    public boolean isDragging() {
        return dragging;
    }

    @Override
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
        if (!dragging) {
            dragOffsetX = 0;
            dragOffsetY = 0;
            scr.savePositions();
        }
    }

    @Nullable
    @Override
    public Element getFocused() {
        return focused;
    }

    @Override
    public void setFocused(@Nullable Element focused) {
        if (this.focused != null) this.focused.setFocused(false);

        if (focused != null) focused.setFocused(true);

        this.focused = focused;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + getWidth() && mouseY >= y && mouseY <= y + getHeight();
    }

    @Override
    public void setFocused(boolean focused) { // this isnt focusable
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    public SelectionType getType() {
        return SelectionType.FOCUSED;
    }

    @Override
    public boolean isNarratable() {
        return false;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }

    private ClickableWidget getComponentForSetting(ModuleSetting setting) {
        ClickableWidget widget;
        var text = Text.literal(setting.getName());
        switch (setting.getType()) {
            case TEXT -> {
                if (!(setting instanceof StringSetting stringSetting))
                    throw new IllegalArgumentException("Setting is not a StringSetting");
                var textField = new TextFieldWidget(textRenderer, 0, 0, 100, 20, text);
                textField.setText(stringSetting.getValue());
                textField.setChangedListener(stringSetting::setValue);
                textField.setTooltip(Tooltip.of(stringSetting.getDescription()));
                widget = textField;
            }
            case INT -> {
                if (!(setting instanceof IntSetting intSetting))
                    throw new IllegalArgumentException("Setting is not a DoubleSetting");
                var textField = new TextFieldWidget(textRenderer, 0, 0, 100, 20, text);
                textField.setText("%d".formatted(intSetting.getValue()));
                textField.setTooltip(Tooltip.of(intSetting.getDescription()));
                textField.setChangedListener(s -> {
                    try {
                        intSetting.setValue(Integer.parseInt(s));
                    } catch (NumberFormatException ignored) {
                        // clear the text field
                        textField.setText("%d".formatted(intSetting.getValue()));
                    }
                });
                widget = textField;
            }
            case DECIMAL -> {
                if (!(setting instanceof DoubleSetting doubleSetting))
                    throw new IllegalArgumentException("Setting is not a DoubleSetting");
                var textField = new TextFieldWidget(textRenderer, 0, 0, 100, 20, text);
                textField.setText("%s".formatted(doubleSetting.getValue()));
                textField.setTooltip(Tooltip.of(doubleSetting.getDescription()));
                textField.setChangedListener(s -> {
                    try {
                        doubleSetting.setValue(Double.parseDouble(s));
                    } catch (NumberFormatException ignored) {
                        // clear the text field
                        textField.setText("%s".formatted(doubleSetting.getValue()));
                    }
                });
                widget = textField;
            }
            case BOOLEAN -> {
                if (!(setting instanceof BoolSetting boolSetting)) {
                    throw new IllegalArgumentException("Setting is not a BoolSetting");
                }
                widget = CheckboxWidget.builder(text, textRenderer)
                        .pos(0, 0)
                        .callback((w, b) -> boolSetting.setValue(b))
                        .checked(boolSetting.getValue())
                        .tooltip(Tooltip.of(boolSetting.getDescription()))
                        .build();
            }
            //case SELECT -> {
            //}
            //case COLOR -> {
            //}
            case DECIMAL_SLIDER -> {
                if (!(setting instanceof DoubleSetting doubleSetting))
                    throw new IllegalArgumentException("Setting is not a DoubleSetting");
                var slider = new dev.symo.finz.ui.widgets.SliderWidget(0, 0, 100, 20, text,
                        doubleSetting.getValue(), doubleSetting.getMin(), doubleSetting.getMax());
                slider.setChangedListener(doubleSetting::setValue);
                slider.setTooltip(Tooltip.of(doubleSetting.getDescription()));
                widget = slider;
            }
            case PERCENT_SLIDER -> {
                if (!(setting instanceof DoubleSetting doubleSetting))
                    throw new IllegalArgumentException("Setting is not a DoubleSetting");
                var slider = new SliderWidget(0, 0, 100, 20, text,
                        doubleSetting.getValue(), doubleSetting.getMin(), doubleSetting.getMax());
                slider.setTooltip(Tooltip.of(doubleSetting.getDescription()));
                slider.setChangedListener(doubleSetting::setValue);
                widget = slider;
            }
            default -> throw new IllegalStateException("Unexpected value: " + setting.getType());
        }
        return widget;
    }
}
