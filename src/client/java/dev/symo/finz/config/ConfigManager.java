package dev.symo.finz.config;

import com.google.gson.Gson;
import dev.symo.finz.FinZClient;
import dev.symo.finz.config.FinZConfig;
import dev.symo.finz.events.ConfigChangeEvent;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConfigManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinZClient.MOD_ID);
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static ConfigBuilder configBuilder;

    public static void build() {
        initConfigBuilder();
        setSavingLogic();
        buildSettings();
    }

    public static void initConfigBuilder() {
        // create config builder
        configBuilder = ConfigBuilder.create()
                .setParentScreen(mc.currentScreen)
                .setTitle(Text.translatable("title.finz.config"));
    }

    public static void setSavingLogic() {
        // what action to execute when the save button is pressed
        configBuilder.setSavingRunnable(() -> {
            Gson g = new Gson();
            String json = g.toJson(FinZClient.config);
            try {
                FileWriter myWriter = new FileWriter(FinZClient.FinZPath.resolve("config.json").toString());
                myWriter.write(json);
                myWriter.close();
                LOGGER.info("Config file saved: " + json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ConfigChangeEvent.CHANGE.invoker().onChange();
        });
    }

    public static void readConfig(String path) {
        // file
        File cfgFile = new File(path);
        try {
            // create file if it doesn't exist
            if (cfgFile.createNewFile())
                LOGGER.info("Config file created: " + cfgFile.getName());
            else {
                LOGGER.info("ConfigFile Path: " + cfgFile.getAbsolutePath());
                Scanner cfgFileContent = new Scanner(cfgFile);
                StringBuilder content = new StringBuilder();
                while (cfgFileContent.hasNextLine())
                    content.append(cfgFileContent.nextLine());
                // parse json
                Gson g = new Gson();
                FinZClient.config = g.fromJson(content.toString(), FinZConfig.class);
                cfgFileContent.close();
                // log
                if (FinZClient.config == null) {
                    FinZClient.config = new FinZConfig();
                    LOGGER.info("Config file is empty, using default values.");
                } else
                    LOGGER.info("Config file loaded");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ConfigChangeEvent.CHANGE.invoker().onChange();
    }

    public static void buildSettings() {
        // categories

        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();

        // esp
        ConfigCategory esp = configBuilder.getOrCreateCategory(Text.translatable("category.finz.espEnabled"));
        esp.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.playerEsp"), FinZClient.config.playerEsp)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> FinZClient.config.playerEsp = newValue)
                .build());
        esp.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.mobEsp"), FinZClient.config.mobEsp)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> FinZClient.config.mobEsp = newValue)
                .build());
        esp.addEntry(entryBuilder.startFloatField(Text.translatable("option.finz.mobEspRange"), FinZClient.config.mobEspRange)
                .setDefaultValue(50f)
                .setTooltip(Text.of("The distance at which Mob ESP will render."))
                .setSaveConsumer(newValue -> {
                    if (newValue < 0) FinZClient.config.mobEspRange = 0;
                    else FinZClient.config.mobEspRange = newValue;
                })
                .build());


        esp.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.itemEsp"), FinZClient.config.itemEsp)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> FinZClient.config.itemEsp = newValue)
                .build());

        esp.addEntry(entryBuilder.startFloatField(Text.translatable("option.finz.itemEspRange"), FinZClient.config.itemEspRange)
                .setDefaultValue(50f)
                .setTooltip(Text.of("The distance at which Item ESP will render."))
                .setSaveConsumer(newValue -> {
                    if (newValue < 0) FinZClient.config.itemEspRange = 0;
                    else FinZClient.config.itemEspRange = newValue;
                })
                .build());


        // Path Tracer
        ConfigCategory pathTracer = configBuilder.getOrCreateCategory(Text.translatable("category.finz.pathTracerEnabled"));
        pathTracer.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.pathTracerEnabled"), FinZClient.config.pathTracerEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> FinZClient.config.pathTracerEnabled = newValue)
                .build());
        pathTracer.addEntry(entryBuilder.startIntField(Text.translatable("option.finz.pathTracerLength"), FinZClient.config.pathTracerLength)
                .setDefaultValue(100)
                .setTooltip(Text.of("The length of the path tracer. Higher values will cause LAG."))
                .setSaveConsumer(newValue -> FinZClient.config.pathTracerLength = newValue)
                .build());

        // Material Scanner
        ConfigCategory materialScanner = configBuilder.getOrCreateCategory(Text.translatable("category.finz.materialScannerEnabled"));
        materialScanner.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.materialScannerEnabled"), FinZClient.config.materialScannerEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> FinZClient.config.materialScannerEnabled = newValue)
                .build());
        // range of scan
        materialScanner.addEntry(entryBuilder.startIntField(Text.translatable("option.finz.materialScannerRange"), FinZClient.config.materialScannerRange)
                .setDefaultValue(10)
                .setTooltip(Text.of("The range of the material scanner. Higher values will cause LAG."))
                .setSaveConsumer(newValue -> FinZClient.config.materialScannerRange = newValue)
                .build());
        // materials to scan for as string
        materialScanner.addEntry(entryBuilder.startStrField(Text.translatable("option.finz.materialScannerMaterials"), FinZClient.config.materialScannerMaterial)
                .setDefaultValue("AIR,CAVE_AIR,VOID_AIR")
                .setTooltip(Text.of("The materials to scan for. e.g. minecraft:oak_planks"))
                .setSaveConsumer(newValue -> FinZClient.config.materialScannerMaterial = newValue)
                .build());

        /*// Fullbright
        ConfigCategory fullbright = configBuilder.getOrCreateCategory(Text.translatable("category.finz.fullbright"));
        fullbright.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.fullbrightenabled"), FinZClient.config.fullBrightEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> FinZClient.config.fullBrightEnabled = newValue)
                .build());
        fullbright.addEntry(entryBuilder.startDoubleField(Text.translatable("option.finz.fullbrightlevel"), FinZClient.config.fullBrightGamma)
                .setDefaultValue(1.0)
                .setTooltip(Text.of("The gamma level to set when fullbright is enabled."))
                .setSaveConsumer(newValue -> FinZClient.config.fullBrightGamma = newValue)
                .build());*/


        // ZOOM
        ConfigCategory zoomCategory = configBuilder.getOrCreateCategory(Text.translatable("category.finz.zoom"));
        zoomCategory.addEntry(entryBuilder.startIntSlider(Text.translatable("option.finz.zoomlevel"), FinZClient.config.zoomLevel, 0, 50)
                .setDefaultValue(3)
                .setTooltip(Text.of("The zoom level."))
                .setSaveConsumer(newValue -> FinZClient.config.zoomLevel = newValue)
                .build());


        // What the F am i looking at
        ConfigCategory wtfail = configBuilder.getOrCreateCategory(Text.translatable("category.finz.wtfail"));
        wtfail.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.wtfail"), FinZClient.config.whatTheFAmILookingAtEnabled)
                .setDefaultValue(true)
                .setTooltip(Text.of("Display's information for the entity/block you are looking at"))
                .setSaveConsumer(newValue -> FinZClient.config.whatTheFAmILookingAtEnabled = newValue)
                .build());

        //    fast place
        ConfigCategory fastPlace = configBuilder.getOrCreateCategory(Text.translatable("category.finz.fastplace"));
        fastPlace.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.fastplace"), FinZClient.config.fastPlaceEnabled)
                .setDefaultValue(false)
                .setTooltip(Text.of("Places blocks faster"))
                .setSaveConsumer(newValue -> FinZClient.config.fastPlaceEnabled = newValue)
                .build());
        fastPlace.addEntry(entryBuilder.startIntSlider(Text.translatable("option.finz.fastplacevalue"), FinZClient.config.fastPlaceValue, 0, 3)
                .setDefaultValue(4)
                .setTooltip(Text.of("Resets the item use timer to this value. \n 0 = fastest \n 1 = faster \n 2 = fast"))
                .setSaveConsumer(newValue -> FinZClient.config.fastPlaceValue = newValue)
                .build());

        //    auto get block
        /*ConfigCategory autoGetBlock = configBuilder.getOrCreateCategory(Text.translatable("category.finz.autogetblock"));
        autoGetBlock.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.autogetblock"), FinZClient.config.autoGetBlockEnabled)
                .setDefaultValue(true)
                .setTooltip(Text.of("Automatically restocks your hotbar with the block you placed"))
                .setSaveConsumer(newValue -> FinZClient.config.autoGetBlockEnabled = newValue)
                .build());
        //    auto get tool
        ConfigCategory autoGetTool = configBuilder.getOrCreateCategory(Text.translatable("category.finz.autogettool"));
        autoGetTool.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.autogettool"), FinZClient.config.autoGetToolEnabled)
                .setDefaultValue(true)
                .setTooltip(Text.of("Automatically restocks your hotbar with the tool that broke"))
                .setSaveConsumer(newValue -> FinZClient.config.autoGetToolEnabled = newValue)
                .build());*/
        //    chroma block outline
        ConfigCategory chromaOutline = configBuilder.getOrCreateCategory(Text.translatable("category.finz.chromaoutline"));
        chromaOutline.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.finz.blockoutline"), FinZClient.config.chromaBlockOutlineEnabled)
                .setDefaultValue(true)
                .setTooltip(Text.of("Enables chroma block outline"))
                .setSaveConsumer(newValue -> FinZClient.config.chromaBlockOutlineEnabled = newValue)
                .build());
    }
}
