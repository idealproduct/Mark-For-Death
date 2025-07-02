package com.yichenxbohan.markedfordeath.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class BladeOfTrustItem extends SwordItem {
    public BladeOfTrustItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof Player player) || !(target instanceof Player targetPlayer)) {
            return super.hurtEnemy(stack, target, attacker);
        }

        // TODO: 判斷 target 是否為玩家目標 (能力系統尚未完成，暫時預設 false)
        boolean isCorrectTarget = false;

        if (isCorrectTarget) {
            target.hurt(DamageSource.playerAttack(player), 6.0F); // 額外傷害
            player.sendSystemMessage(Component.literal("✔ 成功擊中你的目標！").withStyle(ChatFormatting.GREEN));
        } else {
            player.hurt(DamageSource.MAGIC, 6.0F); // 自傷懲罰
            player.sendSystemMessage(Component.literal("✘ 你殺錯人了！").withStyle(ChatFormatting.RED));
        }

        return super.hurtEnemy(stack, target, attacker);
    }
}
