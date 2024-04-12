package dev.symo.finz.modules.settings;

import com.google.gson.JsonElement;
import dev.symo.finz.util.InputType;

public class BoolSetting extends ModuleSetting {
    private boolean _value;

    public BoolSetting(String name, String description, boolean value) {
        super(name, description, InputType.BOOLEAN);
        _value = value;
    }

    @Override
    public void fromJson(JsonElement json) {

    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    public boolean getValue() {
        return _value;
    }

    public void setValue(boolean _value) {
        this._value = _value;
    }
}
