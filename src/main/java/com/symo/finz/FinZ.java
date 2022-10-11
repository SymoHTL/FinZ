package com.symo.finz;

import com.symo.finz.modules.Module;
import com.symo.finz.modules.ModuleManager;
import com.symo.finz.utils.MillisecondEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Mod(modid = FinZ.MOD_ID, version = FinZ.VERSION, acceptedMinecraftVersions = "[1.8.9]")
public class FinZ {
    public static final String MOD_ID = "FinZ";
    public static final String VERSION = "0.1";
    public static final Minecraft mc;
    public static ModuleManager moduleManager = new ModuleManager();

    @EventHandler
    public void onFMLInitialization(final FMLPreInitializationEvent event) {
        System.out.println("FinZ is loading...");
        final File directory = new File(event.getModConfigurationDirectory(), "finz");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        System.out.println("FinZ is initializing...");
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(moduleManager);


    }

    @EventHandler
    public void post(final FMLPostInitializationEvent event) {
        System.out.println("FinZ is done loading!");
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime firstRun = now.withSecond(0).plusMinutes(1L);
        final Duration initialDelay = Duration.between(now, firstRun);
        // FinZ
        moduleManager.init();
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() ->
                MinecraftForge.EVENT_BUS.post(new MillisecondEvent()),
                initialDelay.getSeconds(), 1L, TimeUnit.MILLISECONDS);
    }

    static {
        mc = Minecraft.getMinecraft();
    }
}
