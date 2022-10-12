package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.BlockESPUtil;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class PathRender extends Module {

public static int maxBlocks = 100;
    public static List<BlockPos> blockPosList = new ArrayList<>();

    public PathRender() {
        super("PathRender", Keyboard.KEY_NUMPAD4, "FinZ - Visual");
    }


    public void onRender() {
        BlockPos lastBlockPos = null;
        for (BlockPos currentBlockPos : blockPosList) {
            BlockESPUtil.drawPathLine(currentBlockPos, lastBlockPos == null ? currentBlockPos : lastBlockPos, 0.5f, 2f);
            lastBlockPos = currentBlockPos;
        }
        if (maxBlocks < blockPosList.size())
            blockPosList.remove(0);

    }

}
