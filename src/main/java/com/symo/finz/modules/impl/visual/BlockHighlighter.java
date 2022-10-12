package com.symo.finz.modules.impl.visual;

import com.symo.finz.modules.Module;
import com.symo.finz.modules.impl.visual.esp.BlockRenderESP;
import com.symo.finz.utils.ChatUtils;
import com.symo.finz.utils.extension.PlayerExtension;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class BlockHighlighter extends Module {

    public BlockHighlighter() {
        super("BlockHighlighter", "FinZ - Visual");
    }

    public void onUpdate() {
        BlockPos blockPos = PlayerExtension.getBlockPosBelow();
        if (mc.theWorld.getBlockState(blockPos).getBlock().getMaterial() != Material.air) {
            if (!BlockRenderESP.blockPosList.contains(blockPos)) {
                BlockRenderESP.blockPosList.add(blockPos);
                ChatUtils.sendMessage("Added block to list");
            }
        }
    }
}
