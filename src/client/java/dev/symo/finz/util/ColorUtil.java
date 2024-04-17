package dev.symo.finz.util;

import java.awt.*;

public class ColorUtil {
    public static Color percentageToColor(double percentage) {
        if (percentage < 0) {
            percentage = 0;
        }
        int red = (int) (255.0 * (1 - percentage / 100));
        int green = (int) (255.0 * (percentage / 100));
        int blue = 0;
        return new Color(red, green, blue, 127);
    }

    public static int getRainbowColor(){
        long time = System.currentTimeMillis();
        int red = (int) (Math.sin(time / 2000.0) * 127 + 128);
        int green = (int) (Math.sin(time / 2000.0 + 2 * Math.PI / 3) * 127 + 128);
        int blue = (int) (Math.sin(time / 2000.0 + 4 * Math.PI / 3) * 127 + 128);
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }
}
