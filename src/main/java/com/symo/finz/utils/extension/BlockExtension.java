package com.symo.finz.utils.extension;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockExtension {

    public static boolean isBlockBelowAir(World worldIn, BlockPos pos)    {
        return worldIn.getBlockState(pos.add(0, -1, 0)).getBlock().getMaterial() == Material.air;
    }

    public static boolean isBlockBelowSolid(World worldIn, BlockPos pos)    {
        return worldIn.getBlockState(pos.add(0, -1, 0)).getBlock().getMaterial().isSolid();
    }

    public static boolean isBlockBelowFullCube(World worldIn, BlockPos pos)    {
        return worldIn.getBlockState(pos.down()).getBlock().isFullCube();
    }

    public static boolean isBlockBelowCollidable(World worldIn, BlockPos pos)    {
        return worldIn.getBlockState(pos.down()).getBlock().isCollidable();
    }

    public static boolean checkBetweenPosForLava(BlockPos start, BlockPos end, World worldIn) {
        // check for every pos if x y z is bigger smaller than x y z of end
        // if yes then switch x y z
        int x1 = start.getX();
        int y1 = start.getY();
        int z1 = start.getZ();
        int x2 = end.getX();
        int y2 = end.getY();
        int z2 = end.getZ();
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        if (z1 > z2) {
            int temp = z1;
            z1 = z2;
            z2 = temp;
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    if (checkForLava(new BlockPos(i, j, k), worldIn))
                        return true;

                }
            }
        }
        return false;
    }

    public static boolean checkNeighboursForLava(BlockPos pos, World worldIn){
        if (checkForLava(pos, worldIn)) return true;
        if (checkForLava(pos.up(), worldIn)) return true;
        if (checkForLava(pos.down(), worldIn)) return true;
        if (checkForLava(pos.north(), worldIn)) return true;
        if (checkForLava(pos.east(), worldIn)) return true;
        if (checkForLava(pos.south(), worldIn)) return true;
        return checkForLava(pos.west(), worldIn);
    }
    public static boolean checkNeighboursForWater(BlockPos pos, World worldIn){
        if (checkForWater(pos, worldIn)) return true;
        if (checkForWater(pos.up(), worldIn)) return true;
        if (checkForWater(pos.down(), worldIn)) return true;
        if (checkForWater(pos.north(), worldIn)) return true;
        if (checkForWater(pos.east(), worldIn)) return true;
        if (checkForWater(pos.south(), worldIn)) return true;
        return checkForWater(pos.west(), worldIn);
    }
    public static boolean checkForLava(BlockPos pos, World worldIn){
        return worldIn.getBlockState(pos).getBlock().getMaterial() == Material.lava;
    }

    public static boolean checkForWater(BlockPos pos, World worldIn){
        return worldIn.getBlockState(pos).getBlock().getMaterial() == Material.water;
    }
}
