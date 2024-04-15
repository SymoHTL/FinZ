package dev.symo.finz.ui.components;

import dev.symo.finz.ui.ParentUiComponent;
import dev.symo.finz.ui.UiComponent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.jetbrains.annotations.Nullable;

public class WidgetContainer extends UiComponent {

    private final ClickableWidget widget;

    public WidgetContainer(String title, int x, int y, int width, int height, boolean scrollable, @Nullable ParentUiComponent parent, ClickableWidget widget) {
        super(title, x, y, width, height, scrollable, parent);
        this.widget = widget;
    }

    @Override
    public int getHeight() {
        return widget.getHeight();
    }

    @Override
    public int getWidth() {
        return widget.getWidth();
    }

    @Override
    public void setYSanitized(int y) {
        super.setYSanitized(y);
        widget.setX(x);
    }

    @Override
    public void setXSanitized(int x) {
        super.setXSanitized(x);
        widget.setY(y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        widget.render(context, mouseX, mouseY, delta);
    }

    @Override
    public SelectionType getType() {
        return SelectionType.FOCUSED;
    }
}
