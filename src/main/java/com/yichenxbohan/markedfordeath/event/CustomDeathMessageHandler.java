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
        if (!(event.getEntity() instanceof Player player)) return;

        if (player.getPersistentData().getBoolean("MarkedByMeteor")) {
            if (player.level instanceof ServerLevel serverLevel && !serverLevel.isClientSide) {
                Component customMessage = Component.literal(player.getName().getString() + " 被隕石輾成了渣");
                for (ServerPlayer serverPlayer : serverLevel.players()) {
                    serverPlayer.sendSystemMessage(customMessage);
                }
            }

        }
    }
}
