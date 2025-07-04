package com.yichenxbohan.markedfordeath.event;

import com.yichenxbohan.markedfordeath.item.WeaponQuality;
import com.yichenxbohan.markedfordeath.util.WeaponQualityUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WeaponQualityApplier {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (event.player instanceof ServerPlayer player) {
            for (ItemStack stack : player.getInventory().items) {
                if (isVanillaSword(stack) && !WeaponQualityUtils.hasQuality(stack)) {
                    WeaponQuality quality = WeaponQualityUtils.randomQuality();
                    WeaponQualityUtils.setQuality(stack, quality);
                }
            }
        }
    }

    private static boolean isVanillaSword(ItemStack stack) {
        return stack.getItem() == Items.WOODEN_SWORD ||
                stack.getItem() == Items.STONE_SWORD ||
                stack.getItem() == Items.IRON_SWORD ||
                stack.getItem() == Items.GOLDEN_SWORD ||
                stack.getItem() == Items.DIAMOND_SWORD ||
                stack.getItem() == Items.NETHERITE_SWORD;
    }
}
