package dev.symo.finz.util;

import dev.symo.finz.FinZClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Objects;

public class FakePlayerEntity extends OtherClientPlayerEntity {
    private final ClientPlayerEntity player = FinZClient.mc.player;
    private final ClientWorld world = FinZClient.mc.world;

    public FakePlayerEntity() {
        super(Objects.requireNonNull(FinZClient.mc.world), Objects.requireNonNull(FinZClient.mc.player).getGameProfile());
        copyPositionAndRotation(player);

        copyInventory();
        copyPlayerModel(player, this);
        copyRotation();
        resetCapeMovement();

        spawn();
    }

    private void copyInventory() {
        assert player != null;
        getInventory().clone(player.getInventory());
    }

    private void copyPlayerModel(Entity from, Entity to) {
        DataTracker fromTracker = from.getDataTracker();
        DataTracker toTracker = to.getDataTracker();
        Byte playerModel = fromTracker.get(PlayerEntity.PLAYER_MODEL_PARTS);
        toTracker.set(PlayerEntity.PLAYER_MODEL_PARTS, playerModel);
    }

    private void copyRotation() {
        headYaw = player.headYaw;
        bodyYaw = player.bodyYaw;
    }

    private void resetCapeMovement() {
        capeX = getX();
        capeY = getY();
        capeZ = getZ();
    }

    private void spawn() {
        world.addEntity(this);
    }

    public void despawn() {
        discard();
    }

    public void resetPlayerPosition() {
        player.refreshPositionAndAngles(getX(), getY(), getZ(), getYaw(),
                getPitch());
    }
}
