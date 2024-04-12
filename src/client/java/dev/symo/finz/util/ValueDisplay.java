package dev.symo.finz.util;

import java.text.DecimalFormat;

public interface ValueDisplay
{
    public static final ValueDisplay INTEGER = v -> (int)v + "";

    public static final ValueDisplay DECIMAL =
            v -> Math.round(v * 1e6) / 1e6 + "";

    public static final ValueDisplay PERCENTAGE =
            v -> (int)(Math.round(v * 1e8) / 1e6) + "%";

    public static final ValueDisplay LOGARITHMIC = new ValueDisplay()
    {
        private static final DecimalFormat FORMAT =
                new DecimalFormat("#,###");

        @Override
        public String getValueString(double v)
        {
            return FORMAT.format(Math.pow(10, v));
        }
    };

    public static final ValueDisplay DEGREES = INTEGER.withSuffix("°");

    public static final ValueDisplay ROUNDING_PRECISION =
            v -> (int)v == 0 ? "1" : "0." + "0".repeat((int)v - 1) + "1";

    public static final ValueDisplay NONE = v -> "";

    public String getValueString(double value);

    public default ValueDisplay withLabel(double value, String label)
    {
        return v -> v == value ? label : getValueString(v);
    }

    public default ValueDisplay withPrefix(String prefix)
    {
        return v -> prefix + getValueString(v);
    }

    public default ValueDisplay withSuffix(String suffix)
    {
        return v -> getValueString(v) + suffix;
    }
}