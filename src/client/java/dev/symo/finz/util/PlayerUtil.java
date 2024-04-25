package dev.symo.finz.util;

import dev.symo.finz.FinZClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class PlayerUtil {

    public static BlockPos getAccurateBlockPos() {
        if (FinZClient.mc.getCameraEntity() == null) return new BlockPos(0, 0, 0);
        return FinZClient.mc.getCameraEntity().getBlockPos();
    }

    public static BlockPos getBlockPosBelow() {
        BlockPos pos = getAccurateBlockPos();
        return new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
    }

    public static boolean isSameTeam(ClientPlayerEntity client, PlayerEntity other){
        var myTeam = client.getScoreboardTeam();
        var otherTeam = other.getScoreboardTeam();
        if (myTeam == null || otherTeam == null) return false;
        return myTeam.getColor().equals(otherTeam.getColor());
    }

    public static boolean isFriend(PlayerEntity other) {
        return FinZClient.friendList.contains(other.getName().getString());
    }

    public static boolean shouldAttack(ClientPlayerEntity client, PlayerEntity other) {
        return !isFriend(other) && !isSameTeam(client, other);
    }


    public static Color getScoreBoardColorOr(PlayerEntity p, Color defaultColor) {
        var team = p.getScoreboardTeam();
        if (team == null) return defaultColor;
        var teamColor = team.getColor().getColorValue();
        if (teamColor == null) return defaultColor;
        return new Color(teamColor);
    }
}
