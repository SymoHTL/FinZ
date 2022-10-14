package com.symo.finz.modules.impl.movement;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.extension.BlockExtension;
import com.symo.finz.utils.extension.PlayerExtension;
import com.symo.finz.utils.extension.PlayerInventoryExtension;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class MineDown extends Module {

    public int y = 0;

    public MineDown() {
        super("MineDown", "FinZ - Movement");
    }


    @Override
    public boolean isEnabled() {
        return FinZ.configFile.MineDownEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.MineDownEnabled = enabled;
    }

    public void onUpdate() {
            //if (!mineTimer.hasTimeElapsed(10,true))
            //    return;

            BlockPos currentBlockPos = PlayerExtension.getAccurateBlockPos();
            BlockPos belowBlockPos = new BlockPos(currentBlockPos.getX(), currentBlockPos.getY() - 1, currentBlockPos.getZ());
            Block belowBlock = mc.theWorld.getBlockState(belowBlockPos).getBlock();


            if (belowBlock.getBlockHardness(mc.theWorld, belowBlockPos) >= 18000000) {
                this.disable("You can't mine the block below! - stopped mining");
                return;
            }

            if (currentBlockPos.getY() <= y) {
                this.disable("You are at the specified height! - stopped mining");
                return;
            }

            if (mc.theWorld.getBlockState(new BlockPos(currentBlockPos.getX(), currentBlockPos.getY() - 2, currentBlockPos.getZ())).getBlock() == Blocks.air) {
                this.disable("Opening below detected! - stopped mining");
                return;
            }

            if (BlockExtension.checkNeighboursForLava(belowBlockPos, mc.theWorld)) {
                this.disable("Lava below detected! - stopped mining");
                return;
            }

            mineBlockBelow();
    }


    public void mineBlockBelow() {

        if (PlayerExtension.hasBlockBelow()) {
            int slot = PlayerInventoryExtension.getFirstEffectiveToolForBlockInHotBar(mc.theWorld.getBlockState(PlayerExtension.getBlockPosBelow()).getBlock());
            if (slot != -1)
                mc.thePlayer.inventory.currentItem = slot;

            mc.thePlayer.swingItem();
            mc.playerController.onPlayerDamageBlock(PlayerExtension.getBlockPosBelow(), mc.thePlayer.getHorizontalFacing());
        }
    }

}
