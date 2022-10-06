package finz.util.esp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class EntityESPUtil {

    public static void drawESP(EntityLivingBase entity) {
        drawESP(entity, 0.4f, 2.0f);
    }

    public static void drawESP(EntityLivingBase entity, float alpha, float lineWidth) {
        double x = entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double y = entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double z = entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4d(1, 0, 0, 0.15f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        double startX = x - entity.width / 2;
        double startZ = z - entity.width / 2;

        double endX = startX + entity.width;
        double endY = y + entity.height;
        double endZ = startZ + entity.width;

        // Box
        GL11.glColor4d(1, 0, 0, alpha);
        RenderGlobal.func_181561_a(new AxisAlignedBB(startX, y, startZ, endX, endY, endZ));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

}
