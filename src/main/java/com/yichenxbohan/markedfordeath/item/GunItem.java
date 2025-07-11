package com.yichenxbohan.markedfordeath.item;

import com.yichenxbohan.markedfordeath.entity.BulletEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GunItem extends Item {
    public GunItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            BulletEntity bullet = new BulletEntity(level, player);
            bullet.setPos(player.getX(), player.getEyeY(), player.getZ());
            //bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 0.0F);
            bullet.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 3, 0.5f);
            level.addFreshEntity(bullet);
        }
        player.getCooldowns().addCooldown(this, 20); // 冷卻 1 秒
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
