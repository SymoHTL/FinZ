package finz.modules.impl.movement;

import finz.FinZ;
import finz.events.impl.EventUpdate;
import finz.modules.Category;
import finz.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class GoDown extends Module {

    public int y = 0;

    public GoDown() {
        super("GoDown", "goes down", Category.MOVEMENT);
    }


    public void onEnable(){

        super.onEnable();
    }

    public void onUpdate(EventUpdate e) {
        //if (!mineTimer.hasTimeElapsed(10,true))
        //    return;

        BlockPos currentBlockPos = mc.thePlayer.getAccurateBlockPos();
        BlockPos belowBlockPos = new BlockPos(currentBlockPos.getX(), currentBlockPos.getY() - 1, currentBlockPos.getZ());
        Block belowBlock = mc.theWorld.getBlockState(belowBlockPos).getBlock();


        if(belowBlock.getBlockHardness(mc.theWorld, belowBlockPos) >= 18000000) {
            FinZ.addChatMessage("You can't mine the block below! - stopped mining");
            this.setEnabled(false);
            return;
        }

        if(currentBlockPos.getY() <= y) {
            FinZ.addChatMessage("You are at the specified height! - stopped mining");
            this.setEnabled(false);
            return;
        }

        if (mc.theWorld.getBlockState(new BlockPos(currentBlockPos.getX(), currentBlockPos.getY() - 2, currentBlockPos.getZ())).getBlock() == Blocks.air) {
            FinZ.addChatMessage("Opening below detected! - stopped mining");
            this.setEnabled(false);
            return;
        }

        if (Block.checkNeighboursForLava(belowBlockPos, mc.theWorld)) {
            FinZ.addChatMessage("Lava below detected! - stopped mining");
            this.setEnabled(false);
            return;
        }

        mineBlockBelow();
    }


    public void mineBlockBelow() {

        if (mc.thePlayer.hasBlockBelow()) {
            int slot = mc.thePlayer.inventory.getFirstEffectiveToolForBlockInHotBar(mc.theWorld.getBlockState(mc.thePlayer.getBlockPosBelow()).getBlock());
            if (slot != -1)
                mc.thePlayer.inventory.currentItem = slot;

            mc.thePlayer.swingItem();
            mc.playerController.onPlayerDamageBlock(mc.thePlayer.getBlockPosBelow(), mc.thePlayer.getHorizontalFacing());
        }
    }

}
