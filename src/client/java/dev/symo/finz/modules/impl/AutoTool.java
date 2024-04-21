package dev.symo.finz.modules.impl;

import dev.symo.finz.FinZClient;
import dev.symo.finz.events.listeners.BreakingListener;
import dev.symo.finz.events.listeners.TickListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.settings.BoolSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.FakePlayerEntity;
import dev.symo.finz.util.ToolUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class AutoTool extends AModule implements TickListener, BreakingListener {

    private final BoolSetting switchBack = new BoolSetting("Switch back",
            "After using a tool, automatically switches back to the previously selected tool.",
            false);

    private int prevSlot;

    public AutoTool() {
        super("AutoTool", Category.COMBAT);
        addSetting(switchBack);
    }

    @Override
    public void onEnable() {
        EVENTS.add(TickListener.class, this);
        EVENTS.add(BreakingListener.class, this);
        prevSlot = -1;
    }

    @Override
    public void onDisable() {
        EVENTS.remove(TickListener.class, this);
        EVENTS.remove(BreakingListener.class, this);
    }

    @Override
    public void onTick() {
        if (mc.interactionManager == null || mc.player == null) return;

        if (prevSlot == -1)
            prevSlot = mc.player.getInventory().selectedSlot;

        if (mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.ENTITY)
            if (isAttacking(((EntityHitResult) mc.crosshairTarget).getEntity())) return;

        if (prevSlot == -1 || mc.interactionManager.isBreakingBlock()) return;

        if (switchBack.getValue())
            mc.player.getInventory().selectedSlot = prevSlot;

        prevSlot = -1;
    }

    @Override
    public void onBlockBreakingProgress(BreakingProgressEvent event) {
        if (mc.world == null || mc.player == null) return;
        BlockPos pos = event.getBlockPos();

        if (mc.world.getBlockState(pos).getOutlineShape(mc.world, pos).isEmpty()) return;

        var bestSlot = ToolUtil.getBestToolSlot(pos);
        if (bestSlot == -1) return;
        mc.player.getInventory().selectedSlot = bestSlot;
    }

    private boolean isAttacking(Entity entity) {
        if(entity instanceof LivingEntity le) {
            if (le.getHealth() > 0 &&
                    le != mc.player &&
                    !(le instanceof FakePlayerEntity) &&
                    !FinZClient.friendList.contains(le.getName().getString())){
                var bestSlot = ToolUtil.getBestWeaponSlot(le);
                if (bestSlot != -1) {
                    mc.player.getInventory().selectedSlot = bestSlot;
                    return true;
                }
            }
        }
        return false;
    }
}
