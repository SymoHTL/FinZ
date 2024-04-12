package dev.symo.finz.modules.impl;

import dev.symo.finz.FinZClient;
import dev.symo.finz.events.listeners.ConfigChangeListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;

public class MaterialScanner extends AModule implements ConfigChangeListener {

    ArrayList<BlockPos> blocks = new ArrayList<>();

    int _tickDelay = 10;

    public MaterialScanner() {
        super("Material Scanner", Category.RENDER);
    }


    @Override
    public boolean isEnabled() {
        return config.materialScannerEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        config.materialScannerEnabled = enabled;
    }

    public void onConfigChange() {
        blocks.clear();
    }

    public void onDisable() {
        blocks.clear();
    }

    public void onTick() {
        if (!isEnabled()) return;
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

        var range = config.materialScannerRange;

        for (int x = playerX - range; x < playerX + range; x++) {
            for (int y = playerY - range; y < playerY + range; y++) {
                for (int z = playerZ - range; z < playerZ + range; z++) {
                    var pos = new BlockPos(x, y, z);
                    var state = mc.world.getBlockState(pos);
                    var block = state.getBlock();
                    var match = config.materialScannerMaterial; // minecraft:ancient_debris
                    var id = String.valueOf(Registries.BLOCK.getId(block));
                    if (match.contains(id) && !blocks.contains(pos))
                        blocks.add(pos);
                }
            }
        }

        blocks.removeIf(pos -> Math.abs(pos.getX() - playerX) > range + range / 2 || Math.abs(pos.getY() - playerY) > range + range / 2 || Math.abs(pos.getZ() - playerZ) > range + range / 2);
    }

    public void onWorldRender(MatrixStack matrixStack, float partialTicks) {
        if (!isEnabled()) return;
        if (mc.player == null) return;
        if (mc.world == null) return;

        var hue = (float) (System.currentTimeMillis() % 4000) / 4000;
        var color = Color.HSBtoRGB(hue, 1, 1);

        WorldSpaceRenderer.renderBlocks(matrixStack, new Color(color), blocks.stream());
    }
}
