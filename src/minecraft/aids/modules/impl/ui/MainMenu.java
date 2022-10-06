package aids.modules.impl.ui;

import aids.BaseHiv;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class MainMenu extends GuiScreen {

    private static final String[] buttons = new String[]{"Singleplayer", "Multiplayer", "Hypixel", "Options", "Quit"};

    public MainMenu() {

    }

    public void initGui() {

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("aids/main.jpg"));
        drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);

        int counter = 0;
        for (String name : buttons) {
            float x = width / 1.5f;//(width / buttons.length) * counter + (width / buttons.length / 2) - (mc.fontRendererObj.getStringWidth(name) / 2);
            float firstElement = (height - ((fontRendererObj.FONT_HEIGHT + 20) * buttons.length) + 20) / 2f;
            float y = firstElement + ((20 + fontRendererObj.FONT_HEIGHT) * counter);//(height / 2.5f - 20) + (fontRendererObj.FONT_HEIGHT + 20) * counter;//(height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2);

            // float textLength = (fontRendererObj.FONT_HEIGHT + 20) * buttons.length;
            // float lastElement = firstElement + textLength - 20;
            // drawVerticalLine((int) x, 0, (int) firstElement, 0xffffffff);
            // drawVerticalLine((int) x, (int) lastElement, height, 0xffffffff);
            // drawVerticalLine((int) x - 40, (int) firstElement, (int) lastElement, 0xffffffff);


            boolean hovered = mouseX >= x - 40 && mouseX <= x + 40 && mouseY >= y - 2 && mouseY <= y + fontRendererObj.FONT_HEIGHT + 2;

            this.drawCenteredStringF(mc.fontRendererObj, name, x, y, hovered ? 0xAAAAAA : 0xFFFFFF);
            counter++;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(this.width / 2f, this.height / 2f, 0);
        GlStateManager.scale(3, 3, 1);
        GlStateManager.translate(-(width / 2f), -(height / 2f), 0);                                   //2.1f
        this.drawCenteredStringF(mc.fontRendererObj, BaseHiv.INSTANCE.getName(), width / 2.4f, height / 2f - mc.fontRendererObj.FONT_HEIGHT / 2f, 0xFFFFFF);
        GlStateManager.popMatrix();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int counter = 0;
        for (String name : buttons) {
            float x = width / 1.5f;
            float firstElement = (height - ((fontRendererObj.FONT_HEIGHT + 20) * buttons.length) + 20) / 2f;
            float y = firstElement + ((20 + fontRendererObj.FONT_HEIGHT) * counter);
            boolean hovered = mouseX >= x - 40 && mouseX <= x + 40 && mouseY >= y - 2 && mouseY <= y + fontRendererObj.FONT_HEIGHT + 2;

            if (hovered) {
                switch (counter) {
                    case 0:
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    case 1:
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    case 2:
                        //TODO change ip
                        mc.displayGuiScreen(new GuiConnecting(this, mc, "localhost", 25565));
                        break;
                    case 3:
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                    case 4:
                        mc.shutdown();
                        break;
                }
            }
            counter++;
        }
    }

    public void onGuiClosed() {

    }



}
