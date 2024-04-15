package dev.symo.finz.modules.settings;

import com.google.gson.JsonElement;
import dev.symo.finz.util.InputType;
import dev.symo.finz.util.ValueDisplay;

public class DoubleSetting extends ModuleSetting{
    private double _value;
    private final double _min;
    private final double _max;
    private final ValueDisplay _display;


    public DoubleSetting(String name, String description, double value, double min, double max, ValueDisplay display) {
        super(name, description, InputType.DECIMAL);
        _value = value;
        _min = min;
        _max = max;
        _display = display;
    }
    public DoubleSetting(String name, String description, double value, double min, double max, ValueDisplay display, InputType type) {
        super(name, description, type);
        _value = value;
        _min = min;
        _max = max;
        _display = display;
    }

    @Override
    public void fromJson(JsonElement json) {
    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    public double getValue() {
        return _value;
    }

    public void setValue(double value) {
        _value = Math.max(_min, Math.min(_max, value));
    }

    @Override
    public String toString() {
        return _display.getValueString(_value);
    }

    public double getMin() {
        return _min;
    }

    public double getMax() {
        return _max;
    }
}
