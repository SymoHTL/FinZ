package dev.symo.finz.util;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.symo.finz.FinZClient;
import net.fabricmc.fabric.mixin.blockview.WorldViewMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

public class UiRenderer {

    @ApiStatus.Internal
    public static final Matrix4f lastProjMat = new Matrix4f();
    @ApiStatus.Internal
    public static final Matrix4f lastModMat = new Matrix4f();
    @ApiStatus.Internal
    public static final Matrix4f lastWorldSpaceMatrix = new Matrix4f();

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static Vec3d worldSpaceToScreenSpace(Vec3d pos) {
        Camera camera = client.gameRenderer.getCamera();
        int displayHeight = client.getWindow().getHeight();
        int[] viewport = new int[4];
        RenderSystem.recordRenderCall(() -> GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport)); // Ensuring this runs in the render thread

        Vector3f cameraPos = new Vector3f((float) camera.getPos().x, (float) camera.getPos().y, (float) camera.getPos().z);
        Vector3f deltaPos = new Vector3f((float) (pos.x - cameraPos.x), (float) (pos.y - cameraPos.y), (float) (pos.z - cameraPos.z));

        // Create the transformation matrix for model-view-projection
        Matrix4f transformationMatrix = new Matrix4f(lastModMat);
        transformationMatrix.mul(lastProjMat); // Apply the projection matrix

        // Create and transform the world coordinates
        Vector4f worldCoords = new Vector4f(deltaPos.x(), deltaPos.y(), deltaPos.z(), 1.0f);
        worldCoords.mul(transformationMatrix);

        if (worldCoords.w() == 0) {
            // Avoid division by zero in perspective divide
            return null;
        }

        // Perform the perspective divide to get normalized device coordinates
        float ndcX = worldCoords.x() / worldCoords.w();
        float ndcY = worldCoords.y() / worldCoords.w();
        float ndcZ = worldCoords.z() / worldCoords.w();

        // Convert NDC to window coordinates
        float windowX = viewport[0] + (ndcX + 1) * viewport[2] / 2;
        float windowY = viewport[1] + (1 - ndcY) * viewport[3] / 2; // Y is inverted in OpenGL
        float windowZ = (ndcZ + 1) / 2;

        return new Vec3d(windowX / client.getWindow().getScaleFactor(),
                (displayHeight - windowY) / client.getWindow().getScaleFactor(),
                windowZ);
    }


    public static void drawRectDouble(DrawContext drawContext, double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        drawContext.fill((int) left, (int) top, (int) right, (int) bottom, color);
    }

    public static void drawItem(DrawContext context, ItemStack stack, int x, int y, float scale) {
        if (stack.isEmpty()) return;
        var matrices = context.getMatrices();
        var vertexConsumers = context.getVertexConsumers();
        BakedModel bakedModel = FinZClient.mc.getItemRenderer().getModel(stack, FinZClient.mc.world, FinZClient.mc.player, 0);
        matrices.push();
        matrices.translate((float) (x + 8), (float) (y + 8), 150);

        matrices.multiplyPositionMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
        matrices.scale(scale, scale, scale);
        boolean bl = !bakedModel.isSideLit();
        if (bl) DiffuseLighting.disableGuiDepthLighting();


        FinZClient.mc.getItemRenderer().renderItem(stack, ModelTransformationMode.GUI, false, matrices, vertexConsumers, 15728880, OverlayTexture.DEFAULT_UV, bakedModel);
        RenderSystem.disableDepthTest();
        vertexConsumers.draw();
        RenderSystem.enableDepthTest();
        if (bl) DiffuseLighting.enableGuiDepthLighting();
        matrices.pop();
    }

    public static void drawEntity(DrawContext context, int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
        float i = (float) Math.atan((x - mouseX) / 40.0F);
        float j = (float) Math.atan((y - mouseY) / 40.0F);
        Quaternionf quaternionf = (new Quaternionf()).rotateZ(3.1415927F);
        Quaternionf quaternionf2 = (new Quaternionf()).rotateX(j * 20.0F * 0.017453292F);
        quaternionf.mul(quaternionf2);
        Vector3f vector3f = new Vector3f(0.0F, entity.getHeight() / 2.0F, 0.0F);
        drawEntity(context, x, y, size, vector3f, quaternionf, quaternionf2, entity);
    }

    private static void drawEntity(DrawContext context, float x, float y, int size, Vector3f vector3f, Quaternionf quaternionf, @Nullable Quaternionf quaternionf2, LivingEntity entity) {
        context.getMatrices().push();
        context.getMatrices().translate(x, y, 50.0D);
        context.getMatrices().multiplyPositionMatrix((new Matrix4f()).scaling((float) size, (float) size, (float) (-size)));
        context.getMatrices().translate(vector3f.x, vector3f.y, vector3f.z);
        context.getMatrices().multiply(quaternionf);
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        if (quaternionf2 != null) {
            quaternionf2.conjugate();
            entityRenderDispatcher.setRotation(quaternionf2);
        }

        entityRenderDispatcher.setRenderShadows(false);
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, context.getMatrices(), context.getVertexConsumers(), 15728880));
        context.draw();
        entityRenderDispatcher.setRenderShadows(true);
        context.getMatrices().pop();
        DiffuseLighting.enableGuiDepthLighting();
    }

}
