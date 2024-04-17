package dev.symo.finz.util;

import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;

public class HarvestLevelExtension {

    public static String levelToString(int level) {
        return switch (level) {
            case -1 -> "None";
            case 0 -> "Wood";
            case 1 -> "Stone";
            case 2 -> "Iron";
            case 3 -> "Diamond";
            case 4 -> "Netherite";
            default -> "Unknown";
        };
    }

    public static String blockStateTagToString(BlockState state) {
        if (state.isIn(BlockTags.AXE_MINEABLE)) return "Axe";
        if (state.isIn(BlockTags.PICKAXE_MINEABLE)) return "Pickaxe";
        if (state.isIn(BlockTags.SHOVEL_MINEABLE)) return "Shovel";
        if (state.isIn(BlockTags.HOE_MINEABLE)) return "Hoe";
        return "None";
    }
}
