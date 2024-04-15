package dev.symo.finz.modules.settings;

import com.google.gson.JsonElement;
import dev.symo.finz.util.InputType;

public class StringSetting extends ModuleSetting{
    private String _value;

    public StringSetting(String name, String description, String value) {
        super(name, description, InputType.TEXT);
        _value = value;
    }

    @Override
    public void fromJson(JsonElement json) {

    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    public String setValue(String value) {
        return _value = value;
    }

    public String getValue() {
        return _value;
    }
}
