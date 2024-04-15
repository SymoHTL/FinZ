package dev.symo.finz.ui.components;

import dev.symo.finz.ui.ParentUiComponent;
import dev.symo.finz.ui.UiComponent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;

public class OptionComponent extends UiComponent {
    private final ClickableWidget widget;

    public OptionComponent(String optionName, int x, int y, int width, int height,
            boolean scrollable, ParentUiComponent parent, ClickableWidget widget) {
        super(optionName, x, y, width, height, scrollable, parent);
        this.widget = widget;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        //context.drawBorder(getXLeft(), getYTop(), getWidth(), getHeight(), 0xFFFFFFFF);
        context.drawTextWithShadow(textRenderer, title, getXLeft(), getYTop(), 0xFFFFFFFF);
        widget.setX(getXLeft() + textRenderer.getWidth(title));
        widget.setY(getYTop());
        widget.render(context, mouseX, mouseY, delta);
    }

    @Override
    public SelectionType getType() {
        return SelectionType.FOCUSED;
    }

    @Override
    public int getHeight() {
        return (int) (Math.max(textRenderer.fontHeight * 1.5, widget.getHeight()));
    }

    @Override
    public int getWidth() {
        return textRenderer.getWidth(title) + widget.getWidth();
    }
}
