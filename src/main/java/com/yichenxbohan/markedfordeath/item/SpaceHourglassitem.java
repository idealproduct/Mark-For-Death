package com.yichenxbohan.markedfordeath.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.yichenxbohan.markedfordeath.event.SlowNearbyPlayersEvent;

public class SpaceHourglassitem extends Item {
    public SpaceHourglassitem(Properties props) {
        super(props);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player instanceof ServerPlayer serverplayer && level instanceof ServerLevel serverlevel) {
            SlowNearbyPlayersEvent.applySlownessNearby(serverlevel, serverplayer, serverplayer.blockPosition(), 20);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        player.getCooldowns().addCooldown(this, 1200);
        return InteractionResultHolder.success(stack);
    }

}
