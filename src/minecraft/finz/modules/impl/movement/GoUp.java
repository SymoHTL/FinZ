package finz.modules.impl.movement;

import finz.FinZ;
import finz.events.impl.EventUpdate;
import finz.modules.Category;
import finz.modules.Module;
import finz.util.Timer;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

import java.util.TimerTask;

public class GoUp extends Module {
    Timer jumpingTimer = new Timer();
    Timer placeBlockTimer = new Timer();

    public int y = 0;

    public GoUp() {
        super("GoUp", "Builds you to the specified y level", Category.MOVEMENT);
    }


    public void onEnable() {
        if (y == 0 && mc.thePlayer.getEntityWorld().getChunkFromBlockCoords(mc.thePlayer.getPosition()).canSeeSky(mc.thePlayer.getPosition())) {
            FinZ.addChatMessage("You can see the sky! - stopped mining");
            this.setEnabled(false);
            return;
        }
        super.onEnable();
    }
    public void onUpdate(EventUpdate e) {
        // if itemStack in hotBar is null or not of type ItemBlock (not placeable) just return
        //if (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))
        //    return;

        // if the player has a block above him don't jump
        if (!mc.thePlayer.hasBlockAbove()) {
            // jumping takes around ~600ms so jump every 650 to be sure
            if (jumpingTimer.hasTimeElapsed(550, true)) {
                BlockPos pos = mc.thePlayer.getPosition();
                if (pos.getY() >= y && y != 0) {
                    FinZ.addChatMessage("You are at the specified height! - stopped mining");
                    this.setEnabled(false);
                    return;
                }
                if (y == 0 && mc.thePlayer.getEntityWorld().getChunkFromBlockCoords(mc.thePlayer.getPosition()).canSeeSky(mc.thePlayer.getPosition())) {
                    FinZ.addChatMessage("You can see the sky! - stopped mining");
                    this.setEnabled(false);
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
        if (Block.checkNeighboursForLava(mc.thePlayer.getBlockPosAbove(), mc.theWorld)) {
            FinZ.addChatMessage("Lava above detected! - stopped mining");
            this.setEnabled(false);
            return;
        }
        // if the player has a block above him mine it
        if (mc.thePlayer.hasBlockAbove()) {
            int slot = mc.thePlayer.inventory.getFirstEffectiveToolForBlockInHotBar(mc.theWorld.getBlockState(mc.thePlayer.getBlockPosAbove()).getBlock());
            if (slot != -1)
                mc.thePlayer.inventory.currentItem = slot;
            // if the player is not on the goto height go to it
            mc.thePlayer.swingItem();
            mc.playerController.onPlayerDamageBlock(mc.thePlayer.getBlockPosAbove(), mc.thePlayer.getHorizontalFacing());
        }
    }

    public void buildToSkylight() {
        int slot = mc.thePlayer.inventory.getFirstNonFallableSolidBlockInHotBarIndex();
        if (slot != -1)
            mc.thePlayer.inventory.currentItem = slot;
        mc.thePlayer.swingItem();
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(FinZ.INSTANCE.mc.thePlayer.getBlockPosBelow(),
                        1, mc.thePlayer.getHeldItem(), 0, 0, 0));
            }
        }, 75);
    }

}
