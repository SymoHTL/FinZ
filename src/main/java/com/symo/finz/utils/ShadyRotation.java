package com.symo.finz.utils;

import com.symo.finz.FinZ;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ShadyRotation
{
    private static float pitchDifference;
    public static float yawDifference;
    private static int ticks;
    private static int tickCounter;
    private static Runnable callback;
    private static boolean async;
    private static float serverPitch;
    private static float serverYaw;
    public static boolean running;
    public static boolean runningAsync;
    
    private static double wrapAngleTo180(final double angle) {
        return angle - Math.floor(angle / 360.0 + 0.5) * 360.0;
    }
    
    private static float wrapAngleTo180(final float angle) {
        return (float)(angle - Math.floor(angle / 360.0f + 0.5) * 360.0);
    }
    
    public static Rotation getRotationToBlock(final BlockPos block) {
        final double diffX = block.getX() - FinZ.mc.thePlayer.posX + 0.5;
        final double diffY = block.getY() - FinZ.mc.thePlayer.posY + 0.5 - FinZ.mc.thePlayer.getEyeHeight();
        final double diffZ = block.getZ() - FinZ.mc.thePlayer.posZ + 0.5;
        final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float pitch = (float)(-Math.atan2(dist, diffY));
        float yaw = (float)Math.atan2(diffZ, diffX);
        pitch = (float)wrapAngleTo180((pitch * 180.0f / 3.141592653589793 + 90.0) * -1.0);
        yaw = (float)wrapAngleTo180(yaw * 180.0f / 3.141592653589793 - 90.0);
        return new Rotation(pitch, yaw);
    }
    
    public static Rotation getRotationToEntity(final Entity entity) {
        final double diffX = entity.posX - FinZ.mc.thePlayer.posX;
        final double diffY = entity.posY + entity.getEyeHeight() - FinZ.mc.thePlayer.posY - FinZ.mc.thePlayer.getEyeHeight();
        final double diffZ = entity.posZ - FinZ.mc.thePlayer.posZ;
        final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float pitch = (float)(-Math.atan2(dist, diffY));
        float yaw = (float)Math.atan2(diffZ, diffX);
        pitch = (float)wrapAngleTo180((pitch * 180.0f / 3.141592653589793 + 90.0) * -1.0);
        yaw = (float)wrapAngleTo180(yaw * 180.0f / 3.141592653589793 - 90.0);
        return new Rotation(pitch, yaw);
    }
    
    public static Rotation vec3ToRotation(final Vec3 vec) {
        final double diffX = vec.xCoord - FinZ.mc.thePlayer.posX;
        final double diffY = vec.yCoord - FinZ.mc.thePlayer.posY - FinZ.mc.thePlayer.getEyeHeight();
        final double diffZ = vec.zCoord - FinZ.mc.thePlayer.posZ;
        final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float pitch = (float)(-Math.atan2(dist, diffY));
        float yaw = (float)Math.atan2(diffZ, diffX);
        pitch = (float)wrapAngleTo180((pitch * 180.0f / 3.141592653589793 + 90.0) * -1.0);
        yaw = (float)wrapAngleTo180(yaw * 180.0f / 3.141592653589793 - 90.0);
        return new Rotation(pitch, yaw);
    }
    
    public static void smoothLook(final Rotation rotation, final int ticks, final Runnable callback, final boolean async) {
        if (ticks == 0) {
            look(rotation);
            callback.run();
            return;
        }
        ShadyRotation.callback = callback;
        ShadyRotation.async = rotation.async;
        ShadyRotation.pitchDifference = wrapAngleTo180(rotation.pitch - FinZ.mc.thePlayer.rotationPitch); // TODO SUS rotationPitch !!!
        ShadyRotation.yawDifference = wrapAngleTo180(rotation.yaw - FinZ.mc.thePlayer.rotationYaw); // as well
        ShadyRotation.ticks = ticks * 20;
        ShadyRotation.tickCounter = 0;
    }
    
    public static void smoothLook(final Rotation rotation, final int ticks, final Runnable callback) {
        smoothLook(rotation, ticks, callback, false);
    }
    
    public static void smartLook(final Rotation rotation, final int ticksPer180, final Runnable callback) {
        final float rotationDifference = Math.max(Math.abs(rotation.pitch - FinZ.mc.thePlayer.rotationPitch), Math.abs(rotation.yaw - FinZ.mc.thePlayer.rotationYaw));
        smoothLook(rotation, (int)(rotationDifference / 180.0f * ticksPer180), callback);
    }
    
    public static void look(final Rotation rotation) {
        FinZ.mc.thePlayer.rotationPitch = rotation.pitch;
        FinZ.mc.thePlayer.rotationYaw = rotation.yaw;
    }
    
//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    public void onUpdatePre(final PlayerMoveEvent.Pre pre) {
//        ShadyRotation.serverPitch = FinZ.mc.thePlayer.rotationPitch;
//        ShadyRotation.serverYaw = FinZ.mc.thePlayer.rotationYaw;
//    }
//
//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    public void onUpdatePost(final PlayerMoveEvent.Post post) {
//        FinZ.mc.thePlayer.rotationPitch = ShadyRotation.serverPitch;
//        FinZ.mc.thePlayer.rotationYaw = ShadyRotation.serverYaw;
//    }
    
    @SubscribeEvent
    public void onTick(final TickEvent event) {
        if (FinZ.mc.thePlayer == null) {
            return;
        }
        if (ShadyRotation.tickCounter < ShadyRotation.ticks) { // false at start
            if (!ShadyRotation.async) {
                ShadyRotation.running = true;
                ShadyRotation.runningAsync = false;
            }
            else {
                ShadyRotation.runningAsync = true;
                ShadyRotation.running = false;
            }
            EntityPlayerSP thePlayer = FinZ.mc.thePlayer;
            thePlayer.rotationPitch += ShadyRotation.pitchDifference / ShadyRotation.ticks;
            thePlayer.rotationYaw += ShadyRotation.yawDifference / ShadyRotation.ticks;
            //final EntityPlayerSP thePlayer = FinZ.mc.thePlayer;
            //thePlayer.rotationPitch += ShadyRotation.pitchDifference / ShadyRotation.ticks;
            //final EntityPlayerSP thePlayer2 = FinZ.mc.thePlayer;
            //thePlayer2.rotationYaw += ShadyRotation.yawDifference / ShadyRotation.ticks;
            ++ShadyRotation.tickCounter;
        }
        else if (ShadyRotation.callback != null) {
            if (!ShadyRotation.async) {
                ShadyRotation.running = false;
            }
            else {
                ShadyRotation.runningAsync = false;
            }
            ShadyRotation.callback.run();
            ShadyRotation.callback = null;
        }
    }
    
    static {
        ShadyRotation.ticks = -1;
        ShadyRotation.tickCounter = 0;
        ShadyRotation.callback = null;
        ShadyRotation.async = false;
        ShadyRotation.running = false;
        ShadyRotation.runningAsync = false;
    }
    
    public static class Rotation
    {
        public float pitch;
        public float yaw;
        public boolean async;
        
        public Rotation(final float pitch, final float yaw) {
            this.pitch = pitch;
            this.yaw = yaw;
            this.async = false;
        }
        
        public Rotation(final float pitch, final float yaw, final boolean async) {
            this.pitch = pitch;
            this.yaw = yaw;
            this.async = async;
        }
        
        public void setAsync(final boolean async) {
            this.async = async;
        }
    }
}