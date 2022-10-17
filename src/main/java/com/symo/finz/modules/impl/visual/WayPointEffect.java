package com.symo.finz.modules.impl.visual;

import com.symo.finz.FinZ;
import com.symo.finz.utils.RGBColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WayPointEffect {


    private static final ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");

    public static void renderBeamAt(double x, double y, double z) {
        int color = RGBColor.getRainbow(10);

        // render a waypoint beam

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
        float f = (float) FinZ.mc.thePlayer.ticksExisted;// + FinZ.mc.getRender();
        GlStateManager.color(0, 1.0F, 0, 0.15f);
        FinZ.mc.getTextureManager().bindTexture(beaconBeam);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(-1.0D, -1.0D, -1.0D).tex(0.0D, 0.0D).color(0, 1, 0, 0.15f).endVertex();
        worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 1.0D).color(0, 1, 0, 0.15f).endVertex();
        worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 1.0D).color(0, 1, 0, 0.15f).endVertex();
        worldrenderer.pos(1.0D, -1.0D, -1.0D).tex(1.0D, 0.0D).color(0, 1, 0, 0.15f).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
}
