package com.symo.finz.modules.impl.movement;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.ChatUtils;
import com.symo.finz.utils.extension.BlockExtension;
import com.symo.finz.utils.extension.PlayerExtension;
import com.symo.finz.utils.extension.PlayerInventoryExtension;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;

public class GoDown extends Module {

    public int y = 0;

    public GoDown() {
        super("GoDown","movement");
    }


    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event){
        this.disable();
    }

    public void onUpdate(TickEvent.ClientTickEvent event) {
        //if (!mineTimer.hasTimeElapsed(10,true))
        //    return;

        BlockPos currentBlockPos = PlayerExtension.getAccurateBlockPos();
        BlockPos belowBlockPos = new BlockPos(currentBlockPos.getX(), currentBlockPos.getY() - 1, currentBlockPos.getZ());
        Block belowBlock = mc.theWorld.getBlockState(belowBlockPos).getBlock();


        if(belowBlock.getBlockHardness(mc.theWorld, belowBlockPos) >= 18000000) {
            ChatUtils.sendMessage("You can't mine the block below! - stopped mining");
            this.disable();
            return;
        }

        if(currentBlockPos.getY() <= y) {
            ChatUtils.sendMessage("You are at the specified height! - stopped mining");
            this.disable();
            return;
        }

        if (mc.theWorld.getBlockState(new BlockPos(currentBlockPos.getX(), currentBlockPos.getY() - 2, currentBlockPos.getZ())).getBlock() == Blocks.air) {
            ChatUtils.sendMessage("Opening below detected! - stopped mining");
            this.disable();
            return;
        }

        if (BlockExtension.checkNeighboursForLava(belowBlockPos, mc.theWorld)) {
            ChatUtils.sendMessage("Lava below detected! - stopped mining");
            this.disable();
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
