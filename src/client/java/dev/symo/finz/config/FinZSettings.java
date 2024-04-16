package dev.symo.finz.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.Modules;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FinZSettings {

    private final String path;

    public FinZSettings(String path) {
        this.path = path;
    }

    public void load() {
        try {
            Path path = Path.of(this.path);
            if (!Files.exists(path))
                return;

            var reader = Files.newBufferedReader(path);
            var json = JsonParser.parseReader(reader).getAsJsonObject();

            if (!json.get("version").getAsString().equals(FinZClient.CONFIG_VERSION)) {
                Files.copy(path, Path.of(this.path + ".bak"));
                Files.delete(path);
                return;
            }

            var modules = Modules.all;
            // load the settings for each module
            modules.forEach(module -> {
                var moduleJson = json.getAsJsonObject(module._name.toLowerCase());
                module.getSettings().forEach(setting -> {
                    setting.fromJson(moduleJson.get(setting.getName()));
                });
                module.checkEnabled();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        JsonObject json = new JsonObject();
        json.addProperty("version", FinZClient.CONFIG_VERSION);

        var modules = Modules.all;

        modules.forEach(module -> {
            JsonObject moduleJson = new JsonObject();
            module.getSettings().forEach(setting -> {
                moduleJson.add(setting.getName(), setting.toJson());
            });
            json.add(module._name.toLowerCase(), moduleJson);
        });

        try {
            var writer = Files.newBufferedWriter(Path.of(path));
            var gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
            gson.toJson(json, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
