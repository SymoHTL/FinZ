package dev.symo.finz.modules.impl;

import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.AModule;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Zoom extends AModule {

    private Double defaultMouseSensitivity;

    public Zoom() {
        super("Zoom", "Misc");
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
        mouseSensitivitySetting.setValue(defaultMouseSensitivity * (1.0 / FinZClient.config.zoomLevel));

        return fov / FinZClient.config.zoomLevel;
    }


    @Override
    public boolean IsEnabled() {
        return _keybind.isPressed();
    }

    @Override
    public void SetEnabled(boolean enabled) {
        _keybind.setPressed(enabled);
        AfterEnableChange();
    }

    @Override
    public void onConfigChange() {
        AfterEnableChange();
    }
}
