package dev.symo.finz.modules.impl.esp;

import dev.symo.finz.events.listeners.ConfigChangeListener;
import dev.symo.finz.events.listeners.TickListener;
import dev.symo.finz.events.listeners.WorldRenderListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.IntSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.ColorUtil;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.BedBlock;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;

public class BedESP extends AModule implements ConfigChangeListener, TickListener, WorldRenderListener {
    private final IntSetting _range = new IntSetting("Range", "Range to scan for blocks",
            50, 1, 100);

    private final ArrayList<BlockPos> beds = new ArrayList<>();

    private int _tickDelay = 0;

    public BedESP() {
        super("Bed ESP", Category.RENDER);
        addSetting(_range);
    }

    public void onConfigChange() {
        beds.clear();
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
        beds.clear();
    }

    public void onTick() {
        if (mc.player == null) return;
        if (mc.world == null) return;

        if (_tickDelay > 0) {
            _tickDelay--;
            return;
        }
        _tickDelay = 40;

        // look for beds in range around player

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
                    if (block instanceof BedBlock && !beds.contains(pos))
                        beds.add(pos);
                }
            }
        }

        beds.removeIf(pos -> !(mc.world.getBlockState(pos).getBlock() instanceof BedBlock));

        beds.removeIf(pos -> Math.abs(pos.getX() - playerX) > range + range / 2 || Math.abs(pos.getY() - playerY) > range + range / 2 || Math.abs(pos.getZ() - playerZ) > range + range / 2);
    }

    public void onWorldRender(MatrixStack matrixStack, float partialTicks, WorldRenderContext context) {
        if (mc.player == null) return;
        if (mc.world == null) return;

        var color = ColorUtil.getRainbowColor();

        WorldSpaceRenderer.renderBlocks(matrixStack, new Color(color), beds.stream());
    }
}
