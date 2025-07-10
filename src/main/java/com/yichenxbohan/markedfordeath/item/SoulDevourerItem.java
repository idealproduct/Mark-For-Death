package com.yichenxbohan.markedfordeath.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SoulDevourerItem extends SwordItem {
    public SoulDevourerItem(Tiers tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;

            // 播放音效
            serverLevel.playSound(null, player.blockPosition(), SoundEvents.TRIDENT_THUNDER, SoundSource.PLAYERS, 2.0F, 2.0F);

            // 粒子與攻擊
            Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
            Vec3 look = player.getLookAngle().normalize();

            for (int i = 1; i <= 20; i++) {
                Vec3 pos = start.add(look.scale(i * 0.5));
                serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
            }

            // 前方造成傷害
            double range = 10.0;
            serverLevel.getEntities(player, player.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0),
                    e -> !e.is(player)).forEach(e -> {
                e.hurt(DamageSource.playerAttack(player), 15.0F);
            });
        }

        player.getCooldowns().addCooldown(this, 0);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }
}
