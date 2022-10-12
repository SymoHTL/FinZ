package com.symo.finz.utils.extension;

public class HarvestLevelExtension {


    public static String toString(int harvestLevel) {
        switch (harvestLevel) {
            case 0:
                return "Wood";
            case 1:
                return "Stone";
            case 2:
                return "Iron";
            case 3:
                return "Diamond";
        }
        return harvestLevel + "";
    }
}
