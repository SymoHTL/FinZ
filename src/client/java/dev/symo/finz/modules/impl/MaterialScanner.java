package dev.symo.finz.modules.impl;

import dev.symo.finz.events.listeners.ConfigChangeListener;
import dev.symo.finz.events.listeners.TickListener;
import dev.symo.finz.events.listeners.WorldRenderListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.IntSetting;
import dev.symo.finz.modules.settings.StringSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;

public class MaterialScanner extends AModule implements ConfigChangeListener, TickListener, WorldRenderListener {
    private final IntSetting _range = new IntSetting("Range", "Range to scan for blocks",
            50, 1, 100);

    private final StringSetting _material = new StringSetting("Material", "Material to scan for",
            "minecraft:ancient_debris");

    private final ArrayList<BlockPos> blocks = new ArrayList<>();

    private int _tickDelay = 0;

    public MaterialScanner() {
        super("Material Scanner", Category.RENDER);
        addSetting(_range);
        addSetting(_material);
    }

    public void onConfigChange() {
        blocks.clear();
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
        blocks.clear();
    }

    public void onTick() {
        if (mc.player == null) return;
        if (mc.world == null) return;

        if (_tickDelay > 0) {
            _tickDelay--;
            return;
        }
        _tickDelay = 10;

        // look for blocks in range around player

        var playerPos = mc.player.getBlockPos();
        var playerX = playerPos.getX();
        var playerY = playerPos.getY();
        var playerZ = playerPos.getZ();

        var range = _range.getValue();

        for (int x = playerX - range; x < playerX + range; x++) {
            for (int y = playerY - range; y < playerY + range; y++) {
                for (int z = playerZ - range; z < playerZ + range; z++) {
                    var pos = new BlockPos(x, y, z);
                    var state = mc.world.getBlockState(pos);
                    var block = state.getBlock();
                    // _material.getValue() -> minecraft:ancient_debris
                    var id = String.valueOf(Registries.BLOCK.getId(block));
                    if (_material.getValue().contains(id) && !blocks.contains(pos))
                        blocks.add(pos);
                }
            }
        }

        blocks.removeIf(pos -> Math.abs(pos.getX() - playerX) > range + range / 2 || Math.abs(pos.getY() - playerY) > range + range / 2 || Math.abs(pos.getZ() - playerZ) > range + range / 2);
    }

    public void onWorldRender(MatrixStack matrixStack, float partialTicks) {
        if (mc.player == null) return;
        if (mc.world == null) return;

        var hue = (float) (System.currentTimeMillis() % 4000) / 4000;
        var color = Color.HSBtoRGB(hue, 1, 1);

        WorldSpaceRenderer.renderBlocks(matrixStack, new Color(color), blocks.stream());
    }
}
