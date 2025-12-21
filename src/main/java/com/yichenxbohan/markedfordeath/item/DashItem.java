package com.yichenxbohan.markedfordeath.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class DashItem extends Item {

    public DashItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 2));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 3));

        }
        if (player instanceof ServerPlayer serverplayer){
            GameType gamemode = serverplayer.gameMode.getGameModeForPlayer();
            if(gamemode != GameType.CREATIVE) {
                player.getCooldowns().addCooldown(this, 1200);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }


    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // 可改成NONE
    }
}