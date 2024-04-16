package dev.symo.finz.modules.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.symo.finz.events.listeners.TickListener;
import dev.symo.finz.events.listeners.WorldRenderListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.IntSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.PlayerUtil;
import dev.symo.finz.util.RegionPos;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class PathTracer extends AModule implements TickListener, WorldRenderListener {

    private final IntSetting _pathTracerLength = new IntSetting("Path Tracer Length", "Length of the path tracer",
            50, 1, 100);


    public List<BlockPos> path = new ArrayList<>();

    public PathTracer() {
        super("PathTracer", Category.RENDER);
        addSetting(_pathTracerLength);
    }

    @Override
    public void onEnable() {
        EVENTS.add(TickListener.class, this);
        EVENTS.add(WorldRenderListener.class, this);
    }

    @Override
    public void onDisable() {
        EVENTS.remove(TickListener.class, this);
        EVENTS.remove(WorldRenderListener.class, this);
    }


    public void onTick() {
        if (mc.player == null) return;
        if (mc.world == null) return;

        BlockPos blockPos = PlayerUtil.getBlockPosBelow();
        BlockState blockState = mc.world.getBlockState(blockPos);

        if (blockState.isSolidBlock(mc.world, blockPos)) {
            if (!path.contains(blockPos)) {
                path.add(blockPos);
            }
        }

        var length = _pathTracerLength.getValue();

        if (length < path.size())
            if (path.size() - length > 0)
                path.subList(0, path.size() - length).clear();

    }

    public void onWorldRender(MatrixStack matrixStack, float partialTicks, WorldRenderContext context) {
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
