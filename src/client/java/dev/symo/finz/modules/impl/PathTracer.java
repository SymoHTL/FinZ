package dev.symo.finz.modules.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.PlayerUtil;
import dev.symo.finz.util.RegionPos;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class PathTracer extends AModule {

    public List<BlockPos> path = new ArrayList<>();

    public PathTracer() {
        super("PathTracer", Category.RENDER);

    }

    @Override
    public boolean isEnabled() {
        return FinZClient.config.pathTracerEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        config.pathTracerEnabled = enabled;
    }

    public void onTick() {
        if (!isEnabled()) return;
        if (mc.player == null) return;
        if (mc.world == null) return;

        BlockPos blockPos = PlayerUtil.getBlockPosBelow();
        BlockState blockState = mc.world.getBlockState(blockPos);

        if (blockState.isSolidBlock(mc.world, blockPos)) {
            if (!path.contains(blockPos)) {
                path.add(blockPos);
            }
        }

        if (config.pathTracerLength < path.size())
            if (path.size() - config.pathTracerLength > 0)
                path.subList(0, path.size() - config.pathTracerLength).clear();

    }

    public void onWorldRender(MatrixStack matrixStack, float partialTicks) {
        if (!isEnabled()) return;
        if (mc.player == null) return;
        if (mc.world == null) return;

        var hue = (float) (System.currentTimeMillis() % 4000) / 4000;
        var color = Color.HSBtoRGB(hue, 1, 1);

        WorldSpaceRenderer.setupGlESPSettings();
        matrixStack.push();

        RegionPos region = WorldSpaceRenderer.getCameraRegion();
        WorldSpaceRenderer.applyRegionalRenderOffset(matrixStack, region);

        RenderSystem.setShaderColor((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, 1f);

        BlockPos lastBlockPos = null;
        for (BlockPos currentBlockPos : path) {
            if (lastBlockPos != null)
                WorldSpaceRenderer.renderLine(matrixStack, currentBlockPos, lastBlockPos, region);
            lastBlockPos = currentBlockPos;
        }


        matrixStack.pop();


        WorldSpaceRenderer.resetGlESPSettings();
    }


}
