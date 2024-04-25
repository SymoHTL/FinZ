package dev.symo.finz.ui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.navigation.GuiNavigationType;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.input.KeyCodes;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class SliderWidget extends ClickableWidget {
    private static final Identifier TEXTURE = new Identifier("widget/slider");
    private static final Identifier HIGHLIGHTED_TEXTURE = new Identifier("widget/slider_highlighted");
    private static final Identifier HANDLE_TEXTURE = new Identifier("widget/slider_handle");
    private static final Identifier HANDLE_HIGHLIGHTED_TEXTURE = new Identifier("widget/slider_handle_highlighted");
    protected double value;
    private final double min;
    private final double max;
    private boolean sliderFocused;
    @Nullable
    private Consumer<Double> changedListener;

    public SliderWidget(int x, int y, int width, int height, Text text, double value, double min, double max) {
        super(x, y, width, height, text);
        this.value = value;
        this.min = min;
        this.max = max;
    }

    private Identifier getTexture() {
        return this.isFocused() && !this.sliderFocused ? HIGHLIGHTED_TEXTURE : TEXTURE;
    }

    private Identifier getHandleTexture() {
        return !this.hovered && !this.sliderFocused ? HANDLE_TEXTURE : HANDLE_HIGHLIGHTED_TEXTURE;
    }

    @Override
    protected MutableText getNarrationMessage() {
        return Text.translatable("gui.narrate.slider", this.getMessage());
    }

    @Override
    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, this.getNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                builder.put(NarrationPart.USAGE, Text.translatable("narration.slider.usage.focused"));
            } else {
                builder.put(NarrationPart.USAGE, Text.translatable("narration.slider.usage.hovered"));
            }
        }
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        context.drawGuiTexture(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        int handlePos = (int) ((this.value - this.min) / (this.max - this.min) * (this.width - 8));  // Normalize handle position
        context.drawGuiTexture(this.getHandleTexture(), this.getX() + handlePos, this.getY(), 8, this.getHeight());
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 16777215 : 10526880;
        this.drawScrollableText(context, minecraftClient.textRenderer, 2, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.setValueFromMouse(mouseX);
    }

    @Override
    public void setFocused(boolean focused) {
        super.setFocused(focused);
        if (!focused) {
            this.sliderFocused = false;
        } else {
            GuiNavigationType guiNavigationType = MinecraftClient.getInstance().getNavigationType();
            if (guiNavigationType == GuiNavigationType.MOUSE || guiNavigationType == GuiNavigationType.KEYBOARD_TAB) {
                this.sliderFocused = true;
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (KeyCodes.isToggle(keyCode)) {
            this.sliderFocused = !this.sliderFocused;
            return true;
        } else {
            if (this.sliderFocused) {
                boolean bl = keyCode == GLFW.GLFW_KEY_LEFT;
                if (bl || keyCode == GLFW.GLFW_KEY_RIGHT) {
                    float f = bl ? -1.0F : 1.0F;
                    this.setValue(this.value + (double) (f / (float) (this.width - 8)));
                    return true;
                }
            }

            return false;
        }
    }

    private void setValueFromMouse(double mouseX) {
        double normValue = (mouseX - (double) (this.getX() + 4)) / (double) (this.width - 8);  // Normalize the value based on mouse position
        this.setValue(this.min + normValue * (this.max - this.min));  // Scale and offset the value
    }

    public void setChangedListener(@Nullable Consumer<Double> changedListener) {
        this.changedListener = changedListener;
    }

    public void setValue(double value) {
        double clampedValue = MathHelper.clamp(value, this.min, this.max);  // Ensure the value is within bounds
        if (this.value != clampedValue) {
            this.value = clampedValue;
            this.onChanged(this.value);
        }
    }

    private void onChanged(double val) {
        if (this.changedListener != null) this.changedListener.accept(val);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        this.setValueFromMouse(mouseX);
        super.onDrag(mouseX, mouseY, deltaX, deltaY);
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.playDownSound(MinecraftClient.getInstance().getSoundManager());
    }
}
