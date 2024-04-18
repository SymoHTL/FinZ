package dev.symo.finz.modules.impl.esp;

import dev.symo.finz.FinZClient;
import dev.symo.finz.events.listeners.TickListener;
import dev.symo.finz.events.listeners.WorldRenderListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.ModuleManager;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.FakePlayerEntity;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.Nullables;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameMode;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.stream.Stream;

public class PlayerESP extends AModule implements TickListener, WorldRenderListener {

    private final Set<Entity> players = new HashSet<>();

    private List<PlayerListEntry> playerListEntries;

    private int _tickDelay = 10;

    public PlayerESP() {
        super("PlayerESP", Category.RENDER);
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

    @Override
    protected void onSettingsChanged() {
        players.clear();
    }



    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.world == null) return;

        if (_tickDelay == 10)
            playerListEntries = mc.player.networkHandler.getListedPlayerListEntries().stream().limit(80L).toList();
        _tickDelay = (_tickDelay + 1) % 20;

        players.clear();
        Stream<AbstractClientPlayerEntity> stream = mc.world.getPlayers()
                .parallelStream()
                .filter(e -> e != mc.player)
                .filter(e -> !e.isRemoved() && e.getHealth() > 0)
                .filter(e -> !(e instanceof FakePlayerEntity))
                .filter(e -> Math.abs(e.getY() - mc.player.getY()) <= 1e6)
                .filter(e -> !FinZClient.friendList.contains(e.getName().getString()))
                .filter(e -> playerListEntries.stream().anyMatch(p -> p.getProfile().getId().equals(e.getUuid())));

        players.addAll(stream.toList());


        while (ModuleManager.specialKey.wasPressed()) {
            if (mc.crosshairTarget == null || mc.crosshairTarget.getType() != HitResult.Type.ENTITY) return;
            var entity = ((EntityHitResult) mc.crosshairTarget).getEntity();
            if (entity instanceof PlayerEntity pe) {
                if (FinZClient.friendList.contains(pe.getName().getString()))
                    FinZClient.friendList.removeAndSave(pe.getName().getString());
                else FinZClient.friendList.addAndSave(pe.getName().getString());
            }
        }
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float partialTicks, WorldRenderContext context) {
        WorldSpaceRenderer.renderEntitiesEsp(matrices, partialTicks, players);
    }
}
