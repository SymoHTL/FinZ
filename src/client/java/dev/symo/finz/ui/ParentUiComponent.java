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

    public ParentUiComponent(String title, int x, int y, int width, int height, boolean draggable, boolean scrollable, ParentUiComponent parent) {
        super(title, x, y, width, height, scrollable, parent);
        this.draggable = draggable;
    }

    public final boolean isDragging() {
        return this.dragging;
    }

    public final void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    @Nullable
    public Element getFocused() {
        return this.focused;
    }

    public void setFocused(@Nullable Element focused) {
        if (this.focused != null) this.focused.setFocused(false);

        if (focused != null) focused.setFocused(true);

        this.focused = focused;
        System.out.println("Focused: " + focused);
    }

    public final void addChildren(UiComponent... children) {
        Collections.addAll(this.children, children);
    }

    public final void removeChildren(UiComponent... children) {
        for (UiComponent child : children) this.children.remove(child);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (!IsMouseOver(mouseX, mouseY)) return false;

        if (draggable && !isDragging()) {
            setDragging(true);
            dragOffsetX = x - (int) mouseX;
            dragOffsetY = y - (int) mouseY;
        }

        if (dragging) {
            setXSanitized(((int) mouseX + dragOffsetX));
            setYSanitized((int) mouseY + dragOffsetY);
            return true;
        }

        return false;
    }

    private boolean IsMouseOver(double mouseX, double mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
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
