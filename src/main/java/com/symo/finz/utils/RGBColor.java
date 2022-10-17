package com.symo.finz.utils;


import java.awt.*;

public class RGBColor {

    public static org.lwjgl.util.Color getRandColor() {
        // generate random values for RGB
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new org.lwjgl.util.Color(r, g, b);
    }

    public static float getHue(int seconds) {
        return (float) (System.currentTimeMillis() % seconds * 1000) / seconds * 1000;
    }

    public static int getRainbow() {
        return Color.HSBtoRGB(getHue(2), 1, 1);
    }

    public static int getRainbow(int seconds) {
        return Color.HSBtoRGB(getHue(seconds), 1, 1);
    }

    public static int getRainbow(int seconds, float saturation, float brightness) {
        return Color.HSBtoRGB(getHue(seconds), saturation, brightness);
    }

    public static int getRainbow(int seconds, int offset) {
        return Color.HSBtoRGB(getHue(seconds) + offset, 1, 1);
    }

    public static int getRainbow(int seconds, int offset, float saturation, float brightness) {
        return Color.HSBtoRGB(getHue(seconds) + offset, saturation, brightness);
    }
}
