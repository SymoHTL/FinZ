package com.symo.finz.modules.impl.visual;

import com.symo.finz.modules.Module;
import com.symo.finz.modules.impl.visual.esp.BlockRenderESP;
import com.symo.finz.utils.ChatUtils;
import net.minecraft.util.MovingObjectPosition;

public class BlockHighlighter extends Module {

    public BlockHighlighter() {
        super("BlockHighlighter", "FinZ - Visual");
    }

    public void onKey() {
        try {
            //check if middle mouse button is pressed
            if (!mc.gameSettings.keyBindPickBlock.isPressed())
                return;
            if (mc.objectMouseOver == null ||
                    mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK ||
                    mc.objectMouseOver.getBlockPos() == null)
                return;
            BlockRenderESP.blockPosList.add(this.mc.objectMouseOver.getBlockPos());
            ChatUtils.sendMessage("Added block to highlight list");
        } catch (Exception e) {
            e.printStackTrace();
            this.disable("Error");
        }
        //if (!this.mc.gameSettings.keyBindPickBlock.isPressed() && !this.mc.gameSettings.keyBindAttack.isPressed()) return;

    }
}
