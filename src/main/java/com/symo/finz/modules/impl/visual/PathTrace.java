package com.symo.finz.modules.impl.visual;

import com.symo.finz.modules.Module;
import com.symo.finz.modules.impl.visual.esp.PathRender;
import com.symo.finz.utils.ChatUtils;
import com.symo.finz.utils.extension.PlayerExtension;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;

public class PathTrace extends Module {

    public PathTrace() {
        super("PathTrace", "FinZ - Visual");
    }

    public void onUpdate() {
        BlockPos blockPos = PlayerExtension.getBlockPosBelow();
        Block block = mc.theWorld.getBlockState(blockPos).getBlock();
        if (block.getMaterial() != Material.air &&
                block.isFullBlock() &&
                block.isFullCube() &&
                !block.isReplaceable(mc.theWorld, blockPos)) {

            if (!PathRender.blockPosList.contains(blockPos)) {
                PathRender.blockPosList.add(blockPos);
                ChatUtils.sendMessage("Added block to list");
            }
        }
    }
}
