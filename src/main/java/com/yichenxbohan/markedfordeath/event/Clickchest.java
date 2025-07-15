package com.yichenxbohan.markedfordeath.event;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "markedfordeath", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Clickchest {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        Player player = event.getEntity();
        BlockPos pos = event.getPos();

        // 伺服器端才處理
        if (!(level instanceof ServerLevel serverLevel)) return;

        if (!(level.getBlockEntity(pos) instanceof ChestBlockEntity chest)) return;
        if (!chest.hasCustomName() || !chest.getCustomName().getString().equals("關卡獎勵箱")) return;

        // 怪物範圍偵測
        AABB range = new AABB(pos.offset(-10, -5, -10), pos.offset(11, 6, 11));
        List<Monster> mobs = serverLevel.getEntitiesOfClass(Monster.class, range);

        if (!mobs.isEmpty()) {
            // 把 client-side 的 GUI 關掉（靠 cancel 是不夠的）
            event.setUseBlock(net.minecraftforge.eventbus.api.Event.Result.DENY);
            event.setCanceled(true);

            player.sendSystemMessage(Component.literal("你還沒清光怪物！"));
        }
    }
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();

        if (level.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
            if (chest.hasCustomName() && chest.getCustomName().getString().equals("關卡獎勵箱")) {
                if (!event.getPlayer().isCreative()) {
                    event.setCanceled(true);
                    event.getPlayer().sendSystemMessage(Component.literal("別想偷拆關卡箱！"));
                }
            }
        }
    }

}
