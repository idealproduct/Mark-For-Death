package com.yichenxbohan.markedfordeath.item;

import com.yichenxbohan.markedfordeath.entity.ModEntities;
import com.yichenxbohan.markedfordeath.entity.TrackingEyeEntity;
import com.yichenxbohan.markedfordeath.util.TargetUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FlashEyesItem extends Item{
    public FlashEyesItem(Properties props) {
        super(props);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player instanceof ServerPlayer  serverplayer) {

            TargetUtils.teleportNearPlayer(serverplayer, serverplayer, 50);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        player.getCooldowns().addCooldown(this, 1200);
        return InteractionResultHolder.success(stack);
    }
}
