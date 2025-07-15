package com.yichenxbohan.markedfordeath.event;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class Clickchest {
    @SubscribeEvent
    public static void onChestOpen(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();

        if (level.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
            // 檢查是不是你的特殊箱子
            if (chest.hasCustomName() && chest.getCustomName().getString().equals("關卡獎勵箱")) {

                // 檢查周圍是否有怪物
                AABB range = new AABB(
                        pos.getX() - 10, pos.getY(), pos.getZ() - 10,
                        pos.getX() + 11, pos.getY() + 1, pos.getZ() + 11
                );
                List<Monster> mobs = level.getEntitiesOfClass(Monster.class, range);

                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.5;
                double z = pos.getZ() + 0.5;

                if (!mobs.isEmpty()) {
                    // 有怪物，取消開箱
                    if (level instanceof ServerLevel serverLevel) {
                        level.getNearestPlayer(x,y,z, 20,true).sendSystemMessage(Component.literal("你還沒清光怪物！"));
                    }
                    event.setCanceled(true);
                }


            }
        }
    }

}
