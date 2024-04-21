package dev.symo.finz.modules.impl.esp;

import dev.symo.finz.FinZClient;
import dev.symo.finz.events.listeners.TickListener;
import dev.symo.finz.events.listeners.WorldRenderListener;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.modules.ModuleManager;
import dev.symo.finz.modules.settings.BoolSetting;
import dev.symo.finz.util.Category;
import dev.symo.finz.util.ColorUtil;
import dev.symo.finz.util.FakePlayerEntity;
import dev.symo.finz.util.WorldSpaceRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class PlayerESP extends AModule implements TickListener, WorldRenderListener {

    private final BoolSetting filterNps = new BoolSetting("Filter Npcs", "If enabled, only \"real\" players will be shown", true);

    private final BoolSetting showTracers = new BoolSetting("Tracers", "If enabled, tracers will be shown", false);
    private final Set<Entity> players = new HashSet<>();

    private List<PlayerListEntry> playerListEntries;

    private int _tickDelay = 10;

    public PlayerESP() {
        super("PlayerESP", Category.RENDER);
        addSetting(filterNps);
        addSetting(showTracers);
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

        if ((_tickDelay == 10 || playerListEntries == null) && filterNps.getValue())
            playerListEntries = mc.player.networkHandler.getListedPlayerListEntries().stream().limit(80L).toList();
        _tickDelay = (_tickDelay + 1) % 20;

        players.clear();
        var sb = mc.world.getScoreboard();
        var clientTeam = sb.getScoreHolderTeam(mc.player.getName().getString());
        Stream<AbstractClientPlayerEntity> stream = mc.world.getPlayers()
                .parallelStream()
                .filter(e -> e != mc.player)
                .filter(e -> !e.isRemoved() && e.getHealth() > 0)
                .filter(e -> !(e instanceof FakePlayerEntity))
                .filter(e -> Math.abs(e.getY() - mc.player.getY()) <= 1e6)
                .filter(e -> !FinZClient.friendList.contains(e.getName().getString()))
                .filter(e -> {
                    var team = sb.getScoreHolderTeam(e.getName().getString());
                    if (team == null || clientTeam == null) return true;
                    return !clientTeam.getName().equals(team.getName());
                });
        if (filterNps.getValue())
            stream = stream.filter(e -> playerListEntries.stream().anyMatch(p -> p.getProfile().getId().equals(e.getUuid())));

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
        assert mc.world != null;
        var sb = mc.world.getScoreboard();


        WorldSpaceRenderer.renderEntitiesEsp(matrices, partialTicks, players, entity -> {
            var color = Color.BLACK;
            if (FinZClient.friendList.contains(entity.getName().getString())) color = Color.GREEN;
            else if (entity instanceof PlayerEntity pe) {
                var team = sb.getScoreHolderTeam(pe.getName().getString());
                if (team != null) {
                    var teamColor = team.getColor().getColorValue();
                    if (teamColor != null) color = new Color(teamColor);
                }
            }
            return color;
        }, entity -> {
            if (entity instanceof LivingEntity livingEntity) {
                var hpPercent = livingEntity.getHealth() / livingEntity.getMaxHealth();
                return ColorUtil.percentageToColor(hpPercent * 100);
            }

            return Color.WHITE;
        }, true);

        if (showTracers.getValue()) {
            WorldSpaceRenderer.renderTracers(matrices, partialTicks, players);
        }
    }
}
