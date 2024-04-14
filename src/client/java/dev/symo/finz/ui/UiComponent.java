package dev.symo.finz.ui;

import dev.symo.finz.FinZClient;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public abstract class UiComponent {

    private String title;

    private int x; // left offset
    private int y;  // top offset
    private int width;
    private int height;

    private final ArrayList<UiComponent> children = new ArrayList<>();

    private final boolean draggable;
    private boolean dragging;
    private int dragOffsetX;
    private int dragOffsetY;

    private final boolean scrollable;
    private boolean scrolling;
    private int yScrollOffset;

    @Nullable
    private UiComponent parent;


    public UiComponent(String title, int x, int y, int width, int height, boolean draggable, boolean scrollable, @Nullable UiComponent parent) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.draggable = draggable;
        this.scrollable = scrollable;
        this.parent = parent;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final int rightX() {
        return x + width;
    }

    public final int bottomY() {
        return y + height;
    }

    public final int getX() {
        return Math.max(0, Math.min(x, FinZClient.mc.getWindow().getScaledWidth() - width));
    }

    public final int getY() {
        return Math.max(0, Math.min(y, FinZClient.mc.getWindow().getScaledHeight() - height));
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public final void setXSanitized(int x) {
        this.x = x;
        this.x = getX();
    }

    public final void setYSanitized(int y) {
        this.y = y;
        this.y = getY();
    }

    public final void startDragging(int mouseX, int mouseY) {
        dragging = true;
        dragOffsetX = getX() - mouseX;
        dragOffsetY = getY() - mouseY;
    }

    public final void dragTo(int mouseX, int mouseY) {
        if (!dragging || !draggable) return;
        setXSanitized(mouseX + dragOffsetX);
        setYSanitized(mouseY + dragOffsetY);
    }

    public final void stopDragging() {
        dragging = false;
        dragOffsetX = 0;
        dragOffsetY = 0;
    }

    public final void scroll(int amount) {
        if (!scrollable) return;
        yScrollOffset += amount;
    }

    public final void startDraggingScrollbar(int mouseY) {
        scrolling = true;
        dragOffsetY = yScrollOffset - mouseY;
    }

    public final void dragScrollbarTo(int mouseY) {
        if (!scrolling || !scrollable) return;
        yScrollOffset = mouseY + dragOffsetY;
    }

    public final void stopDraggingScrollbar() {
        scrolling = false;
        dragOffsetY = 0;
    }

    public final ArrayList<UiComponent> getChildren() {
        return children;
    }

    public final void addChildren(UiComponent... children) {
        Collections.addAll(this.children, children);
    }

    public final void removeChildren(UiComponent... children) {
        for (UiComponent child : children) {
            this.children.remove(child);
        }
    }

    public @Nullable UiComponent getParent() {
        return parent;
    }

    public void setParent(@Nullable UiComponent parent) {
        this.parent = parent;
    }

    public void render(DrawContext drawContext, float tickDelta, int mouseX, int mouseY) {
        // Render this component
        renderComponent(drawContext, tickDelta, mouseX, mouseY);

        // Render children
        for (UiComponent child : children) {
            child.render(drawContext, tickDelta, mouseX, mouseY);
        }
    }

    // Abstract method that each component must implement to render itself
    protected abstract void renderComponent(DrawContext drawContext, float tickDelta, int mouseX, int mouseY);

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) return false;

        for (UiComponent child : children) {
            if (child.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }

        return onMouseClicked(mouseX, mouseY, button);
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
    }

    protected abstract boolean onMouseClicked(double mouseX, double mouseY, int button);

}
