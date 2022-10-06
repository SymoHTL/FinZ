package finz.modules.impl.player;

import finz.events.impl.EventMotion;
import finz.modules.Category;
import finz.modules.Module;
import finz.util.Timer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class AutoMine extends Module {
    public static List<BlockPos> blocks = new ArrayList<>();
    Timer timer = new Timer();
    private List<BlockPos> blocksToBreak;

    public AutoMine() {
        super("AutoMine", "Breaks selected blocks automatically", Keyboard.KEY_NONE, Category.PLAYER);
    }

    public void onMotion(EventMotion e) {
        // turn player 180 degrees
        // mc.thePlayer.rotationYaw += 180;
        // Are there any blocks
        if (blocks.isEmpty())
            return;
        // Copy blocks to break
        if (blocksToBreak == null || blocksToBreak.isEmpty()) {
            blocksToBreak = new ArrayList<>(blocks);
        }
        // Check if empty
        if (!blocksToBreak.isEmpty()) {
            // Get block
            BlockPos block = blocksToBreak.get(0);
            // Check if we can break block
            if (mc.theWorld.getBlockState(block).getBlock() == Blocks.air ||
                    mc.theWorld.getBlockState(block).getBlock() == Blocks.bedrock ||
                    mc.thePlayer.getPosition().distanceSq(block) > 4.5f * 4.5f) {
                // Remove if not
                blocksToBreak.remove(block);
                return;
            }
            // Turn player to block (Serverside)
            if (timer.hasTimeElapsed(20, true)) {
                float[] rotations = getRotations(blockToVec3(block));
                // need method to slow turn player
                mc.thePlayer.rotationYaw = rotations[0];
                mc.thePlayer.rotationPitch = rotations[1];
                // check if player can see middle of the block
                // I need a method to draw a line and raytrace at the same time
                MovingObjectPosition obj = mc.thePlayer.rayTrace(4.6, 1);//new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ), blockToVec3(block));
                if (obj == null) {
                    // BaseHiv.addChatMessage("obj is null v" + System.currentTimeMillis());
                    // Remove if not
                    return;
                }
                block = obj.getBlockPos();
                //canBreak = (obj.getBlockPos().getX() == block.getX() && obj.getBlockPos().getY() == block.getY() && obj.getBlockPos().getZ() == block.getZ());
            }
            // Break block
            mc.thePlayer.swingItem();
            mc.playerController.onPlayerDamageBlock(block, mc.thePlayer.getHorizontalFacing());
        }

    }

    public float[] getRotations(Vec3 blockPos) {
        double dX = blockPos.xCoord + 0.5 - mc.thePlayer.posX;
        double dY = blockPos.yCoord + 0.5 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double dZ = blockPos.zCoord + 0.5 - mc.thePlayer.posZ;
        double dXZ = Math.sqrt(dX * dX + dZ * dZ);
        float yaw = (float) (Math.atan2(dZ, dX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(dY, dXZ) * 180.0D / Math.PI);

        return new float[]{
                yaw, pitch
        };
    }

    private Vec3 blockToVec3(BlockPos block) {
        return new Vec3(block.getX(), block.getY(), block.getZ());
    }

    /*
     *    List<BlockPos> blocks = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.expand(5, 5, 5)).stream().map(aabb -> new BlockPos(aabb.minX, aabb.minY, aabb.minZ)).collect(Collectors.toList());
     *    for (BlockPos block : blocks) {
     *        BaseHiv.addChatMessage("Block: " + block.toString() + " " + mc.theWorld.getBlockState(block).getBlock().getLocalizedName());
     *    }
     *    MovingObjectPosition block1 = mc.thePlayer.rayTrace(5, 1);
     *    BaseHiv.addChatMessage("Looking at: " + block1.getBlockPos().toString() + " " + mc.theWorld.getBlockState(block1.getBlockPos()).getBlock().getLocalizedName());
     * */

    /*
     * if (counter % 6 == 0) {
     *               Vec3 vec3 = blockToVec3(block).add(new Vec3(0, -0.5, 0)); // Add 0.5 to center of block
     *               blockLookingAt = mc.theWorld.rayTraceBlocks(new Vec3(e.x, e.y + 1.6f, e.z), vec3); // Get the Block we are looking at (Serverside)
     *               // check if we are looking at the correct block
     *               if(blockLookingAt != null && blockLookingAt.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && blockLookingAt.getBlockPos().equals(block)) {
     *
     *               }
     *               rotations = getRotations(vec3);
     *           } else if (counter % 6 == 1) {
     *               Vec3 vec3 = blockToVec3(block).add(new Vec3(0, 0.5, 0));
     *               rotations = getRotations(vec3);
     *           } else if (counter % 6 == 2) {
     *               Vec3 vec3 = blockToVec3(block).add(new Vec3(-0.5, 0, 0));
     *               rotations = getRotations(vec3);
     *           } else if (counter % 6 == 3) {
     *               Vec3 vec3 = blockToVec3(block).add(new Vec3(0.5, 0, 0));
     *               rotations = getRotations(vec3);
     *           } else if (counter % 6 == 4) {
     *               Vec3 vec3 = blockToVec3(block).add(new Vec3(0, 0, -0.5));
     *               rotations = getRotations(vec3);
     *           } else {
     *               Vec3 vec3 = blockToVec3(block).add(new Vec3(0, 0, 0.5));
     *               rotations = getRotations(vec3);
     *           }
     * */

    /*
    * boolean found = false;

                   Vec3 vec3 = blockToVec3(block).add(new Vec3(0, -0.5, 0)); // Add 0.5 to center of block
                   if (e.x < block.getX() && e.z < block.getZ())
                       blockLookingAt = mc.theWorld.rayTraceBlocks(vec3, new Vec3(e.x, e.y + 1.6f, e.z)); // Get the Block we are looking at (Serverside)
                   else
                       blockLookingAt = mc.theWorld.rayTraceBlocks(new Vec3(e.x, e.y + 1.6f, e.z), vec3); // Get the Block we are looking at (Serverside)
                   // check if we are looking at the correct block
                   if (blockLookingAt != null && blockLookingAt.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && blockLookingAt.getBlockPos().equals(block)) {
                       found = true;
                       rotations = getRotations(vec3);
                   }

                   if (!found) {
                       vec3 = blockToVec3(block).add(new Vec3(0, 0.5, 0));
                       if (e.x < block.getX() && e.z < block.getZ())
                           blockLookingAt = mc.theWorld.rayTraceBlocks(vec3, new Vec3(e.x, e.y + 1.6f, e.z)); // Get the Block we are looking at (Serverside)
                       else
                           blockLookingAt = mc.theWorld.rayTraceBlocks(new Vec3(e.x, e.y + 1.6f, e.z), vec3); // Get the Block we are looking at (Serverside)
                       if (blockLookingAt != null && blockLookingAt.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && blockLookingAt.getBlockPos().equals(block)) {
                           found = true;
                           rotations = getRotations(vec3);
                       }
                   }
                   if (!found) {
                       vec3 = blockToVec3(block).add(new Vec3(-0.5, 0, 0));
                       if (e.x < block.getX() && e.z < block.getZ())
                           blockLookingAt = mc.theWorld.rayTraceBlocks(vec3, new Vec3(e.x, e.y + 1.6f, e.z)); // Get the Block we are looking at (Serverside)
                       else
                           blockLookingAt = mc.theWorld.rayTraceBlocks(new Vec3(e.x, e.y + 1.6f, e.z), vec3); // Get the Block we are looking at (Serverside)
                       if (blockLookingAt != null && blockLookingAt.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && blockLookingAt.getBlockPos().equals(block)) {
                           found = true;
                           rotations = getRotations(vec3);
                       }
                   }
                   if (!found) {
                       vec3 = blockToVec3(block).add(new Vec3(0.5, 0, 0));
                       if (e.x < block.getX() && e.z < block.getZ())
                           blockLookingAt = mc.theWorld.rayTraceBlocks(vec3, new Vec3(e.x, e.y + 1.6f, e.z)); // Get the Block we are looking at (Serverside)
                       else
                           blockLookingAt = mc.theWorld.rayTraceBlocks(new Vec3(e.x, e.y + 1.6f, e.z), vec3); // Get the Block we are looking at (Serverside)
                       if (blockLookingAt != null && blockLookingAt.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && blockLookingAt.getBlockPos().equals(block)) {
                           found = true;
                           rotations = getRotations(vec3);
                       }
                   }
                   if (!found) {
                       vec3 = blockToVec3(block).add(new Vec3(0, 0, -0.5));
                       if (e.x < block.getX() && e.z < block.getZ())
                           blockLookingAt = mc.theWorld.rayTraceBlocks(vec3, new Vec3(e.x, e.y + 1.6f, e.z)); // Get the Block we are looking at (Serverside)
                       else
                           blockLookingAt = mc.theWorld.rayTraceBlocks(new Vec3(e.x, e.y + 1.6f, e.z), vec3); // Get the Block we are looking at (Serverside)
                       if (blockLookingAt != null && blockLookingAt.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && blockLookingAt.getBlockPos().equals(block)) {
                           found = true;
                           rotations = getRotations(vec3);
                       }
                   }
                   if (!found) {
                       vec3 = blockToVec3(block).add(new Vec3(0, 0, 0.5));
                       if (e.x < block.getX() && e.z < block.getZ())
                           blockLookingAt = mc.theWorld.rayTraceBlocks(vec3, new Vec3(e.x, e.y + 1.6f, e.z)); // Get the Block we are looking at (Serverside)
                       else
                           blockLookingAt = mc.theWorld.rayTraceBlocks(new Vec3(e.x, e.y + 1.6f, e.z), vec3); // Get the Block we are looking at (Serverside)
                       if (blockLookingAt != null && blockLookingAt.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && blockLookingAt.getBlockPos().equals(block)) {
                           found = true;
                           rotations = getRotations(vec3);
                       }
                   }

                   // found true -> Block is touchable, set rotations false -> remove block
                   // my z and x < block z and x -> does not work
                   if (found) {
                       mc.thePlayer.rotationYaw = rotations[0];
                       mc.thePlayer.rotationPitch = rotations[1];
                   }
    * */
}
