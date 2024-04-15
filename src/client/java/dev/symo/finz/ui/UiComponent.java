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

    protected int x; // x center
    protected int y;  // y center
    private int minWidth;
    private int minHeight;

    protected boolean isFocused;

    protected final boolean scrollable;

    protected TextRenderer textRenderer = FinZClient.mc.textRenderer;

    @Nullable
    private final ParentUiComponent parent;


    public UiComponent(String title, int x, int y, int width, int height, boolean scrollable, @Nullable ParentUiComponent parent) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.minWidth = width;
        this.minHeight = height;
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
        return x + minWidth / 2;
    }

    public final int bottomY() {
        return y + minHeight / 2;
    }

    protected final int getParentWidth() {
        if (parent == null) return FinZClient.mc.getWindow().getWidth();
        else return parent.getMinWidth();
    }

    protected final int getParentHeight() {
        if (parent == null) return FinZClient.mc.getWindow().getHeight();
        else return this.parent.getMinHeight();
    }

    public final int getXLeft() {
        return this.getX() - this.getWidth() / 2;
    }

    public final int getYTop() {
        return this.getY() - this.getHeight() / 2;
    }

    public final int getXRight() {
        return this.getX() + this.getWidth() / 2;
    }

    public final int getYBottom() {
        return this.getY() + this.getHeight() / 2;
    }

    public final int getX() {
        return Math.max(minWidth / 2, Math.min(x, this.getParentWidth() / 2 - this.getWidth() / 2));
    }

    public final int getY() {
        return Math.max(minHeight / 2, Math.min(y, this.getParentHeight() / 2 - this.getHeight() / 2));
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getHeight() {
        return minHeight;
    }

    public int getWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setXSanitized(int x) {
        this.x = x;
        this.x = this.getX();
    }

    public void setYSanitized(int y) {
        this.y = y;
        this.y = this.getY();
    }


    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= this.getXLeft() && mouseX <= this.getXRight() && mouseY >= this.getYTop() && mouseY <= this.getYBottom();
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
    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public boolean isNarratable() {
        return false;
    }
}
