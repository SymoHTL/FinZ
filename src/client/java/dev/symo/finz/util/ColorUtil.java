package dev.symo.finz.util;

import java.awt.*;

public class ColorUtil {
    public static Color PercentageToColor(double percentage) {
        if (percentage < 0) {
            percentage = 0;
        }
        int red = (int) (255.0 * (1 - percentage / 100));
        int green = (int) (255.0 * (percentage / 100));
        int blue = 0;
        return new Color(red, green, blue, 127);
    }
}
