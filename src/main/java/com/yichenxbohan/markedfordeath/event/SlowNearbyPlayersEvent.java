package com.yichenxbohan.markedfordeath.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class SlowNearbyPlayersEvent {

    public static void applySlownessNearby(ServerLevel level, ServerPlayer source, BlockPos center, double radius) {
        // 建立範圍盒子（AABB）
        AABB area = new AABB(center).inflate(radius);

        for (Player player : level.getEntitiesOfClass(Player.class, area)) {
            if (player == source) continue;  // 排除自己
            if (!player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        60,
                        1,
                        false,
                        true
                ));
            }
        }

    }
}

