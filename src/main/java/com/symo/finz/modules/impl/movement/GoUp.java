package com.symo.finz.modules.impl.movement;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.ChatUtils;
import com.symo.finz.utils.Timer;
import com.symo.finz.utils.extension.BlockExtension;
import com.symo.finz.utils.extension.PlayerExtension;
import com.symo.finz.utils.extension.PlayerInventoryExtension;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

import java.util.TimerTask;

public class GoUp extends Module {
    Timer jumpingTimer = new Timer();
    Timer placeBlockTimer = new Timer();

    public int y = 0;

    public GoUp() {
        super("GoUp", "FinZ - Movement");
    }


    public void onServerLeave() {
        this.disable();
    }

    public void onEnable() {
        try {
            if (mc.thePlayer.getPosition().getY() >= y && y != 0) {
                ChatUtils.sendMessage("You are at the specified height! - stopped mining");
                this.disable();
                return;
            }
            if (y == 0 && mc.thePlayer.getEntityWorld().getChunkFromBlockCoords(mc.thePlayer.getPosition()).canSeeSky(mc.thePlayer.getPosition())) {
                ChatUtils.sendMessage("You can see the sky! - stopped mining");
                this.disable();
                return;
            }
            if (PlayerInventoryExtension.getFirstNonFallableSolidBlockInHotBarIndex() == -1) {
                ChatUtils.sendMessage("No non fallable solid block in hotbar! - stopped mining");
                this.disable();
                return;
            }
            super.onEnable();
        } catch (Exception e) {
            e.printStackTrace();
            this.disable("Error");
        }

    }

    public void onUpdate() {
        // if itemStack in hotBar is null or not of type ItemBlock (not placeable) just return
        //if (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))
        //    return;

        // if the player has a block above him don't jump
        if (!PlayerExtension.hasBlockAbove()) {
            // jumping takes around ~600ms so jump every 650 to be sure
            if (jumpingTimer.hasTimeElapsed(550, true)) {
                BlockPos pos = mc.thePlayer.getPosition();
                if (pos.getY() >= y && y != 0) {
                    ChatUtils.sendMessage("You are at the specified height! - stopped mining");
                    this.disable();
                    return;
                }
                if (PlayerInventoryExtension.getFirstNonFallableSolidBlockInHotBarIndex() == -1) {
                    ChatUtils.sendMessage("No non fallable solid block in hotbar! - stopped mining");
                    this.disable();
                    return;
                }
                if (y == 0 && mc.thePlayer.getEntityWorld().getChunkFromBlockCoords(mc.thePlayer.getPosition()).canSeeSky(mc.thePlayer.getPosition())) {
                    ChatUtils.sendMessage("You can see the sky! - stopped mining");
                    this.disable();
                    return;
                }
                // only jump if the player in on the Ground
                if (mc.thePlayer.onGround && y == 0 || pos.getY() <= y)
                    // jump
                    mc.thePlayer.jump();
            }
        } else if (mc.thePlayer.onGround) {
            mineBlockAbove();
        }
        // jumping takes around 600ms to we try to place a block after 950 ms
        if (!mc.thePlayer.onGround) {
            if (placeBlockTimer.hasTimeElapsed(625, true)) {
                BlockPos pos = mc.thePlayer.getPosition();
                buildToSkylight();
                jumpingTimer.reset();
                placeBlockTimer.reset();
            }
        }

    }

    public void mineBlockAbove() {
        if (BlockExtension.checkNeighboursForLava(PlayerExtension.getBlockPosAbove(), mc.theWorld)) {
            ChatUtils.sendMessage("Lava above detected! - stopped mining");
            this.disable();
            return;
        }
        // if the player has a block above him mine it
        if (PlayerExtension.hasBlockAbove()) {
            int slot = PlayerInventoryExtension.getFirstEffectiveToolForBlockInHotBar(mc.theWorld.getBlockState(PlayerExtension.getBlockPosAbove()).getBlock());
            if (slot != -1)
                mc.thePlayer.inventory.currentItem = slot;
            // if the player is not on the goto height go to it
            mc.thePlayer.swingItem();
            mc.playerController.onPlayerDamageBlock(PlayerExtension.getBlockPosAbove(), mc.thePlayer.getHorizontalFacing());
        }
    }

    public void buildToSkylight() {
        int slot = PlayerInventoryExtension.getFirstNonFallableSolidBlockInHotBarIndex();
        if (slot != -1)
            mc.thePlayer.inventory.currentItem = slot;
        else {
            ChatUtils.sendMessage("No non fallable solid block in hotbar! - stopped mining");
            this.disable();
            return;
        }

        mc.thePlayer.swingItem();
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(PlayerExtension.getBlockPosBelow(),
                        1, mc.thePlayer.getHeldItem(), 0, 0, 0));
            }
        }, 75);
    }

}
