package com.yichenxbohan.markedfordeath.item;

import com.yichenxbohan.markedfordeath.entity.BulletEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GunItem extends Item {
    public GunItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;
            BulletEntity bullet = new BulletEntity(level, player);
            bullet.setPos(player.getX(), player.getEyeY(), player.getZ());
            //bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 0.0F);
            bullet.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 3, 0.5f);
            level.addFreshEntity(bullet);
            Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
            Vec3 look = player.getLookAngle().normalize();
            for (double i = 1; i <= 20; i+=0.25) {
                Vec3 pos = start.add(look.scale(i * 0.5));
                serverLevel.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
            }

            double range = 100.0;
            serverLevel.getEntities(player, player.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0),
                    e -> !e.is(player) && e instanceof LivingEntity).forEach(e -> {
                // 💥 無視無敵幀：重設 invulnerableTime
                ((LivingEntity) e).invulnerableTime = 0;
            });
            serverLevel.playSound(null, player.blockPosition(), SoundEvents.WARDEN_DEATH, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        player.getCooldowns().addCooldown(this, 10); // 冷卻 1 秒
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
