package dev.symo.finz.mixin.client;

import dev.symo.finz.FinZ;
import dev.symo.finz.FinZClient;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;

@Environment(net.fabricmc.api.EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class SelectedBlockOutLineMixin {

    @Shadow
    private ClientWorld world;

    @Shadow
    private static void drawCuboidShapeOutline(MatrixStack matrices, VertexConsumer vertexConsumer, VoxelShape shape, double offsetX, double offsetY, double offsetZ, float red, float green, float blue, float alpha) {
    }

    /**
     * @author Symo
     * @reason chroma outline
     */
    @Overwrite
    private void drawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos pos, BlockState state) {
        float red = 0;
        float green = 0;
        float blue = 0;

        if (FinZClient.config.chromaBlockOutlineEnabled) {
            var hue = (float) (System.currentTimeMillis() % 4000) / 4000;
            var color = Color.HSBtoRGB(hue, 1, 1);
            red = (color >> 16 & 255) / 255.0F;
            green = (color >> 8 & 255) / 255.0F;
            blue = (color & 255) / 255.0F;
        }

        // chroma outline
        drawCuboidShapeOutline(matrices, vertexConsumer, state.getOutlineShape(world, pos, ShapeContext.of(entity)),
                (double) pos.getX() - cameraX, (double) pos.getY() - cameraY, (double) pos.getZ() - cameraZ,
                red, green, blue, 0.4f);
    }


}
