package com.yichenxbohan.markedfordeath.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class SmokeBombItem extends Item {

    public SmokeBombItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);


        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            Random random = new Random();

            serverLevel.playSound(null, player.blockPosition(), SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 1.0F, 1.0F);

            int radius = 10;
            int particleCount = 1000;
            double px = player.getX();
            double py = player.getY() + 1.0;
            double pz = player.getZ();

            for (int i = 0; i < particleCount; i++) {
                double dx = px + (random.nextDouble() - 0.5) * radius * 2;
                double dy = py + random.nextDouble() * 4.0;
                double dz = pz + (random.nextDouble() - 0.5) * radius * 2;

                serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        dx, dy, dz,
                        1,
                        0.25, 0.25, 0.25,
                        0.03);
            }

            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 60, 0, false, false, true));

        }

        if (!player.isCreative()) {
            stack.shrink(1);
        }

        return InteractionResultHolder.success(stack);
    }
}
