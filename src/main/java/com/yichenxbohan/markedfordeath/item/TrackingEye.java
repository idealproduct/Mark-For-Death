package com.yichenxbohan.markedfordeath.item;

import com.yichenxbohan.markedfordeath.entity.TrackingEyeEntity;
import com.yichenxbohan.markedfordeath.entity.ModEntities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TrackingEye extends Item {
    public TrackingEye(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            TrackingEyeEntity eye = new TrackingEyeEntity(ModEntities.TRACKING_EYE.get(), level);
            eye.setPos(player.getX(), player.getY() + 1.5, player.getZ());
            eye.setOwner((ServerPlayer) player);
            level.addFreshEntity(eye);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResultHolder.success(stack);
    }
}
