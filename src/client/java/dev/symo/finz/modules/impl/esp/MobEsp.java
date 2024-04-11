package dev.symo.finz.modules.impl.esp;

import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.ModuleManager;
import dev.symo.finz.util.EntityUtil;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.ArrayList;
import java.util.Objects;

public class MobEsp extends AModule {
    private final ArrayList<Entity> mobs = new ArrayList<>();

    public ArrayList<Entity> ignore = new ArrayList<>();


    public MobEsp() {
        super("PlayerESP", "ESP");
    }

    @Override
    public boolean IsEnabled() {
        return FinZClient.config.mobEsp;
    }

    @Override
    public void SetEnabled(boolean enabled) {
        FinZClient.config.mobEsp = enabled;
    }

    @Override
    public void onConfigChange() {

    }

    @Override
    public void onTick() {
        if (!IsEnabled()) return;
        if (mc.player == null) return;
        if (mc.world == null) return;

        mobs.clear();
        for (LivingEntity mob : mc.world.getEntitiesByClass(MobEntity.class, mc.player.getBoundingBox().expand(FinZClient.config.mobEspRange), Objects::nonNull)) {
            if (mob == mc.player) continue;
            if (!mob.isAlive()) continue;
            if (ignore.contains(mob)) continue;
            mobs.add(mob);
        }


        while (ModuleManager.specialKey.wasPressed()) {
            if (mc.crosshairTarget == null || mc.crosshairTarget.getType() != HitResult.Type.ENTITY) return;
            var entity = ((EntityHitResult) mc.crosshairTarget).getEntity();
            if (entity instanceof MobEntity le) {
                if (ignore.contains(le)) ignore.remove(le);
                else ignore.add(le);
            }
        }
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float partialTicks) {
        if (!IsEnabled()) return;

        WorldSpaceRenderer.renderEntitiesEsp(matrices, partialTicks, mobs);
    }
}
