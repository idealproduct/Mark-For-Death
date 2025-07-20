package com.yichenxbohan.markedfordeath.item;

import com.yichenxbohan.markedfordeath.entity.MeteorEntity;
import com.yichenxbohan.markedfordeath.entity.MeteorRGBEntity;
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
import com.yichenxbohan.markedfordeath.client.meteor;

import java.util.Optional;
import java.util.Random;

public class MeteorStick extends Item {
    public MeteorStick(Properties props) {
        super(props);
    }

    private static final Random RANDOM = new Random();

    public static int getRandomInt(int bound) {
        return RANDOM.nextInt(bound); // 0 ~ bound-1
    }

    public static int isrgb;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        isrgb = getRandomInt(2);

//        if (!level.isClientSide()){
//            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, 9));
//            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 3));
//        }
        if (player.level instanceof ServerLevel serverLevel) {
            RandomSource random = player.getRandom();

            for (int i = 0; i < 5; i++) {
                double x = player.getX() + random.nextInt(15) - 7;
                double z = player.getZ() + random.nextInt(15) - 7;
                //double y = player.getY() + 85;
                double y = player.getY() + 200;

                if(isrgb==1){
                    player.level.playSound(null, player.blockPosition(), new SoundEvent(new ResourceLocation("markedfordeath", "meteorfall")), SoundSource.HOSTILE, 5.0F, 0.6F);
                    MeteorRGBEntity Meteorrgb = new MeteorRGBEntity(serverLevel, x, y, z, Optional.of(player));
                    serverLevel.addFreshEntity(Meteorrgb);
                }else{
                    player.level.playSound(null, player.blockPosition(), new SoundEvent(new ResourceLocation("markedfordeath", "meteorfall")), SoundSource.HOSTILE, 5.0F, 0.6F);
                    MeteorEntity Meteor = new MeteorEntity(serverLevel, x, y, z, Optional.of(player));
                    serverLevel.addFreshEntity(Meteor);
                }
            }
        }
        return InteractionResultHolder.success(stack);
    }
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
