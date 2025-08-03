package com.yichenxbohan.markedfordeath.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class SoulDevourerItem extends SwordItem {
    public SoulDevourerItem(Tiers tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }
    @Override
    public MutableComponent getName(ItemStack stack) {
        // 色彩樣本
        ChatFormatting[] styles = {
                ChatFormatting.DARK_RED,
                ChatFormatting.RED,
                ChatFormatting.GOLD,
                ChatFormatting.YELLOW,
                ChatFormatting.DARK_GREEN,
                ChatFormatting.GREEN,
                ChatFormatting.AQUA,
                ChatFormatting.BLUE,
                ChatFormatting.LIGHT_PURPLE,
                ChatFormatting.DARK_PURPLE,
                ChatFormatting.GRAY,
                ChatFormatting.WHITE
        };

        // 控制頻率的重點：tick 數除以速度
        long ticks = System.currentTimeMillis() / 200; // ← 每200ms切一次（頻率=5次/秒）
        ChatFormatting color = styles[(int)(ticks % styles.length)];

        // 加個炫炮樣式（可自調）
        boolean bold = true;
        boolean italic = false;
        boolean obf = false;

        MutableComponent name = Component.translatable(this.getDescriptionId());
        name.withStyle(color);
        if (bold) name.withStyle(ChatFormatting.BOLD);
        if (italic) name.withStyle(ChatFormatting.ITALIC);
        if (obf) name.withStyle(ChatFormatting.OBFUSCATED);

        return name;
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;

            // 播放音效
            serverLevel.playSound(null, player.blockPosition(), SoundEvents.TRIDENT_THUNDER, SoundSource.PLAYERS, 2.0F, 2.0F);

            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 5, 9));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 2));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1));
            // 粒子與攻擊
            Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
            Vec3 look = player.getLookAngle().normalize();

            for (int i = 1; i <= 20; i++) {
                Vec3 pos = start.add(look.scale(i * 0.5));
                serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
            }

            // 前方造成傷害，並重設無敵幀
            double range = 10.0;
            serverLevel.getEntities(player, player.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0),
                    e -> !e.is(player) && e instanceof LivingEntity).forEach(e -> {
                e.hurt(DamageSource.MAGIC, 15.0F);

                // 💥 無視無敵幀：重設 invulnerableTime
                ((LivingEntity) e).invulnerableTime = 0;
            });
        }

        player.getCooldowns().addCooldown(this, 10); // 0.5s cooldown

        return InteractionResultHolder.success(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }
}
