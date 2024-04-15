package dev.symo.finz.ui.components;

import dev.symo.finz.ui.ParentUiComponent;
import net.minecraft.client.gui.DrawContext;


public class BoxComponent extends ParentUiComponent {
    public BoxComponent(String title, int x, int y, int width, int height, boolean draggable, boolean scrollable, ParentUiComponent parent) {
        super(title, x, y, width, height, draggable, scrollable, parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawCenteredTextWithShadow(textRenderer, getTitle(), getXCentered(), getY() + 4, 0xFFFFFFFF);
        context.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0x80000000);
    }

}
