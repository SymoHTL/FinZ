package dev.symo.finz.modules.settings;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.symo.finz.util.InputType;

import java.util.ArrayList;

public class BoolSetting extends ModuleSetting {
    private boolean _value;
    private final boolean _default;
    private final ArrayList<Runnable> _onChanged = new ArrayList<>();

    public BoolSetting(String name, String description, boolean value) {
        super(name, description, InputType.BOOLEAN);
        _value = value;
        _default = value;
    }

    @Override
    public void fromJson(JsonElement json) {
        try {
            _value = json.getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            _value = _default;
        }
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(_value);
    }

    public boolean getValue() {
        return _value;
    }

    public void setValue(boolean _value) {
        this._value = _value;
        _onChanged.forEach(Runnable::run);
        save();
    }


    public void onChanged(Runnable runnable) {
        _onChanged.add(runnable);
    }
}
