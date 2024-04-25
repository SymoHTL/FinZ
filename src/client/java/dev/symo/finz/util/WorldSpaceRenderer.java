package dev.symo.finz.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;


public class WorldSpaceRenderer {

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

    public static void renderEntitiesEsp(MatrixStack matrixStack, float partialTicks, Collection<Entity> entities, Function<Entity, Color> outlineColor, Function<Entity, Color> faceColor, boolean drawFace) {
        setupGlESPSettings();
        if (drawFace) RenderSystem.disableCull();

        matrixStack.push();

        RegionPos region = getCameraRegion();
        applyRegionalRenderOffset(matrixStack, region);

        renderEntitiesEsp(matrixStack, partialTicks, region, entities, outlineColor, faceColor, drawFace);

        matrixStack.pop();

        resetGlESPSettings();
        if (drawFace) RenderSystem.enableCull();
    }


    private static void renderEntitiesEsp(MatrixStack matrixStack, float partialTicks, RegionPos pos, Collection<Entity> entities, Function<Entity, Color> outlineColor, Function<Entity, Color> faceColor, boolean drawFace) {
        for (Entity entity : entities) {
            matrixStack.push();

            Vec3d lerpedPos = EntityUtil.getLerpedPos(entity, partialTicks).subtract(pos.toVec3d());
            matrixStack.translate(lerpedPos.x, lerpedPos.y, lerpedPos.z);

            matrixStack.scale(entity.getWidth(), entity.getHeight(), entity.getWidth());

            Box bb = new Box(-0.5, 0, -0.5, 0.5, 1, 0.5);
            var color = outlineColor.apply(entity);
            RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.5f);
            drawOutlinedBox(bb, matrixStack);

            if (drawFace) {
                color = faceColor.apply(entity);
                RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.1f);
                drawFace(bb, matrixStack);
            }

            matrixStack.pop();
        }
    }


    private static void drawFace(Box bb, MatrixStack matrixStack) {
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        float minX = (float) bb.minX;
        float minY = (float) bb.minY;
        float minZ = (float) bb.minZ;
        float maxX = (float) bb.maxX;
        float maxY = (float) bb.maxY;
        float maxZ = (float) bb.maxZ;

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

        // Draw bottom face
        bufferBuilder.vertex(matrix, minX, minY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, minY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();
        bufferBuilder.vertex(matrix, minX, minY, maxZ).next();

        // Draw top face
        bufferBuilder.vertex(matrix, minX, maxY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();
        bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();

        // Draw front face
        bufferBuilder.vertex(matrix, minX, minY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, minY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();
        bufferBuilder.vertex(matrix, minX, maxY, minZ).next();

        // Draw back face
        bufferBuilder.vertex(matrix, minX, minY, maxZ).next();
        bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();
        bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();
        bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();

        // Draw left face
        bufferBuilder.vertex(matrix, minX, minY, minZ).next();
        bufferBuilder.vertex(matrix, minX, minY, maxZ).next();
        bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();
        bufferBuilder.vertex(matrix, minX, maxY, minZ).next();

        // Draw right face
        bufferBuilder.vertex(matrix, maxX, minY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();
        bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();
        bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();

        tessellator.draw();
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
        bufferBuilder.vertex(matrix, end.getX() - start.getX() + 0.5f, end.getY() - start.getY() + 1f, end.getZ() - start.getZ() + 0.5f).next();
        tessellator.draw();

        matrixStack.pop();
    }


    public static void renderBlocks(MatrixStack matrixStack, Color color, Stream<BlockPos> blocks) {
        setupGlESPSettings();

        matrixStack.push();

        RegionPos region = getCameraRegion();
        applyRegionalRenderOffset(matrixStack, region);

        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.5f);

        blocks.forEach(block -> renderBlock(matrixStack, block, region));

        matrixStack.pop();

        resetGlESPSettings();
    }

    public static void renderBlock(MatrixStack matrixStack, BlockPos block, RegionPos region) {
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

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);

        float minX = (float) bb.minX;
        float minY = (float) bb.minY;
        float minZ = (float) bb.minZ;
        float maxX = (float) bb.maxX;
        float maxY = (float) bb.maxY;
        float maxZ = (float) bb.maxZ;

        // Draw bottom face
        bufferBuilder.vertex(matrix, minX, minY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, minY, minZ).next();

        bufferBuilder.vertex(matrix, maxX, minY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();

        bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();
        bufferBuilder.vertex(matrix, minX, minY, maxZ).next();

        bufferBuilder.vertex(matrix, minX, minY, maxZ).next();
        bufferBuilder.vertex(matrix, minX, minY, minZ).next();

        // Draw top face
        bufferBuilder.vertex(matrix, minX, maxY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();

        bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();

        bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();
        bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();

        bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();
        bufferBuilder.vertex(matrix, minX, maxY, minZ).next();

        // Draw vertical lines (connectors)
        bufferBuilder.vertex(matrix, minX, minY, minZ).next();
        bufferBuilder.vertex(matrix, minX, maxY, minZ).next();

        bufferBuilder.vertex(matrix, maxX, minY, minZ).next();
        bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();

        bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();
        bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();

        bufferBuilder.vertex(matrix, minX, minY, maxZ).next();
        bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();

        tessellator.draw();
    }

    public static void renderTracers(MatrixStack matrixStack, float partialTicks, Set<Entity> players, Function<Entity, Color> getColor) {
        var region = RenderUtils.getCameraRegion();

        applyRegionalRenderOffset(matrixStack, region);

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES,
                VertexFormats.POSITION_COLOR);

        Vec3d regionVec = region.toVec3d();
        Vec3d start = RotationUtils.getClientLookVec(partialTicks)
                .add(RenderUtils.getCameraPos()).subtract(regionVec);


        setupGlESPSettings();

        for (Entity e : players) {
            Vec3d end = EntityUtil.getLerpedBox(e, partialTicks).getCenter()
                    .subtract(regionVec);

            var color = getColor.apply(e);

            bufferBuilder
                    .vertex(matrix, (float) start.x, (float) start.y, (float) start.z)
                    .color(color.getRGB()).next();

            bufferBuilder
                    .vertex(matrix, (float) end.x, (float) end.y, (float) end.z)
                    .color(color.getRGB()).next();
        }

        tessellator.draw();

        resetGlESPSettings();
    }

}
