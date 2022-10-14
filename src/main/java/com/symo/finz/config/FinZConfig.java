package com.symo.finz.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;

import java.io.File;
import java.util.Comparator;

public class FinZConfig extends Vigilant{

    public static FinZConfig INSTANCE;

    static {
        FinZConfig.INSTANCE = new FinZConfig();
    }
    public FinZConfig() {
        super(new File("./config/finz/config.toml"), "FinZ", new JVMAnnotationPropertyCollector(), new ConfigSorting());
        this.initialize();
    }

    // Modules  -------------------------------------------------------------------------------------------------------

    // Combat   -----------------------------------------------------

    // Aimbot   ----------------------

    // enable/disable
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the Aimbot cheat", category = "Combat", subcategory = "Aimbot")
    public boolean AimBotEnabled;
    @Property(type = PropertyType.SLIDER, name = "AimBot reach", max = 10,
            description = "Sets the distance for the Aimbot", category = "Combat", subcategory = "Aimbot")
    public int AimBotReach;
    // MobFarmer ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the MobFarmer cheat", category = "Combat", subcategory = "Mob Farmer")
    public boolean MobFarmerEnabled;

    // GUI   -----------------------------------------------------

    // BlockInfo   ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the BlockInfo cheat", category = "GUI", subcategory = "Block Info")
    public boolean BlockInfoEnabled;

    // HUD   ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the HUD cheat", category = "GUI", subcategory = "HUD")
    public boolean HUDEnabled;

    // InfoUi   ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the InfoUi cheat", category = "GUI", subcategory = "Info UI")
    public boolean InfoUiEnabled;

    // Misc   -----------------------------------------------------

    // Block Aimer   ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the BlockAimer cheat", category = "Misc", subcategory = "Block Aimer")
    public boolean BlockAimerEnabled;

    // Item Collector   ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the ItemCollector cheat", category = "Misc", subcategory = "Item Collector")
    public boolean ItemCollectorEnabled;

    // Movement -----------------------------------------------------

    // Fly  ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the Fly cheat", category = "Movement", subcategory = "Fly")
    public boolean FlyEnabled;

    // MineDown  ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the MineDown cheat", category = "Movement", subcategory = "Mine Down")
    public boolean MineDownEnabled;

    // MineUp  ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the MineUp cheat", category = "Movement", subcategory = "Mine Up")
    public boolean MineUpEnabled;

    // Jetpack  ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the Jetpack cheat", category = "Movement", subcategory = "Jetpack")
    public boolean JetpackEnabled;

    // Scaffold Walk ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the ScaffoldWalk cheat", category = "Movement", subcategory = "Scaffold Walk")
    public boolean ScaffoldWalkEnabled;

    // Spider ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the Spider cheat", category = "Movement", subcategory = "Spider")
    public boolean SpiderEnabled;

    // Auto Sprint ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the AutoSprint cheat", category = "Movement", subcategory = "Auto Sprint")
    public boolean AutoSprintEnabled;


    // Player -----------------------------------------------------

    // Auto Eat ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the AutoEat cheat", category = "Player", subcategory = "Auto Eat")
    public boolean AutoEatEnabled;

    // Fast Place ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the FastPlace cheat", category = "Player", subcategory = "Fast Place")
    public boolean FastPlaceEnabled;

    // No Fall ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the NoFall cheat", category = "Player", subcategory = "No Fall")
    public boolean NoFallEnabled;

    // Visuals -----------------------------------------------------

    // Animal ESP ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the AnimalESP cheat", category = "Visuals", subcategory = "Animal ESP")
    public boolean AnimalESPEnabled;

    // Mob ESP ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the MobESP cheat", category = "Visuals", subcategory = "Mob ESP")
    public boolean MobESPEnabled;

    // Player ESP ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the PlayerESP cheat", category = "Visuals", subcategory = "Player ESP")
    public boolean PlayerESPEnabled;

    // Find Block ESP ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the FindBlockESP cheat", category = "Visuals", subcategory = "Find Block ESP")
    public boolean FindBlockESPEnabled;

    // Path Tracer ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the PathTracer cheat", category = "Visuals", subcategory = "Path Tracer")
    public boolean PathTracerEnabled;

    // Fullbright ----------------------
    @Property(type = PropertyType.SWITCH, name = "Enable/Disable",
            description = "Enables or disables the Fullbright cheat", category = "Visuals", subcategory = "Fullbright")
    public boolean FullBrightEnabled;

    public static class ConfigSorting extends SortingBehavior {
        public Comparator<Category> getCategoryComparator() {
            return (o1, o2) -> {
                if (o1.getName().equals("FinZ")) {
                    return -1;
                } else if (o2.getName().equals("FinZ")) {
                    return 1;
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            };
        }
    }
}
