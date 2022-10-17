package com.symo.finz.modules.impl.visual;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.BlockESPUtil;
import com.symo.finz.utils.extension.PlayerExtension;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class PathTracer extends Module {

    public static List<BlockPos> blockPosList = new ArrayList<>();

    public PathTracer() {
        super("PathTracer", "FinZ - Visual");
    }

    public void onUpdate() {
        BlockPos blockPos = PlayerExtension.getBlockPosBelow();
        Block block = mc.theWorld.getBlockState(blockPos).getBlock();
        if (block.getMaterial() != Material.air &&
                block.isFullBlock() &&
                block.isFullCube() &&
                !block.isReplaceable(mc.theWorld, blockPos)) {
            if (!blockPosList.contains(blockPos)) {
                blockPosList.add(blockPos);
            }
        }
        // remove the difference between the PathTracerLength and the current blockPosList size
        if (FinZ.configFile.PathTracerLength < blockPosList.size())
            if (blockPosList.size() - FinZ.configFile.PathTracerLength > 0)
                blockPosList.subList(0, blockPosList.size() - FinZ.configFile.PathTracerLength).clear();
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.PathTracerEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.PathTracerEnabled = enabled;
    }

    public void onRender() {
        BlockPos lastBlockPos = null;
        for (BlockPos currentBlockPos : blockPosList) {
            BlockESPUtil.drawPathLine(currentBlockPos, lastBlockPos == null ? currentBlockPos : lastBlockPos, 0.5f, 2f);
            lastBlockPos = currentBlockPos;
        }
    }


}
