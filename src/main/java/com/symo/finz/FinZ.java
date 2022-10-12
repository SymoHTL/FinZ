package com.symo.finz;

import com.symo.finz.commands.CommandManager;
import com.symo.finz.modules.ModuleManager;
import com.symo.finz.utils.MillisecondEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Mod(clientSideOnly = true, name = "FinZ", modid = FinZ.modId, version = FinZ.version, acceptedMinecraftVersions = "[1.8.9]")
public class FinZ {
    public static final String modId = "FinZ";
    public static final String version = "0.2";
    public static final String commandPrefix = "#";
    public static final Minecraft mc;
    public static ModuleManager moduleManager = new ModuleManager();
    public static CommandManager commandManager = new CommandManager();

    @EventHandler
    public void onFMLInitialization(final FMLPreInitializationEvent event) {
        ModMetadata modMetadata = event.getModMetadata();
        modMetadata.autogenerated = false;
        modMetadata.name = "FinZ";
        modMetadata.authorList.add(EnumChatFormatting.BOLD + "Symo");
        modMetadata.logoFile = "resources/finz/logo.png";
        modMetadata.description = "A Minecraft Forge 1.8.9 utility/cheat mod.";
        modMetadata.url = "https://github.com/SymoHTL/AdvancedMinecraft";


        System.out.println("FinZ is loading...");
        final File directory = new File(event.getModConfigurationDirectory(), modId);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        System.out.println("FinZ is initializing...");
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(moduleManager);
        moduleManager.init();
        commandManager.init();
        // load config
        // load modules
        // load commands

    }

    @EventHandler
    public void post(final FMLPostInitializationEvent event) {
        System.out.println("FinZ is done loading!");
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime firstRun = now.withSecond(0).plusMinutes(1L);
        final Duration initialDelay = Duration.between(now, firstRun);
        // FinZ

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() ->
                        MinecraftForge.EVENT_BUS.post(new MillisecondEvent()),
                initialDelay.getSeconds(), 1L, TimeUnit.MILLISECONDS);
    }

    static {
        mc = Minecraft.getMinecraft();
    }
}
