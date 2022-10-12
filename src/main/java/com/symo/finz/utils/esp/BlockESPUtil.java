package com.symo.finz.utils.esp;

import com.symo.finz.FinZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class BlockESPUtil {
    public static void drawESP(BlockPos blockPos) {
        drawESP(blockPos, 0.4f, 2.0f);
    }

    public static void drawESP(BlockPos blockPos, float alpha, float lineWidth) {
        double x = blockPos.getX() - FinZ.mc.getRenderManager().viewerPosX;
        double y = blockPos.getY() - FinZ.mc.getRenderManager().viewerPosY;
        double z = blockPos.getZ() - FinZ.mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4d(0, 0, 1, 0.15f);
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

    // TODO get this to work
    public static void drawDebugBox(double x, double y, double z) {
        // Box
        double x1 = x - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double y1 = y - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double z1 = z - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        GL11.glColor4d(0, 0, 1, 0.2F);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x1, y1, z1, x1 + 0.3, y1 + 0.3, z1 + 0.3));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
