package com.yichenxbohan.markedfordeath.util;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class TargetUtils {

    public static final String TARGET_TEAM_NAME = "marked_target";

    // 管理追殺目標 (追殺者UUID -> 被追殺者UUID)
    private static final Map<UUID, UUID> targetMap = new HashMap<>();
    private static final HashMap<UUID, UUID> targets = new HashMap<>();

    // 加入紅色隊伍並設置只近距離可見
    public static void markPlayerWithRedName(ServerPlayer player) {
        Scoreboard scoreboard = player.getScoreboard();
        PlayerTeam team = scoreboard.getPlayerTeam(TARGET_TEAM_NAME);

        if (team == null) {
            team = scoreboard.addPlayerTeam(TARGET_TEAM_NAME);
            team.setColor(ChatFormatting.RED);
            team.setPlayerPrefix(Component.literal("☠ ").withStyle(ChatFormatting.RED));
            team.setNameTagVisibility(PlayerTeam.Visibility.HIDE_FOR_OTHER_TEAMS);
        }

        scoreboard.addPlayerToTeam(player.getScoreboardName(), team);
    }

    // 移除玩家隊伍標記
    public static void clearRedName(ServerPlayer player) {
        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.removePlayerFromTeam(player.getScoreboardName());
    }

    // 設定追殺目標
    public static void setTarget(ServerPlayer hunter, ServerPlayer target) {
        if (hunter == null || target == null) return;
        targetMap.put(hunter.getUUID(), target.getUUID());
        markPlayerWithRedName(target);
    }

    // 取得追殺目標
    public static ServerPlayer getTarget(ServerPlayer hunter) {
        if (hunter == null) return null;
        UUID targetUUID = targetMap.get(hunter.getUUID());
        if (targetUUID == null) return null;
        if (hunter.getServer() == null) return null;
        return hunter.getServer().getPlayerList().getPlayer(targetUUID);
    }

    // 清除追殺目標
    public static void clearTarget(ServerPlayer hunter) {
        if (hunter == null) return;
        UUID targetUUID = targetMap.remove(hunter.getUUID());
        if (targetUUID != null && hunter.getServer() != null) {
            ServerPlayer target = hunter.getServer().getPlayerList().getPlayer(targetUUID);
            if (target != null) clearRedName(target);
        }
    }

    // 是否有設定追殺目標
    public static boolean hasTarget(ServerPlayer hunter) {
        if (hunter == null) return false;
        return targetMap.containsKey(hunter.getUUID());
    }

    // 找出X,Z座標上最高的安全Y高度
    public static int findSafeY(Level level, int x, int z) {
        int maxHeight = level.getMaxBuildHeight();
        int minHeight = level.getMinBuildHeight();

        for (int y = maxHeight; y >= minHeight; y--) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = level.getBlockState(pos);
            BlockState below = level.getBlockState(pos.below());

            if (!state.isAir() && !state.getFluidState().isSource() && !below.isAir()) {
                return y;
            }
        }

        return minHeight;
    }

    // 傳送目標玩家到執行者附近半徑radius內安全地點
    public static void teleportNearPlayer(ServerPlayer source, ServerPlayer target, int radius) {
        ServerLevel level = source.getLevel();
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            double dx = source.getX() + (random.nextDouble() * 2 - 1) * radius;
            double dz = source.getZ() + (random.nextDouble() * 2 - 1) * radius;
            int x = (int) dx;
            int z = (int) dz;

            int y = findSafeY(level, x, z);

            BlockPos pos = new BlockPos(x, y, z);
            BlockState blockBelow = level.getBlockState(pos.below());

            if (!blockBelow.isAir() && blockBelow.getFluidState().isEmpty()) {
                target.teleportTo(level, dx, y + 1, dz, target.getYRot(), target.getXRot());
                source.sendSystemMessage(Component.literal(target.getName().getString() + " 被傳送到你附近。"));
                target.sendSystemMessage(Component.literal("⚠ 你被傳送到了附近，注意安全！"));
                return;
            }
        }

        source.sendSystemMessage(Component.literal("⚠ 找不到合適的地點傳送 " + target.getName().getString()));
    }
}
