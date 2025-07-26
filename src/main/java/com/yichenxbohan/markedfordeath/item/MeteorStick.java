package com.yichenxbohan.markedfordeath.item;

import com.yichenxbohan.markedfordeath.entity.MeteorEntity;
import com.yichenxbohan.markedfordeath.entity.MeteorRGBEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Random;

import static com.yichenxbohan.markedfordeath.ModSounds.METEOR_FALL_SOUND;

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
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        isrgb = getRandomInt(2);

        if (player.level instanceof ServerLevel serverLevel) {
            RandomSource random = player.getRandom();

            for (int i = 0; i < 5; i++) {
                double x = player.getX() + random.nextInt(15) - 7;
                double z = player.getZ() + random.nextInt(15) - 7;
                //double y = player.getY() + 85;
                double y = player.getY() + 200;

                player.level.playSound(null, player.blockPosition(), METEOR_FALL_SOUND.get(), SoundSource.HOSTILE, 5.0F, 0.6F);

                if(isrgb==1){
                    MeteorRGBEntity Meteorrgb = new MeteorRGBEntity(serverLevel, x, y, z, Optional.of(player));
                    serverLevel.addFreshEntity(Meteorrgb);
                }else{
                    MeteorEntity Meteor = new MeteorEntity(serverLevel, x, y, z, Optional.of(player));
                    serverLevel.addFreshEntity(Meteor);
                }
            }
        }
        return InteractionResultHolder.success(stack);
    }
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }
}
