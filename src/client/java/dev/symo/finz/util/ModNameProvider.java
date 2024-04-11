package dev.symo.finz.util;

import dev.symo.finz.FinZClient;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class ModNameProvider {

    public static String getModName(@NotNull Block block) {
        return getModName(block.getTranslationKey());
    }

    public static String getModName(@NotNull Item item) {
        return getModName(item.getTranslationKey());
    }

    public static String getModName(String block) {
        for (ModContainer mod : FinZClient.MODS) {
            if (StringUtils.containsIgnoreCase(block, mod.getMetadata().getId())) {
                return mod.getMetadata().getName();
            }
        }
        return "Minecraft";
    }
}
