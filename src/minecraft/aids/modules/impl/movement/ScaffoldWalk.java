package aids.modules.impl.movement;

import aids.BaseHiv;
import aids.events.impl.EventMotion;
import aids.modules.Category;
import aids.modules.Module;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.TimerTask;

public class ScaffoldWalk extends Module {

        public ScaffoldWalk() {
            super("ScaffoldWalk", "Walks on blocks", Category.MOVEMENT);
            setKey(Keyboard.KEY_I);
        }

        @Override
        public void onMotion(EventMotion m) {
            BlockPos blockBelow = mc.thePlayer.getPosition().down();

            // if the block is not air or is not replaceable return
            if (mc.theWorld.getBlockState(blockBelow).getBlock().getMaterial() != Material.air ||
                    !mc.theWorld.getBlockState(blockBelow).getBlock().isReplaceable(mc.theWorld, blockBelow))
                return;

            // select a block
            mc.thePlayer.inventory.currentItem = mc.thePlayer.inventory.getFirstNonFallableSolidBlockInHotBarIndex();

            // if no block was found return
            if (mc.thePlayer.inventory.currentItem == -1)
                return;

            // animate the block placement
            mc.thePlayer.swingItem();
            // send the packet and wait bc if you switch too fast it will not work
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(BaseHiv.INSTANCE.mc.thePlayer.getBlockPosBelow(),
                            1, mc.thePlayer.getHeldItem(), 0, 0, 0));
                }
            }, 75);

        }
}
