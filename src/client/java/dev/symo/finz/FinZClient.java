package dev.symo.finz;

import com.google.common.collect.ImmutableList;
import dev.symo.finz.config.ConfigManager;
import dev.symo.finz.config.FinZConfig;
import dev.symo.finz.config.FriendList;
import dev.symo.finz.events.impl.EventManager;
import dev.symo.finz.modules.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FinZClient implements ClientModInitializer {

	public static final String MOD_ID = "finz";

	public static EventManager eventManager = new EventManager();
	public static List<ModContainer> MODS = new ArrayList<>();

	public static FinZConfig config = new FinZConfig();


	public static final MinecraftClient mc = MinecraftClient.getInstance();

	public static Path FinZPath = FabricLoader.getInstance().getConfigDir().resolve("finz");

	public static final FriendList friendList = new FriendList(FinZPath.resolve("friends.json").toString());


	@Override
	public void onInitializeClient() {
		createFinZFolder();

		ModuleManager.init();


		ConfigManager.readConfig(FinZPath.resolve("config.json").toString());
		friendList.load();
		FinZClient.MODS = ImmutableList.copyOf(FabricLoader.getInstance().getAllMods());
	}

	private void createFinZFolder(){
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
		ConfigManager.build();
		Screen screen = ConfigManager.configBuilder.build();
		mc.setScreen(screen);
	}

}