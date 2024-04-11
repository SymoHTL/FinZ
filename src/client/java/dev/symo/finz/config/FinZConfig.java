package dev.symo.finz.config;

public class FinZConfig {

    public boolean materialScannerEnabled = false;
    public int materialScannerRange = 25;
    public String materialScannerMaterial = "minecraft:ancient_debris";

    public boolean pathTracerEnabled = true;
    public int pathTracerLength = 100;

    public boolean whatTheFAmILookingAtEnabled = true;

    public boolean fastPlaceEnabled = false;
    public int fastPlaceValue = 4;

    //public boolean autoGetBlockEnabled = true;

    //public boolean autoGetToolEnabled = true;

    public boolean chromaBlockOutlineEnabled = true;

    public int zoomLevel = 3;

    public boolean playerEsp;
    public boolean mobEsp;

    public float mobEspRange = 50;

    public boolean itemEsp;
    public float itemEspRange = 50;

    //public boolean fullBrightEnabled = false;

    //public double fullBrightGamma = 1;
}
