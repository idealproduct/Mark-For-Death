package com.yichenxbohan.markedfordeath.event;

import com.yichenxbohan.markedfordeath.util.TargetUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "markedfordeath")
public class TargetAttackHandler {

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        if (!(event.getSource().getEntity() instanceof Player attacker)) return;
        if (!(event.getEntity() instanceof Player target)) return;

        Player marked = TargetUtils.getTarget();
        if (marked == null) return; // 沒標記任何目標時允許 PvP

        // 只允許與被標記者之間互打，其餘禁止
        if (!attacker.getUUID().equals(marked.getUUID()) && !target.getUUID().equals(marked.getUUID())) {
            attacker.displayClientMessage(Component.literal("❌ 只有與被標記者之間才允許攻擊！"), true);
            event.setCanceled(true);
        }
    }
}
