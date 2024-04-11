package dev.symo.finz.util;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.symo.finz.FinZClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.http.util.EntityUtils;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;


public class WorldSpaceRenderer {
    private static Vec3d applyRegionalRenderOffset(MatrixStack matrixStack) {
        Vec3d camPos = ClientUtil.getCameraPos();
        BlockPos blockPos = ClientUtil.getCameraBlockPos();

        int regionX = (blockPos.getX() >> 9) * 512;
        int regionZ = (blockPos.getZ() >> 9) * 512;
        var vec = new Vec3d(regionX - camPos.x, -camPos.y, regionZ - camPos.z);
        matrixStack.translate(vec.x, vec.y, vec.z);
        return vec;
    }

    public static RegionPos getCameraRegion() {
        return RegionPos.of(ClientUtil.getCameraBlockPos());
    }

    public static void applyRegionalRenderOffset(MatrixStack matrixStack,
                                                 RegionPos region) {
        Vec3d offset = region.toVec3d().subtract(ClientUtil.getCameraPos());
        matrixStack.translate(offset.x, offset.y, offset.z);
    }

    public static void setupGlESPSettings() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void resetGlESPSettings() {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void renderEntitiesEsp(MatrixStack matrixStack, float partialTicks, List<Entity> entities) {
        setupGlESPSettings();

        matrixStack.push();

        RegionPos region = getCameraRegion();
        applyRegionalRenderOffset(matrixStack, region);

        renderEntitiesEsp(matrixStack, partialTicks, region, entities);

        matrixStack.pop();

        resetGlESPSettings();
    }


    public static void renderEntitiesEsp(MatrixStack matrixStack, float partialTicks, RegionPos pos, List<Entity> entities) {
        for (Entity entity : entities) {
            matrixStack.push();

            Vec3d lerpedPos = EntityUtil.getLerpedPos(entity, partialTicks).subtract(pos.toVec3d());
            matrixStack.translate(lerpedPos.x, lerpedPos.y, lerpedPos.z);

            matrixStack.scale(entity.getWidth(), entity.getHeight(), entity.getWidth());

            if (entity instanceof LivingEntity livingEntity) {
                var hpPercent = livingEntity.getHealth() / livingEntity.getMaxHealth();
                var color = ColorUtil.PercentageToColor(hpPercent * 100);
                RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.5f);
            }else
                RenderSystem.setShaderColor(1, 1, 1, 0.5f);

            Box bb = new Box(-0.5, 0, -0.5, 0.5, 1, 0.5);
            drawOutlinedBox(bb, matrixStack);

            matrixStack.pop();
        }
    }


    public static void renderLine(MatrixStack matrixStack, BlockPos start, BlockPos end, RegionPos region) {
        matrixStack.push();
        var vect = region.toVec3d();
        matrixStack.translate(start.getX() - vect.x, start.getY() - vect.y, start.getZ() - vect.z);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);
        bufferBuilder.vertex(matrix, 0.5f, 1f, 0.5f).next();
        bufferBuilder.vertex(matrix, end.getX() - start.getX() + 0.5f, end.getY() - start.getY() +1f, end.getZ() - start.getZ() + 0.5f).next();
        tessellator.draw();

        matrixStack.pop();
    }


    public static void renderBlocks(MatrixStack matrixStack, Color color, Stream<BlockPos> blocks) {
        setupGlESPSettings();

        matrixStack.push();

        RegionPos region = getCameraRegion();
        applyRegionalRenderOffset(matrixStack, region);

        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.5f);

        blocks.forEach(block -> renderBlock(matrixStack, color, block, region));

        matrixStack.pop();

        resetGlESPSettings();
    }

    public static void renderBlock(MatrixStack matrixStack, Color color, BlockPos block, RegionPos region) {
        matrixStack.push();

        var vect = region.toVec3d();
        matrixStack.translate(block.getX() - vect.x, block.getY() - vect.y, block.getZ() - vect.z);

        Box bb = new Box(0, 0, 0, 1, 1, 1);
        drawOutlinedBox(bb, matrixStack);

        matrixStack.pop();
    }

    public static void drawOutlinedBox(Box bb, MatrixStack matrixStack) {
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES,
                VertexFormats.POSITION);
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.minZ)
                .next();
        tessellator.draw();
    }

}
