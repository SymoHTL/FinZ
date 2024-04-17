package dev.symo.finz.modules.impl;

import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.BoolSetting;
import dev.symo.finz.modules.settings.StringSetting;
import dev.symo.finz.util.Category;

public class SettingsModule extends AModule {
    private final StringSetting _color = new StringSetting("UI Color", "Example: For green use #00ff00",
            "#00ff00");

    private final BoolSetting _rainbow = new BoolSetting("Rainbow", "Rainbow UI", false);

    public SettingsModule() {
        super("Settings", Category.SETTINGS);
        addSetting(_color);
        addSetting(_rainbow);
    }

    public String getColorSetting() {
        return _color.getValue();
    }

    public int getColorFromHex() {
        try {
            String colorHex = _color.getValue().substring(1);
            int rgb = (int) Long.parseLong(colorHex, 16);
            return 0xFF000000 | rgb;
        } catch (Exception e) {
            return 0xffffffff;
        }
    }

    public boolean isRainbow() {
        return _rainbow.getValue();
    }

}
