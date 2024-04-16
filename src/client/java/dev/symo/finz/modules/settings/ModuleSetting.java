package dev.symo.finz.modules.settings;

import com.google.gson.JsonElement;
import dev.symo.finz.FinZClient;
import dev.symo.finz.util.InputType;
import net.minecraft.text.Text;

public abstract class ModuleSetting {
    private final String _name;
    private final String _description;
    private final InputType _type;

    protected ModuleSetting(String name, String description, InputType type) {
        _name = name;
        _description = description;
        _type = type;
    }

    public Text getDescription() {
        return Text.of(_description);
    }

    public String getName() {
        return _name;
    }

    public abstract void fromJson(JsonElement json);

    public abstract JsonElement toJson();

    public InputType getType() {
        return _type;
    }

    protected void save() {
        FinZClient.settings.save();
    }
}
