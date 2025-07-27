package com.yichenxbohan.markedfordeath.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class SlowNearbyPlayersEvent {

    public static void applySlownessNearby(ServerLevel level, ServerPlayer source, BlockPos center, double radius) {
        // 建立範圍盒子（AABB）
        AABB area = new AABB(center).inflate(radius);

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity == source) continue;  // 排除自己

            entity.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    100,
                    6,
                    false,
                    false
            ));

        }

    }
}

