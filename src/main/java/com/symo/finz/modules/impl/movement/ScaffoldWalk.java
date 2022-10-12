package com.symo.finz.modules.impl.movement;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.extension.PlayerExtension;
import com.symo.finz.utils.extension.PlayerInventoryExtension;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.TimerTask;

public class ScaffoldWalk extends Module {

    public ScaffoldWalk() {
        super("ScaffoldWalk", Keyboard.KEY_I, "FinZ - Movement");
    }

    public void onUpdate() {
            BlockPos blockBelow = PlayerExtension.getBlockPosBelow();

            // if the block is not air or is not replaceable return
            if (mc.theWorld.getBlockState(blockBelow).getBlock().getMaterial() != Material.air ||
                    !mc.theWorld.getBlockState(blockBelow).getBlock().isReplaceable(mc.theWorld, blockBelow))
                return;

            // select a block
            int slot = PlayerInventoryExtension.getFirstNonFallableSolidBlockInHotBarIndex();
            // if no block was found return
            if (slot == -1)
                return;
            mc.thePlayer.inventory.currentItem = slot;


            // animate the block placement
            mc.thePlayer.swingItem();
            // send the packet and wait bc if you switch too fast it will not work
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(PlayerExtension.getBlockPosBelow(),
                            1, mc.thePlayer.getHeldItem(), 0, 0, 0));
                }
            }, 75);
    }


}
