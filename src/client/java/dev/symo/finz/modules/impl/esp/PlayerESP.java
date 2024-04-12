package dev.symo.finz.modules.impl.esp;

import dev.symo.finz.FinZClient;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.ModuleManager;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.FakePlayerEntity;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.ArrayList;
import java.util.stream.Stream;

public class PlayerESP extends AModule {
    private final ArrayList<Entity> players = new ArrayList<>();

    public PlayerESP() {
        super("PlayerESP", Category.RENDER);
    }

    @Override
    public boolean isEnabled() {
        return config.playerEsp;
    }

    @Override
    public void setEnabled(boolean enabled) {
        config.playerEsp = enabled;
    }

    @Override
    public void onTick() {
        if (!isEnabled()) return;
        if (mc.player == null) return;
        if (mc.world == null) return;

        players.clear();
        Stream<AbstractClientPlayerEntity> stream = mc.world.getPlayers()
                .parallelStream()
                .filter(e -> !e.isRemoved() && e.getHealth() > 0)
                .filter(e -> e !=  mc.player)
                .filter(e -> !(e instanceof FakePlayerEntity))
                .filter(e -> Math.abs(e.getY() -  mc.player.getY()) <= 1e6)
                .filter(e -> !FinZClient.friendList.contains(e.getName().getString()));
        players.addAll(stream.toList());


        while (ModuleManager.specialKey.wasPressed()) {
            if (mc.crosshairTarget == null || mc.crosshairTarget.getType() != HitResult.Type.ENTITY) return;
            var entity = ((EntityHitResult) mc.crosshairTarget).getEntity();
            if (entity instanceof PlayerEntity pe) {
                if (FinZClient.friendList.contains(pe.getName().getString())) FinZClient.friendList.removeAndSave(pe.getName().getString());
                 else FinZClient.friendList.addAndSave(pe.getName().getString());
            }
        }
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float partialTicks) {
        if (!isEnabled()) return;

        WorldSpaceRenderer.renderEntitiesEsp(matrices, partialTicks, players);
    }
}
