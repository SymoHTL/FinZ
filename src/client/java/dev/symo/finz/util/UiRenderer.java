package dev.symo.finz.util;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.symo.finz.FinZClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class UiRenderer {

    public static Vec3d sanitizeScreenPos(Vec3d vec, int padding){
        if (vec.z > -1 && vec.z < 1)  return vec;
        // now it isnt visible so we snap the x and y to the screen edge
        // to make it looklike it is coming from behind for the palyer
        return snapToScreenEdge(vec, padding);
    }

    public static Vec3d snapToScreenEdge(Vec3d vec, int padding) {
        var screen = FinZClient.mc.getWindow();
        double x = vec.x;
        double y = vec.y;
        double z = vec.z;
        var halfWidth = screen.getScaledWidth() / 2;
        if (x > halfWidth) x = padding;
        else x = screen.getScaledWidth() - padding;
        return new Vec3d(x, y, z);
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
        //matrices.translate((float) (x + 8), (float) (y + 8), 150);
        matrices.translate(x , y, 150);

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

    public static void drawEntity(DrawContext context, int x, int y, int size, float mouseY, LivingEntity entity) {
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
        entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, context.getMatrices(), context.getVertexConsumers(), 15728880);
        context.draw();
        entityRenderDispatcher.setRenderShadows(true);
        context.getMatrices().pop();
        DiffuseLighting.enableGuiDepthLighting();
    }

}
