package dev.symo.finz.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParentUiComponent extends UiComponent implements ParentElement {
    protected final ArrayList<UiComponent> children = new ArrayList<>();


    @Nullable
    private Element focused;
    private boolean dragging;
    private final boolean draggable;
    private int dragOffsetX;
    private int dragOffsetY;
    protected final int padding;

    public ParentUiComponent(String title, int x, int y, int width, int height, boolean draggable, boolean scrollable, ParentUiComponent parent) {
        this(title, x, y, width, height, draggable, scrollable, parent, 20);
    }

    public ParentUiComponent(String title, int x, int y, int width, int height, boolean draggable, boolean scrollable, ParentUiComponent parent, int padding) {
        super(title, x, y, width, height, scrollable, parent);
        this.draggable = draggable;
        this.padding = padding;
    }


    public void setXChildren(int x) {
        for (UiComponent child : children)
            child.x = x;
    }

    public void setYChildren(int y) {
        for (UiComponent child : children)
            child.y = y;
    }

    @Override
    public void setXSanitized(int x) {
        var prevX = getX();
        super.setXSanitized(x);
        var deltaX = getX() - prevX;
        setXChildren(getX() + deltaX);
    }

    @Override
    public void setYSanitized(int y) {
        var prevY = getY();
        super.setYSanitized(y);
        var deltaY = getY() - prevY;
        setYChildren(getY() + deltaY);
    }

    public boolean isDraggable() {
        return draggable;
    }

    public final boolean isDragging() {
        return this.dragging;
    }

    public final void setDragging(boolean dragging) {
        this.dragging = dragging;
        if (!dragging) {
            dragOffsetX = 0;
            dragOffsetY = 0;
        }
    }

    @Override
    public int getHeight() {
        int height = 0;
        for (UiComponent child : children) height = Math.max(height, child.getHeight());
        return height + padding;
    }

    @Override
    public int getWidth() {
        int width = 0;
        for (UiComponent child : children) width = Math.max(width, child.getWidth());
        return width + padding;
    }

    @Nullable
    public Element getFocused() {
        return this.focused;
    }

    public void setFocused(@Nullable Element focused) {
        if (this.focused != null) this.focused.setFocused(false);

        if (focused != null) focused.setFocused(true);

        this.focused = focused;
    }

    public final void addChildren(UiComponent... children) {
        Collections.addAll(this.children, children);
    }

    public final void removeChildren(UiComponent... children) {
        for (UiComponent child : children) this.children.remove(child);
    }

    //@Override
    //public boolean mouseClicked(double mouseX, double mouseY, int button) {
    //    if (!isMouseOver(mouseX, mouseY)) return false;

    //    for (UiComponent child : children) {
    //        if (child.mouseClicked(mouseX, mouseY, button)) {
    //            return true;
    //        }
    //    }

    //    return true;
    //}

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (!isMouseOver(mouseX, mouseY)) return false;

        if (draggable && !isDragging()) {
            setDragging(true);
            dragOffsetX = (int) (getX() - mouseX);
            dragOffsetY = (int) (getY() - mouseY);
        }

        if (dragging) {
            setXSanitized(((int) mouseX + dragOffsetX));
            setYSanitized((int) mouseY + dragOffsetY);
            return true;
        }

        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (UiComponent child : children) child.render(context, mouseX, mouseY, delta);
    }

    @Override
    public List<? extends Element> children() {
        return children;
    }

    @Override
    public SelectionType getType() {
        return SelectionType.FOCUSED;
    }
}
