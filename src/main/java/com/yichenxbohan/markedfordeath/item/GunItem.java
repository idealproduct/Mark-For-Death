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
            bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
            level.addFreshEntity(bullet);
        }
        player.getCooldowns().addCooldown(this, 20); // 冷卻 1 秒
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
