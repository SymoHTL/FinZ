package dev.symo.finz.config;

import com.google.gson.Gson;
import dev.symo.finz.FinZClient;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FriendList {
    private final String path;
    private final ArrayList<String> Friends = new ArrayList<>();


    public FriendList(String path) {
        this.path = path;
    }

    public void load() {
         // load a String array from json file
         if (Files.exists(Paths.get(path))) {
             try {
                 String[] friends = new Gson().fromJson(new FileReader(path), String[].class);
                 for (String friend : friends) {
                     if (friend != null && !friend.isEmpty() && !contains(friend)) {
                         Friends.add(friend);
                     }
                 }

             } catch (IOException e) {
                 FinZClient.LOGGER.error("Failed to load friends list", e);
             }
         }
    }


    public boolean contains(String s) {
        return Friends.contains(s);
    }

    public void addAndSave(String s) {
        if (!contains(s)) {
            Friends.add(s);
            save();
        }
    }

    public void removeAndSave(String s) {
        if (contains(s)) {
            Friends.remove(s);
            save();
        }
    }

    public void save() {
        try {
            Files.write(Paths.get(path), new Gson().toJson(Friends).getBytes());
        } catch (IOException e) {
            FinZClient.LOGGER.error("Failed to save friends list", e);
        }
    }
}
