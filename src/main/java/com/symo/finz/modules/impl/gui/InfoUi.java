package com.symo.finz.modules.impl.gui;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.extension.PlayerExtension;
import net.minecraft.util.BlockPos;

public class InfoUi extends Module {
    public InfoUi() {
        super("InfoUi", "FinZ - Gui");
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.InfoUiEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.InfoUiEnabled = enabled;
    }

    public void on2DRender() {
        BlockPos coords = PlayerExtension.getAccurateBlockPos();
        // draw coords in bottom left corner
        mc.fontRendererObj.drawString("X: " + coords.getX() + "Y: " + coords.getY() + "Z: "+ coords.getZ(), 2, mc.displayHeight - mc.fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
    }
}
