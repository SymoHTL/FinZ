package dev.symo.finz.mixin.client;

import dev.symo.finz.modules.Modules;
import dev.symo.finz.util.ColorUtil;
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

        if (Modules.chromaOutline.isEnabled()){
            int color = ColorUtil.getRainbowColor();
            red = (float) (color >> 16 & 255) / 255.0F;
            green = (float) (color >> 8 & 255) / 255.0F;
            blue = (float) (color & 255) / 255.0F;
        }

        // chroma outline
        drawCuboidShapeOutline(matrices, vertexConsumer, state.getOutlineShape(world, pos, ShapeContext.of(entity)),
                (double) pos.getX() - cameraX, (double) pos.getY() - cameraY, (double) pos.getZ() - cameraZ,
                red, green, blue, 0.4f);
    }


}
