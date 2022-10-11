package com.symo.finz.utils.extension;

import com.sun.javafx.geom.Vec3d;
import com.symo.finz.FinZ;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerExtension {

    static EntityPlayerSP player = FinZ.mc.thePlayer;

    static Vec3 getEyePosition(){
        return new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ);
    }

    @Nullable
    public static EntityLivingBase getClosetMob(){
        return getLivingEntities().stream()
                .filter(entity -> entity instanceof EntityMob)
                .sorted(Comparator.comparingDouble(player::getDistanceToEntity))
                .collect(Collectors.toList()).get(0);
    }


    @Nullable
    public static EntityLivingBase getClosetAnimal(){
        return getLivingEntities().stream()
                .filter(entity -> entity instanceof EntityAnimal)
                .sorted(Comparator.comparingDouble(player::getDistanceToEntity))
                .collect(Collectors.toList()).get(0);
    }
    @Nullable
    public static EntityLivingBase getClosetPlayer(){
        return getLivingEntities().stream()
                .filter(entity -> entity instanceof EntityPlayer)
                .sorted(Comparator.comparingDouble(player::getDistanceToEntity))
                .collect(Collectors.toList()).get(0);
    }
    @Nullable
    public static EntityLivingBase getClosetLivingEntity(){
        return getLivingEntities().stream()
                .sorted(Comparator.comparingDouble(player::getDistanceToEntity))
                .collect(Collectors.toList()).get(0);
    }

    public static List<EntityLivingBase> getLivingEntities(){
        // get all living Entities
        List<EntityLivingBase> livingBases = FinZ.mc.theWorld.getLoadedEntityList().stream()
                .filter(entity -> entity instanceof EntityLivingBase)
                .map(entity -> (EntityLivingBase)entity).collect(Collectors.toList());
        // filter out dead entities
        livingBases = livingBases.stream().filter(entity ->
                        entity != player &&
                                !entity.isDead &&
                                entity.getHealth() > 0)
                .collect(Collectors.toList());
        // sot by distance
        return livingBases;
    }



    public static boolean canSeeEyesMidOrFeet(Entity entityIn)
    {
        return canSeeEyes(entityIn) || canSeeMid(entityIn) || canSeeFeet(entityIn);
    }
    public static boolean canSeeEyes(Entity entityIn)
    {
        return FinZ.mc.theWorld.rayTraceBlocks(getEyePosition(), new Vec3(entityIn.posX, entityIn.posY + (double)entityIn.getEyeHeight(), entityIn.posZ)) == null;
    }
    public static boolean canSeeMid(Entity entityIn)
    {
        return FinZ.mc.theWorld.rayTraceBlocks(getEyePosition(), new Vec3(entityIn.posX, entityIn.posY + entityIn.height * 0.5D, entityIn.posZ)) == null;
    }
    public static boolean canSeeFeet(Entity entityIn)
    {
        return FinZ.mc.theWorld.rayTraceBlocks(getEyePosition(), new Vec3(entityIn.posX, entityIn.posY + entityIn.height * 0.15D, entityIn.posZ)) == null;
    }

    public static BlockPos getAccurateBlockPos() {
        return new BlockPos(FinZ.mc.getRenderViewEntity().posX, FinZ.mc.getRenderViewEntity().getEntityBoundingBox().minY, FinZ.mc.getRenderViewEntity().posZ);
    }

    public static boolean hasBlockAbove() {
        return player.getEntityWorld().getBlockState(getBlockPosAbove()).getBlock().getMaterial() != Material.air;
    }
    public static boolean hasBlockBelow() {
        return player.getEntityWorld().getBlockState(getBlockPosBelow()).getBlock().getMaterial() != Material.air;
    }

    public static BlockPos getBlockPosAbove() {
        BlockPos pos = getAccurateBlockPos();
        return new BlockPos(pos.getX(), pos.getY()+2, pos.getZ());
    }

    public static BlockPos getBlockPosBelow() {
        BlockPos pos = getAccurateBlockPos();
        return new BlockPos(pos.getX(), pos.getY()-1, pos.getZ());
    }

}
