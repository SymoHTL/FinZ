package dev.symo.finz.modules.settings;

import com.google.gson.JsonElement;
import dev.symo.finz.util.InputType;

public class DoubleSetting extends ModuleSetting{
    private double _value;
    private final double _min;
    private final double _max;


    public DoubleSetting(String name, String description, double value, double min, double max) {
        super(name, description, InputType.DECIMAL);
        _value = value;
        _min = min;
        _max = max;
    }
    public DoubleSetting(String name, String description, double value, double min, double max, InputType type) {
        super(name, description, type);
        _value = value;
        _min = min;
        _max = max;
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

    public double getMin() {
        return _min;
    }

    public double getMax() {
        return _max;
    }
}
