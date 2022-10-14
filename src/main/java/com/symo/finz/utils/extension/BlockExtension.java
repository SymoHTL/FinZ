package com.symo.finz.utils.extension;

import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockExtension {

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x1 - x2;
        double d1 = y1 - y2;
        double d2 = z1 - z2;
        return (double) MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public static boolean isBlockBelowAir(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.add(0, -1, 0)).getBlock().getMaterial() == Material.air;
    }

    public static boolean isBlockBelowSolid(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.add(0, -1, 0)).getBlock().getMaterial().isSolid();
    }

    public static boolean isBlockBelowFullCube(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getBlock().isFullCube();
    }

    public static boolean isBlockBelowCollidable(World worldIn, BlockPos pos) {
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

    public static boolean checkNeighboursForLava(BlockPos pos, World worldIn) {
        if (checkForLava(pos, worldIn)) return true;
        if (checkForLava(pos.up(), worldIn)) return true;
        if (checkForLava(pos.down(), worldIn)) return true;
        if (checkForLava(pos.north(), worldIn)) return true;
        if (checkForLava(pos.east(), worldIn)) return true;
        if (checkForLava(pos.south(), worldIn)) return true;
        return checkForLava(pos.west(), worldIn);
    }

    public static boolean checkNeighboursForWater(BlockPos pos, World worldIn) {
        if (checkForWater(pos, worldIn)) return true;
        if (checkForWater(pos.up(), worldIn)) return true;
        if (checkForWater(pos.down(), worldIn)) return true;
        if (checkForWater(pos.north(), worldIn)) return true;
        if (checkForWater(pos.east(), worldIn)) return true;
        if (checkForWater(pos.south(), worldIn)) return true;
        return checkForWater(pos.west(), worldIn);
    }

    public static boolean checkForLava(BlockPos pos, World worldIn) {
        return worldIn.getBlockState(pos).getBlock().getMaterial() == Material.lava;
    }

    public static boolean checkForWater(BlockPos pos, World worldIn) {
        return worldIn.getBlockState(pos).getBlock().getMaterial() == Material.water;
    }

    public static boolean isWestAir(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.add(-1, 0, 0)).getBlock().getMaterial() == Material.air;
    }

    public static boolean isEastAir(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.add(1, 0, 0)).getBlock().getMaterial() == Material.air;
    }

    public static boolean isNorthAir(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.add(0, 0, -1)).getBlock().getMaterial() == Material.air;
    }

    public static boolean isSouthAir(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.add(0, 0, 1)).getBlock().getMaterial() == Material.air;
    }

    public static boolean isUpAir(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.add(0, 1, 0)).getBlock().getMaterial() == Material.air;
    }

    public static boolean isDownAir(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.add(0, -1, 0)).getBlock().getMaterial() == Material.air;
    }


    public static EnumFacing getVisibleSide(BlockPos closestBlockPos, EntityPlayerSP thePlayer) {
        BlockPos playerPos = PlayerExtension.getAccurateBlockPos();
        // west -- -x
        if (isWestAir(thePlayer.worldObj, closestBlockPos) && closestBlockPos.getX() > playerPos.getX())
            return EnumFacing.WEST;
        // east -- +x
        if (isEastAir(thePlayer.worldObj, closestBlockPos) && closestBlockPos.getX() < playerPos.getX())
            return EnumFacing.EAST;
        // north -- -z
        if (isNorthAir(thePlayer.worldObj, closestBlockPos) && closestBlockPos.getZ() > playerPos.getZ())
            return EnumFacing.NORTH;
        // south -- +z
        if (isSouthAir(thePlayer.worldObj, closestBlockPos) && closestBlockPos.getZ() < playerPos.getZ())
            return EnumFacing.SOUTH;
        // up -- +y
        if (isUpAir(thePlayer.worldObj, closestBlockPos) && closestBlockPos.getY() <= playerPos.getY())
            return EnumFacing.UP;
        // down -- -y
        if (isDownAir(thePlayer.worldObj, closestBlockPos) && closestBlockPos.getY() > playerPos.getY())
            return EnumFacing.DOWN;


        return null;







        /*
        // get the visible side of the block and check if there is a block
        // if no then return the side
        double d0 = thePlayer.posX - closestBlockPos.getX();
        double d1 = thePlayer.posY - closestBlockPos.getY();
        double d2 = thePlayer.posZ - closestBlockPos.getZ();
        double d3 = MathHelper.abs((float) d0);
        double d4 = MathHelper.abs((float) d1);
        double d5 = MathHelper.abs((float) d2);
        if (d3 > d5) {
            if (d3 > d4) {
                if (d0 > 0.0D) {
                    if (thePlayer.worldObj.getBlockState(closestBlockPos.add(-1, 0, 0)).getBlock().getMaterial() == Material.air) {
                        return EnumFacing.EAST;
                    }
                } else {
                    if (thePlayer.worldObj.getBlockState(closestBlockPos.add(1, 0, 0)).getBlock().getMaterial() == Material.air) {
                        return EnumFacing.WEST;
                    }

                }
            } else {
                if (d1 > 0) {
                    if (thePlayer.worldObj.getBlockState(closestBlockPos.add(0, 1, 0)).getBlock().getMaterial() == Material.air) {
                        return EnumFacing.UP;
                    }
                } else {
                    if (thePlayer.worldObj.getBlockState(closestBlockPos.add(0, -1, 0)).getBlock().getMaterial() == Material.air) {
                        return EnumFacing.DOWN;
                    }
                }
            }
        } else if (d5 > d4) {
            if (d2 > 0) {
                if (thePlayer.worldObj.getBlockState(closestBlockPos.add(0, 0, 1)).getBlock().getMaterial() == Material.air) {
                    return EnumFacing.SOUTH;
                }
            }else {
                if (thePlayer.worldObj.getBlockState(closestBlockPos.add(0, 0, -1)).getBlock().getMaterial() == Material.air) {
                    return EnumFacing.NORTH;
                }
            }
        } else {
            if (d1 > 0) {
                if (thePlayer.worldObj.getBlockState(closestBlockPos.add(0, 1, 0)).getBlock().getMaterial() == Material.air) {
                    return EnumFacing.UP;
                }
            } else {
                if (thePlayer.worldObj.getBlockState(closestBlockPos.add(0, -1, 0)).getBlock().getMaterial() == Material.air) {
                    return EnumFacing.DOWN;
                }
            }
        }
        return null;
        */
    }
}
