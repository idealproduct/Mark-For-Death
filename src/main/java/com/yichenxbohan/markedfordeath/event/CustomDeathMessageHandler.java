package com.yichenxbohan.markedfordeath.event;

import com.yichenxbohan.markedfordeath.entity.MeteorEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "markedfordeath")
public class CustomDeathMessageHandler {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        DamageSource source = event.getSource();

        // 判斷是不是原版隕石傷害(你自己定義的條件)
        if ("outOfWorld".equals(source.getMsgId()) && player.getPersistentData().getBoolean("MarkedByMeteor")) {
            if (player.level instanceof ServerLevel serverLevel && !serverLevel.isClientSide) {
                // 取消原死亡事件，避免出現原死訊
                event.setCanceled(true);

                // 清除標記，避免重複執行
                player.getPersistentData().remove("MarkedByMeteor");

                // 用自訂傷害殺死玩家，觸發自訂死訊
                player.hurt(new DamageSource("killed_by_meteor").bypassArmor(), Float.MAX_VALUE);

                // 你可以額外發送訊息
                Component customMessage = Component.literal(player.getName().getString() + " 被隕石輾成了渣");
                for (ServerPlayer sp : serverLevel.players()) {
                    sp.sendSystemMessage(customMessage);
                }
            }
        }
        if ("fireball".equals(source.getMsgId()) && player.getPersistentData().getBoolean("MarkedByMeteor")) {
            if (player.level instanceof ServerLevel serverLevel && !serverLevel.isClientSide) {
                // 取消原死亡事件，避免出現原死訊
                event.setCanceled(true);

                // 清除標記，避免重複執行
                player.getPersistentData().remove("MarkedByMeteor");

                // 用自訂傷害殺死玩家，觸發自訂死訊
                player.hurt(new DamageSource("killed_by_meteor").bypassArmor(), Float.MAX_VALUE);

                // 你可以額外發送訊息
                Component customMessage = Component.literal(player.getName().getString() + " 被隕石輾成了渣");
                for (ServerPlayer sp : serverLevel.players()) {
                    sp.sendSystemMessage(customMessage);
                }
            }
        }
    }
}

