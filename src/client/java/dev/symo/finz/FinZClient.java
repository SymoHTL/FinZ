package dev.symo.finz;

import com.google.common.collect.ImmutableList;
import dev.symo.finz.config.FinZSettings;
import dev.symo.finz.config.FriendList;
import dev.symo.finz.events.impl.EventManager;
import dev.symo.finz.modules.ModuleManager;
import dev.symo.finz.ui.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FinZClient implements ClientModInitializer {

    public static final String MOD_ID = "finz";
    public static final String CONFIG_VERSION = "1";

    public static EventManager eventManager = new EventManager();
    public static List<ModContainer> MODS = new ArrayList<>();

    public static boolean isConfigOpen = false;
    private static ConfigScreen configScreen;

    public static final MinecraftClient mc = MinecraftClient.getInstance();

    public static Path FinZPath = FabricLoader.getInstance().getConfigDir().resolve("finz");

    public static final FriendList friendList = new FriendList(FinZPath.resolve("friends.json").toString());

    public static final FinZSettings settings = new FinZSettings(FinZPath.resolve("config.json").toString());

    @Override
    public void onInitializeClient() {
        createFinZFolder();

        ModuleManager.init();

        settings.load();
        friendList.load();
        FinZClient.MODS = ImmutableList.copyOf(FabricLoader.getInstance().getAllMods());
    }

    private void createFinZFolder() {
        var cfgDir = FabricLoader.getInstance().getConfigDir();
        var finzDir = cfgDir.resolve("finz");
        if (!finzDir.toFile().exists()) {
            try {
                Files.createDirectories(finzDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showConfigScreen() {
        System.out.println("showConfigScreen");
        if (isConfigOpen) {
            configScreen.close();
            configScreen = null;
            isConfigOpen = false;
            return;
        }
        configScreen = new ConfigScreen();
        mc.setScreen(configScreen);
        isConfigOpen = true;
        //ConfigManager.build();
        //Screen screen = ConfigManager.configBuilder.build();
        //mc.setScreen(screen);
    }

}