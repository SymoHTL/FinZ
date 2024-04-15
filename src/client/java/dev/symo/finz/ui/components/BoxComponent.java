package dev.symo.finz.ui.components;

import dev.symo.finz.ui.ParentUiComponent;
import net.minecraft.client.gui.DrawContext;


public class BoxComponent extends ParentUiComponent {
    public BoxComponent(String title, int x, int y, int width, int height, boolean draggable, boolean scrollable, ParentUiComponent parent) {
        super(title, x, y, width, height, draggable, scrollable, parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawCenteredTextWithShadow(textRenderer, this.getTitle(), this.getX(), this.getYTop() + 4, 0xFFFFFFFF);
        context.fill(this.getXLeft(), this.getYTop(),this. getXRight(), this.getYBottom(), 0x80000000);
    }

}
