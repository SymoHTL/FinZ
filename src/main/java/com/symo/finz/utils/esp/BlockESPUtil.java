package com.symo.finz.utils.esp;

import com.symo.finz.FinZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public class BlockESPUtil {
    public static final Minecraft mc;

    public static void drawESP(BlockPos blockPos) {
        drawESP(blockPos, 0.4f, 2.0f);
    }

    public static void drawESP(BlockPos blockPos, float alpha, float lineWidth) {
        double x = blockPos.getX() - mc.getRenderManager().viewerPosX;
        double y = blockPos.getY() - mc.getRenderManager().viewerPosY;
        double z = blockPos.getZ() - mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4d(0, 0, 1, 0.4f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        // Box
        GL11.glColor4d(0, 0, 1, alpha);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawESP(BlockPos blockPos, float alpha, float lineWidth, int r, int g, int b) {
        double x = blockPos.getX() - mc.getRenderManager().viewerPosX;
        double y = blockPos.getY() - mc.getRenderManager().viewerPosY;
        double z = blockPos.getZ() - mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4d(0, 0, 1, 0.15f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        // Box
        GL11.glColor4d(r, g, b, alpha);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawSquareWhereToAim(double[] coords, float alpha, float lineWidth) {
        double x = coords[0] - mc.getRenderManager().viewerPosX;
        double y = coords[1] - mc.getRenderManager().viewerPosY;
        double z = coords[2] - mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4d(0, 1, 0, 0.15f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        // Box
        GL11.glColor4d(0, 1, 0, alpha);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x - 0.05, y - 0.05, z - 0.05, x + 0.05, y + 0.05, z + 0.05));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawESP(double x, double y, double z, double x2, double y2, double z2, float alpha, float lineWidth, int r, int g, int b) {
        x -= mc.getRenderManager().viewerPosX;
        y -= mc.getRenderManager().viewerPosY;
        z -= mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4d(r, g, b, alpha);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        // Box
        GL11.glColor4d(r, g, b, alpha);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawESP(double x, double y, double z, double x2, double y2, double z2, Color color) {
        x -= mc.getRenderManager().viewerPosX;
        y -= mc.getRenderManager().viewerPosY;
        z -= mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2);
        GL11.glColor4d(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        // Box
        GL11.glColor4d(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawBeacon(double x, double y, double z, Color color) {
        x -= mc.getRenderManager().viewerPosX;
        y -= mc.getRenderManager().viewerPosY;
        z -= mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2);
        GL11.glColor4d(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        //GL11.glDisable(GL11.GL_DEPTH_TEST);
        //GL11.glDepthMask(false);

        // Box
        GL11.glColor4d(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x + 0.25, y, z + 0.25, x + 0.75, 255, z + 0.75));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        //GL11.glEnable(GL11.GL_DEPTH_TEST);
        //GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawESPWithBody(BlockPos blockPos, float alpha, float lineWidth) {
        double x = blockPos.getX() - mc.getRenderManager().viewerPosX;
        double y = blockPos.getY() - mc.getRenderManager().viewerPosY;
        double z = blockPos.getZ() - mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4d(1, 1, 0, 0.15f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        // Box
        GL11.glColor4d(1, 1, 1, alpha);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x + 0.25, y + 0.25, z + 0.25, x + 0.75, y + 0.75, z + 0.75));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawPathLine(BlockPos currentBlock, BlockPos lastBlock, float alpha, float lineWidth) {
        double currentX = currentBlock.getX() - mc.getRenderManager().viewerPosX;
        double currentY = currentBlock.getY() - mc.getRenderManager().viewerPosY;
        double currentZ = currentBlock.getZ() - mc.getRenderManager().viewerPosZ;
        double lastX = lastBlock.getX() - mc.getRenderManager().viewerPosX;
        double lastY = lastBlock.getY() - mc.getRenderManager().viewerPosY;
        double lastZ = lastBlock.getZ() - mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4d(0, 0, 1, 0.15f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        // Box
        GL11.glColor4d(0, 0, 1, alpha);
        // draw a line from the middle of the last block to the middle of the current block
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(lastX + 0.5, lastY + 1, lastZ + 0.5);
        GL11.glVertex3d(currentX + 0.5, currentY + 1, currentZ + 0.5);
        GL11.glEnd();

        //RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(lastX + 0.5, lastY + 0.5, lastZ + 0.5, currentX + 0.5, currentY + 0.5, currentZ + 0.5));


        //RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(currentX + 0.25, currentY + 1, currentZ, currentX + 0.75, currentY + 1, currentZ + 1));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    static {
        mc = FinZ.mc;
    }


}
