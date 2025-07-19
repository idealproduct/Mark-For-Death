package com.yichenxbohan.markedfordeath.item;

import com.yichenxbohan.markedfordeath.entity.MeteorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class MeteorStick extends Item {
    public MeteorStick(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

//        if (!level.isClientSide()){
//            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, 9));
//            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 3));
//        }
        if (player.level instanceof ServerLevel serverLevel) {
            RandomSource random = player.getRandom();

            for (int i = 0; i < 5; i++) {
                double x = player.getX() + random.nextInt(15) - 7;
                double z = player.getZ() + random.nextInt(15) - 7;
                double y = player.getY() + 170;

                player.level.playSound(null, player.blockPosition(), new SoundEvent(new ResourceLocation("markedfordeath", "meteorfall")), SoundSource.HOSTILE, 2.0F, 0.8F);
                MeteorEntity meteor = new MeteorEntity(serverLevel, x, y, z, Optional.of(player));
                serverLevel.addFreshEntity(meteor);
            }
        }
        return InteractionResultHolder.success(stack);
    }
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
