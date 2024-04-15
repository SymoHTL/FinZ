package dev.symo.finz.ui;

import dev.symo.finz.FinZClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import org.jetbrains.annotations.Nullable;

public abstract class UiComponent implements Element, Drawable, Selectable {

    protected String title;

    protected int x; // left offset
    protected int y;  // top offset
    private int width;
    private int height;

    protected boolean isFocused;

    protected final boolean scrollable;

    protected TextRenderer textRenderer = FinZClient.mc.textRenderer;

    @Nullable
    private final ParentUiComponent parent;


    public UiComponent(String title, int x, int y, int width, int height, boolean scrollable, @Nullable ParentUiComponent parent) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    protected final int getParentWidth(){
        if (parent == null) return FinZClient.mc.getWindow().getWidth();
        else return parent.getWidth();
    }

    protected final int getParentHeight(){
        if (parent == null) return FinZClient.mc.getWindow().getHeight();
        else return parent.getHeight();
    }

    public final int getX() {
        return Math.max(0, Math.min(x, getParentWidth() / 2 - width));
    }

    public final int getY() {
        return Math.max(0, Math.min(y, getParentHeight() / 2 - height));
    }

    public final int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public final int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public final int getXCentered() {
        return width / 2 + getX();
    }

    public final int getYCentered() {
        return height / 2 + getY();
    }

    public final void setXSanitized(int x) {
        this.x = x;
        this.x = getX();
    }

    public final void setYSanitized(int y) {
        this.y = y;
        this.y = getY();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return false;
    }

    @Override
    public void setFocused(boolean focused) {
        isFocused = focused;
        System.out.println("Focused: " + isFocused + " on " + getTitle());
    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }

    @Override
    public int getNavigationOrder() {
        return 0;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public boolean isNarratable() {
        return false;
    }
}
