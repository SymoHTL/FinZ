package dev.symo.finz.modules.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.symo.finz.util.InputType;

public class IntSetting extends ModuleSetting{

    private int _value;
    private final int _default;
    private final int _min;
    private final int _max;


    public IntSetting(String name, String description, int value, int min, int max,  InputType type) {
        super(name, description, type);
        _value = value;
        _default = value;
        _min = min;
        _max = max;
    }
    public IntSetting(String name, String description, int value, int min, int max) {
        this(name, description, value, min, max, InputType.INT);
    }

    @Override
    public void fromJson(JsonElement json) {
        try {
            _value = json.getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
            _value = _default;
        }
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(_value);
    }

    public int getValue() {
        return _value;
    }

    public void setValue(int value) {
        _value = Math.max(_min, Math.min(_max, value));
        save();
    }

    public int getMin() {
        return _min;
    }

    public int getMax() {
        return _max;
    }
}
