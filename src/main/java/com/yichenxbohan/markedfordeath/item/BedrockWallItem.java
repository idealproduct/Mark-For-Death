package com.yichenxbohan.markedfordeath.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.Direction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BedrockWallItem extends Item {

    public BedrockWallItem(Properties properties) {
        super(properties);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private final List<WallData> wallList = new ArrayList<>();

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            BlockPos origin = player.blockPosition().relative(player.getDirection());
            List<Pair<BlockPos,Block>> placedBlocks = new ArrayList<>();

            for (int y = -5; y < 5; y++) {
                for (int x = -5; x <= 5; x++) {
                    BlockPos pos = origin.relative(player.getDirection().getClockWise(), x).above(y+2);
                    placedBlocks.add(Pair.of(pos, level.getBlockState(pos).getBlock()));
                    level.setBlock(pos, Blocks.BARRIER.defaultBlockState(), 3);
                }
            }
            player.getCooldowns().addCooldown(this, 20*60);
            wallList.add(new WallData(level, placedBlocks, level.getGameTime() + 60)); // 5秒
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Iterator<WallData> iterator = wallList.iterator();
        while (iterator.hasNext()) {
            WallData wall = iterator.next();
            if (wall.level.getGameTime() >= wall.expireTime) {
                for (Pair<BlockPos,Block> i : wall.blocks ) {

                    wall.level.setBlock(i.getFirst(), i.getSecond().defaultBlockState(), 3);
                }
                iterator.remove();
            }
        }
    }

    // 牆資料類
    private static class WallData {
        public final Level level;
        public final List<Pair<BlockPos,Block>> blocks;
        public final long expireTime;

        public WallData(Level level, List<Pair<BlockPos,Block>> blocks, long expireTime) {
            this.level = level;
            this.blocks = blocks;
            this.expireTime = expireTime;
        }
    }
}
