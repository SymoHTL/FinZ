package com.symo.finz.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;

import java.io.File;
import java.util.Comparator;

public class FinZConfig extends Vigilant {

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

    @Property(type = PropertyType.SWITCH, name = "Aimbot Enable/Disable",
            description = "Aimbot for Entities.(Animals, Mobs, Players)", category = "Combat", subcategory = "Aimbot")
    public boolean AimBotEnabled;
    @Property(type = PropertyType.SLIDER, name = "AimBot reach", max = 10,
            description = "Sets the distance for the Aimbot", category = "Combat", subcategory = "Aimbot")
    public int AimBotReach = 4;
    // MobFarmer ----------------------
    @Property(type = PropertyType.SWITCH, name = "MobFarmer Enable/Disable",
            description = "Mob-Farmer is just like KillAura except it only targets Mobs (Best for AFK Mob Farms)",
            category = "Combat", subcategory = "Mob Farmer")
    public boolean MobFarmerEnabled;

    // GUI   -----------------------------------------------------

    // BlockInfo   ----------------------
    @Property(type = PropertyType.SWITCH, name = "BlockInfo Enable/Disable",
            description = "My own WAILA (What am i looking at)", category = "GUI", subcategory = "Block Info")
    public boolean BlockInfoEnabled;

    // HUD   ----------------------
    @Property(type = PropertyType.SWITCH, name = "HUD Enable/Disable",
            description = "Default Hud that shows enabled Modules/FPS", category = "GUI", subcategory = "HUD")
    public boolean HUDEnabled;

    // Misc   -----------------------------------------------------

    // Block Aimer   ----------------------
    @Property(type = PropertyType.SWITCH, name = "Block-Aimer Enable/Disable",
            description = "Aims at Blocks (has a few checks for visibility)", category = "Misc", subcategory = "Block-Aimer")
    public boolean BlockAimerEnabled;

    // Item Collector   ----------------------
    @Property(type = PropertyType.SWITCH, name = "Item Collector Enable/Disable",
            description = "Picks up Items from the ground (above or on your y-Level)", category = "Misc", subcategory = "Item Collector")
    public boolean ItemCollectorEnabled;

    // Movement -----------------------------------------------------

    // Fly  ----------------------
    @Property(type = PropertyType.SWITCH, name = "Fly Enable/Disable",
            description = "Well fly hacks (Produces Fall-damage, you should enable NoFall)", category = "Movement", subcategory = "Fly")
    public boolean FlyEnabled;

    // MineDown  ----------------------
    @Property(type = PropertyType.SWITCH, name = "Mine Down Enable/Disable",
            description = "Mines down to the specified y level or if none is specified as far as he can (Stops at lava and an opening below)",
            category = "Movement", subcategory = "Mine Down")
    public boolean MineDownEnabled;

    @Property(type = PropertyType.SLIDER, name = "Mine Down y-Level", min = 0, max = 255,
            description = "Sets the y-Level for MineDown (if y is 0 he just mine till bedrock or sth)", category = "Movement", subcategory = "Mine Down")
    public int MineDownY = 0;

    // MineUp  ----------------------
    @Property(type = PropertyType.SWITCH, name = "Mine Up Enable/Disable",
            description = "Mines/Builds up to the specified y level or if none is specified till he can see skylight (Stops at lava)",
            category = "Movement", subcategory = "Mine Up")
    public boolean MineUpEnabled;

    @Property(type = PropertyType.SLIDER, name = "Mine Up y-Level", min = 0, max = 255,
            description = "Sets the y-Level for MineUp (if y is 0 he will mine until he can see skylight)", category = "Movement", subcategory = "Mine Down")
    public int MineUpY = 0;

    // Jetpack  ----------------------
    @Property(type = PropertyType.SWITCH, name = "JetPack Enable/Disable",
            description = "Its a JetPack press space and hold after you enable it (Produces Fall-damage, you should enable NoFall)", category = "Movement", subcategory = "Jetpack")
    public boolean JetpackEnabled;

    // Scaffold Walk ----------------------
    @Property(type = PropertyType.SWITCH, name = "Scaffold Walk Enable/Disable",
            description = "Places Blocks beneath you (Bugs you back when sprinting)", category = "Movement", subcategory = "Scaffold Walk")
    public boolean ScaffoldWalkEnabled;

    // Spider ----------------------
    @Property(type = PropertyType.SWITCH, name = "Spider Enable/Disable",
            description = "Makes you climb walls just like a spider", category = "Movement", subcategory = "Spider")
    public boolean SpiderEnabled;

    // Auto Sprint ----------------------
    @Property(type = PropertyType.SWITCH, name = "Auto Sprint Enable/Disable",
            description = "Just sprints for you", category = "Movement", subcategory = "Auto Sprint")
    public boolean AutoSprintEnabled;


    // Player -----------------------------------------------------

    // Auto Eat ----------------------

    // No Fall ----------------------
    @Property(type = PropertyType.SWITCH, name = "No Fall Enable/Disable",
            description = "Negates Fall-Damage (Could work on servers)", category = "Player", subcategory = "No Fall")
    public boolean NoFallEnabled;

    @Property(type = PropertyType.SWITCH, name = "Auto Eat Enable/Disable",
            description = "Automatically eats everything in HotBar (Don't use on (official) servers (where you don't want to be banned))",
            category = "Player", subcategory = "Auto Eat")
    public boolean AutoEatEnabled;

    // Fast Place ----------------------
    @Property(type = PropertyType.SWITCH, name = "Fast Place Enable/Disable",
            description = "Places Blocks without delay (Coming soon...)", category = "Player", subcategory = "Fast Place")
    public boolean FastPlaceEnabled;

    // Visuals -----------------------------------------------------

    // Animal ESP ----------------------
    @Property(type = PropertyType.SWITCH, name = "Animal ESP Enable/Disable",
            description = "Draws an ESP-Box around Animals", category = "Visuals", subcategory = "Animal ESP")
    public boolean AnimalESPEnabled;

    // Mob ESP ----------------------
    @Property(type = PropertyType.SWITCH, name = "Mob ESP Enable/Disable",
            description = "Draws an ESP-Box around Mobs", category = "Visuals", subcategory = "Mob ESP")
    public boolean MobESPEnabled;

    // Player ESP ----------------------
    @Property(type = PropertyType.SWITCH, name = "Player ESP Enable/Disable",
            description = "Draws an ESP-Box around Players", category = "Visuals", subcategory = "Player ESP")
    public boolean PlayerESPEnabled;

    // Find Block ESP ----------------------
    @Property(type = PropertyType.SWITCH, name = "Find Block ESP Enable/Disable",
            description = "Draws an ESP-Box around the Found Blocks (Block Finder Module)", category = "Visuals", subcategory = "Find Block ESP")
    public boolean FindBlockESPEnabled;

    // Path Tracer ----------------------
    @Property(type = PropertyType.SWITCH, name = "Path Tracer Enable/Disable",
            description = "Draws a line on every Block you stepped on", category = "Visuals", subcategory = "Path Tracer")
    public boolean PathTracerEnabled;
    @Property(type = PropertyType.SLIDER, name = "Path Tracer Enable/Disable", min = 50, max = 10000,
            description = "Draws a line on every Block you stepped on", category = "Visuals", subcategory = "Path Tracer")
    public int PathTracerLength = 100;

    // Fullbright ----------------------
    @Property(type = PropertyType.SWITCH, name = "Fullbright Enable/Disable",
            description = "Full-Bright welp... sets you gamma so high that you can see in the dark",
            category = "Visuals", subcategory = "Fullbright")
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
