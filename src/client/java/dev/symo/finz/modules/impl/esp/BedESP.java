package dev.symo.finz.modules.impl.esp;

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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class BedESP extends AModule implements TickListener, WorldRenderListener {
    private final IntSetting _range = new IntSetting("Range", "Range to scan for blocks",
            50, 1, 100);

    private final ArrayList<BlockPos> beds = new ArrayList<>();

    private int _tickDelay = 0;

    public BedESP() {
        super("Bed ESP", Category.RENDER);
        addSetting(_range);
    }

    protected void onSettingsChanged() {
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

        var playerPos = mc.player.getBlockPos();
        var range = _range.getValue();

        Set<BlockPos> newBlocks = ConcurrentHashMap.newKeySet();

        IntStream.rangeClosed(playerPos.getX() - range, playerPos.getX() + range).parallel().forEach(x -> {
            IntStream.rangeClosed(playerPos.getY() - range, playerPos.getY() + range).forEach(y -> {
                IntStream.rangeClosed(playerPos.getZ() - range, playerPos.getZ() + range).forEach(z -> {
                    BlockPos pos = new BlockPos(x, y, z);
                    var block = mc.world.getBlockState(pos).getBlock();
                    if (block instanceof BedBlock) {
                        newBlocks.add(pos);
                    }
                });
            });
        });

        beds.addAll(newBlocks);

        beds.parallelStream().filter(block -> !inRange(block, playerPos, range) || !(mc.world.getBlockState(block).getBlock() instanceof BedBlock)).toList().forEach(beds::remove);
    }

    private boolean inRange(BlockPos pos, BlockPos playerPos, int range) {
        return Math.abs(pos.getX() - playerPos.getX()) <= range &&
                Math.abs(pos.getY() - playerPos.getY()) <= range &&
                Math.abs(pos.getZ() - playerPos.getZ()) <= range;
    }

    public void onWorldRender(MatrixStack matrixStack, float partialTicks, WorldRenderContext context) {
        if (mc.player == null) return;
        if (mc.world == null) return;

        var color = ColorUtil.getRainbowColor();

        WorldSpaceRenderer.renderBlocks(matrixStack, new Color(color), beds.stream());
    }
}
