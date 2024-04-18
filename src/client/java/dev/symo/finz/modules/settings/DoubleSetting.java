package dev.symo.finz.modules.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.symo.finz.util.InputType;

public class DoubleSetting extends ModuleSetting{
    private double _value;
    private final double _default;
    private final double _min;
    private final double _max;


    public DoubleSetting(String name, String description, double value, double min, double max) {
        super(name, description, InputType.DECIMAL);
        _value = value;
        _default = value;
        _min = min;
        _max = max;
    }
    public DoubleSetting(String name, String description, double value, double min, double max, InputType type) {
        super(name, description, type);
        _value = value;
        _default = value;
        _min = min;
        _max = max;
    }

    @Override
    public void fromJson(JsonElement json) {
        try {
            _value = json.getAsDouble();
        } catch (Exception e) {
            e.printStackTrace();
            _value = _default;
        }
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(_value);
    }

    public double getValue() {
        return _value;
    }

    public void setValue(double value) {
        _value = Math.max(_min, Math.min(_max, value));
        changed();
        save();
    }

    public double getMin() {
        return _min;
    }

    public double getMax() {
        return _max;
    }
}
