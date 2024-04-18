package dev.symo.finz.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.symo.finz.FinZClient;
import dev.symo.finz.events.listeners.KeyPressListener;
import dev.symo.finz.modules.Modules;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public final class KeyBindSettings implements KeyPressListener {

    private final String path;

    private final ArrayList<KeyBind> keyBinds = new ArrayList<>();

    public KeyBindSettings(String path) {
        this.path = path;
    }

    @Override
    public void onKeyPress(KeyPressEvent event) {
        if(event.getAction() != GLFW.GLFW_PRESS) return;
        if(InputUtil.isKeyPressed(FinZClient.mc.getWindow().getHandle(), GLFW.GLFW_KEY_F3))return;
        if(FinZClient.mc.currentScreen != null) return;

        var keyName = InputUtil.fromKeyCode(event.getKeyCode(), event.getScanCode()).getTranslationKey();
        for (KeyBind keyBind : keyBinds) keyBind.onKeyPress(keyName);
    }

    public void load() {
        try {
            Path path = Paths.get(this.path);
            if (!path.toFile().exists()) return;

            var reader = Files.newBufferedReader(path);
            var json = JsonParser.parseReader(reader).getAsJsonObject();

            var modules = Modules.all;

            var keyBinds = json.getAsJsonArray("keyBinds");
            for (var keyBind : keyBinds) {
                var keyBindObj = keyBind.getAsJsonObject();
                var keyCode = keyBindObj.get("keyName").getAsString();
                var moduleName = keyBindObj.get("module").getAsString().toLowerCase();
                var module = modules.stream().filter(m -> m.name.toLowerCase().equals(moduleName)).findFirst().orElse(null);
                if (module == null) {
                    FinZClient.LOGGER.error("Failed to find module for keybind: {}", moduleName);
                    continue;
                }
                add(new KeyBind(keyCode, module));
            }
        } catch (Exception e) {
            FinZClient.LOGGER.error("Failed to load keybinds", e);
        }
    }

    public void save() {
        JsonObject json = new JsonObject();
        var keyBinds = new JsonArray();
        for (KeyBind keyBind : this.keyBinds) {
            var keyBindObj = new JsonObject();
            keyBindObj.addProperty("keyName", keyBind.getKeyName());
            keyBindObj.addProperty("module", keyBind.getModule().name.toLowerCase());
            keyBinds.add(keyBindObj);
        }
        json.add("keyBinds", keyBinds);

        try {
            var writer = Files.newBufferedWriter(Path.of(path));
            var gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
            gson.toJson(json, writer);
            writer.close();
        } catch (Exception e) {
            FinZClient.LOGGER.error("Failed to save keybinds", e);
        }
    }

    public void add(KeyBind keyBind) {
        for (KeyBind bind : keyBinds) if (bind.equals(keyBind)) return;
        keyBinds.add(keyBind);
    }

    public void addAndSave(KeyBind keyBind) {
        add(keyBind);
        save();
    }
}
