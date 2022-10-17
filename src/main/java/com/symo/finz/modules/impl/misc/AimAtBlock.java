package com.symo.finz.modules.impl.misc;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.AimHelper;
import com.symo.finz.utils.esp.BlockESPUtil;
import com.symo.finz.utils.extension.BlockExtension;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public class AimAtBlock extends Module {

    public static ArrayList<BlockPos> blockPosList = new ArrayList<>();

    public static BlockPos currentBlock = null;

    public static double[] squarePos = null;

    public AimAtBlock() {
        super("AimAtBlock", "FinZ - Misc");
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.BlockAimerEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.BlockAimerEnabled = enabled;
    }

    public void onServerLeave() {
        // Reset on server leave
        blockPosList.clear();
        currentBlock = null;
        squarePos = null;
        super.onServerLeave();
    }


    // render esps
    public void onRender() {
        // draw current block esp
        if (currentBlock != null)
            BlockESPUtil.drawESPWithBody(currentBlock, 0.4f, 2);
        // draw all block esp's
        for (BlockPos pos : blockPosList)
            BlockESPUtil.drawESP(pos, 0.4f, 2, 1, 0, 0);
        // draw little square where to aim
        if (squarePos != null)
            BlockESPUtil.drawSquareWhereToAim(squarePos, 0.4f, 2);
    }


    public void onUpdate() {
        if (mc.thePlayer == null)
            return;
        // temporary to add blocks to aim at
        if (mc.gameSettings.keyBindPickBlock.isKeyDown())
            // add the block the player is looking at to the list
            if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null)
                if (!blockPosList.contains(mc.objectMouseOver.getBlockPos()))
                    blockPosList.add(mc.objectMouseOver.getBlockPos());
        if (mc.gameSettings.keyBindPickBlock.isKeyDown() && mc.gameSettings.keyBindSneak.isKeyDown())
            // remove the block the player is looking at from the list
            if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null)
                blockPosList.remove(mc.objectMouseOver.getBlockPos());


        // reset current block when out of range
        if (currentBlock != null) {
            double resetDistance = mc.thePlayer.getDistance(currentBlock.getX(), currentBlock.getY(), currentBlock.getZ());
            if (resetDistance > 4)
                currentBlock = null;
        }

        // remove blocks that are Air
        List<BlockPos> toRemove = new ArrayList<>();
        for (BlockPos pos : blockPosList)
            if (mc.theWorld.getBlockState(pos).getBlock().getMaterial() == Material.air)
                toRemove.add(pos);
        blockPosList.removeAll(toRemove);
        if (currentBlock != null)
            if (mc.theWorld.getBlockState(currentBlock).getBlock().getMaterial() == Material.air)
                currentBlock = null;


        // get the block the player is looking at
        //if (mc.objectMouseOver != null) {
        //    // get the closest block to the block the player is looking at
        //    BlockPos lookingAt = mc.objectMouseOver.getBlockPos();
        //    if (lookingAt != null) {
        //        double closestDistance = 0;
        //        for (BlockPos pos : blockPosList) {
        //            // skip all blocks that are out of range
        //            double distanceToPlayer = mc.thePlayer.getDistance(pos.getX(), pos.getY(), pos.getZ());
        //            if (distanceToPlayer > 4)
        //                continue;
        //            // get distance to selected blocks
        //            double distance = BlockExtension.getDistance(pos.getX(), pos.getY(), pos.getZ(), lookingAt.getX(), lookingAt.getY(), lookingAt.getZ());
        //            if (closestDistance == 0 || distance < closestDistance) {
        //                closestDistance = distance;
        //                currentBlock = pos;
        //            }
        //        }
        //        if (currentBlock == null){
        //            squarePos = null;
        //            return;
        //        }
        //        // after getting the closest block, aim at it
        //        lookAtBlock();
        //        return;
//
        //    }
        //}
        // if player is not looking at a block get the closest block to the player
        double closestDistance = 0;
        for (BlockPos pos : blockPosList) {
            // skip all blocks that are out of range
            double distanceToPlayer = mc.thePlayer.getDistance(pos.getX(), pos.getY(), pos.getZ());
            if (distanceToPlayer > 5.49)
                continue;
            // get distance to selected blocks
            double distance = BlockExtension.getDistance(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
            if (closestDistance == 0 || distance < closestDistance) {
                closestDistance = distance;
                currentBlock = pos;
            }
        }
        if (currentBlock == null) {
            squarePos = null;
            return;
        }
        lookAtBlock();

//        List<BlockPos> reachableBlocks = new ArrayList<>();
//        for (BlockPos pos : blockPosList) {
//            // get distance to block
//            double distance = mc.thePlayer.getDistance(pos.getX(), pos.getY(), pos.getZ());
//            if (distance <= 4)
//                reachableBlocks.add(pos);
//        }
//
//        // get the block the player is looking at
//        if (mc.objectMouseOver == null)
//            return;
//        BlockPos lookingAt = mc.objectMouseOver.getBlockPos();
//        if (lookingAt == null)
//            return;
//        // get closest block to lookingAt
//        double closestDistance = 0;
//        for (BlockPos pos : reachableBlocks) {
//            double distance = BlockExtension.getDistance(lookingAt.getX(), lookingAt.getY(), lookingAt.getZ(), pos.getX(), pos.getY(), pos.getZ());
//            if (closestDistance == 0 || distance < closestDistance) {
//                closestDistance = distance;
//                currentBlock = pos;
//            }
//        }
//        if (lookingAt.equals(currentBlock))
//            return;
//
//        lookAtBlock();

    }


    public void lookAtBlock() {
        //get the visible side of the block
        if (currentBlock == null)
            return;
        EnumFacing visibleSide = BlockExtension.getVisibleSide(currentBlock, mc.thePlayer);
        // aim at the visible side
        if (visibleSide == null) {
            squarePos = null;
            return;
        }
        double x = currentBlock.getX() + 0.5;
        double y = currentBlock.getY() + 0.5;
        double z = currentBlock.getZ() + 0.5;
        switch (visibleSide) {
            case DOWN:
                y -= 0.5;
                break;
            case UP:
                y += 0.5;
                break;
            case NORTH:
                z -= 0.5;
                break;
            case SOUTH:
                z += 0.5;
                break;
            case WEST:
                x -= 0.5;
                break;
            case EAST:
                x += 0.5;
                break;
        }
        squarePos = new double[]{x, y, z};

        // look at the visible side
        float[] lookAt = AimHelper.getYawAndPitchToLookAt(new double[]{x, y, z});
        mc.thePlayer.rotationYaw = lookAt[0];
        mc.thePlayer.rotationPitch = lookAt[1];

    }


}
