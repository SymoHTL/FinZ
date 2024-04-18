package dev.symo.finz.modules.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.symo.finz.util.InputType;
import net.minecraft.text.Text;

public class StringSetting extends ModuleSetting{
    private String _value;
    private String _default;

    public StringSetting(String name, String description, String value) {
        super(name, description, InputType.TEXT);
        _value = value;
        _default = value;
    }

    @Override
    public void fromJson(JsonElement json) {
          try {
                _value = json.getAsString();
          } catch (Exception e) {
                e.printStackTrace();
                _value = _default;
          }
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(_value);
    }

    public void setValue(String value) {
        _value = value;
        changed();
        save();
    }

    public String getValue() {
        return _value;
    }
}
