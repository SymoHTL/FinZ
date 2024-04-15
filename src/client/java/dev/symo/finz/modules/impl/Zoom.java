package dev.symo.finz.modules.impl;

import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.DoubleSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.InputType;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Zoom extends AModule {

    private final DoubleSetting _zoomLevel = new DoubleSetting("Zoom Level", "Zoom level", 1, 1, 50, InputType.DECIMAL_SLIDER);

    private Double defaultMouseSensitivity;

    public Zoom() {
        super("Zoom", Category.OTHER);
        _keybind = new KeyBinding("key.finz.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.finz.config");
    }

    public double zoom(double fov) {
        SimpleOption<Double> mouseSensitivitySetting = mc.options.getMouseSensitivity();

        if (!_keybind.isPressed()) {
            if (defaultMouseSensitivity != null) {
                mouseSensitivitySetting.setValue(defaultMouseSensitivity);
                defaultMouseSensitivity = null;
            }
            return fov;
        }

        if (defaultMouseSensitivity == null)
            defaultMouseSensitivity = mouseSensitivitySetting.getValue();

        // Adjust mouse sensitivity in relation to zoom level.
        // 1.0 / currentLevel is a value between 0.02 (50x zoom)
        // and 1 (no zoom).
        mouseSensitivitySetting.setValue(defaultMouseSensitivity * (1.0 / _zoomLevel.getValue()));

        return fov / _zoomLevel.getValue();
    }
}
