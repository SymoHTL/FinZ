package aids.modules.impl.movement;

import aids.BaseHiv;
import aids.events.impl.EventUpdate;
import aids.modules.Category;
import aids.modules.Module;
import aids.util.Timer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

import java.util.TimerTask;

public class Top extends Module {
    Timer jumpingTimer = new Timer();
    Timer placeBlockTimer = new Timer();

    public int y = 0;

    public Top() {
        super("Top", "Builds you to the specified y level", Category.MOVEMENT);
    }

    @Override
    public void onUpdate(EventUpdate e) {
            // if itemStack in hotBar is null or not of type ItemBlock (not placeable) just return
            //if (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))
            //    return;

            // if no goto height was defined but the player can see the sky just do nothing
            if (y == 0 && mc.thePlayer.getEntityWorld().getChunkFromBlockCoords(mc.thePlayer.getPosition()).canSeeSky(mc.thePlayer.getPosition()))
                return;
            // if the player has a block above him don't jump
            if (!mc.thePlayer.hasBlockAbove()) {
                // jumping takes around ~600ms so jump every 650 to be sure
                if (jumpingTimer.hasTimeElapsed(550, true)) {
                    BlockPos pos = mc.thePlayer.getPosition();
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
                    // if no goto height was defined, and you cant see the sky
                    if (y == 0 && !(mc.thePlayer.getEntityWorld().getChunkFromBlockCoords(mc.thePlayer.getPosition()).canSeeSky(mc.thePlayer.getPosition()))) {
                        buildToSkylight();
                        jumpingTimer.reset();
                        placeBlockTimer.reset();
                        // if goto height was defined just goto it
                    } else if (y != 0 && pos.getY() <= y) {
                        // if the player is not on the goto height go to it
                        buildToSkylight();
                        jumpingTimer.reset();
                        placeBlockTimer.reset();
                    } else {
                        // if the player is on the goto height just disable the module
                        this.setEnabled(false);
                        BaseHiv.addChatMessage("Finished building to " + y);
                    }
                }
            }

    }

    public void mineBlockAbove() {
        // if the player has a block above him mine it
        if (mc.thePlayer.hasBlockAbove()) {
            mc.thePlayer.inventory.currentItem = mc.thePlayer.inventory.getFirstPickaxeInHotBarIndex();
            // if the player is not on the goto height go to it
            mc.thePlayer.swingItem();
            mc.playerController.onPlayerDamageBlock(mc.thePlayer.getBlockPosAbove(), mc.thePlayer.getHorizontalFacing());
        }
    }

    public void buildToSkylight() {
        mc.thePlayer.inventory.currentItem = mc.thePlayer.inventory.getFirstNonFallableSolidBlockInHotBarIndex();
        mc.thePlayer.swingItem();
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(BaseHiv.INSTANCE.mc.thePlayer.getBlockPosBelow(),
                        1, mc.thePlayer.getHeldItem(), 0, 0, 0));
            }
        }, 75);
    }

}
