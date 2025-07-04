package com.yichenxbohan.markedfordeath.event;

import com.yichenxbohan.markedfordeath.item.WeaponQuality;
import com.yichenxbohan.markedfordeath.util.WeaponQualityUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipEventHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (!WeaponQualityUtils.hasQuality(stack)) return;

        WeaponQuality quality = WeaponQualityUtils.getQuality(stack);
        String baseName = stack.getHoverName().getString();

        Component prefix;

        if (quality.ordinal() >= WeaponQuality.LEGENDARY.ordinal()) {
            // 閃爍顏色陣列（你可以自訂）
            int[] sparkleColors = new int[]{
                    0xFFFFD700, // 金色
                    0xFFFFFF00, // 黃色
                    0xFFFFA500  // 橘色
            };
            long time = System.currentTimeMillis();
            int colorIndex = (int)((time / 300) % sparkleColors.length);
            int color = sparkleColors[colorIndex];

            prefix = Component.literal("[" + quality.displayName + "] ")
                    .withStyle(Style.EMPTY.withColor(color));
        } else {
            // 靜態顏色
            prefix = Component.literal("[" + quality.displayName + "] ")
                    .withStyle(Style.EMPTY.withColor(quality.color));
        }

        Component newName = Component.empty()
                .append(prefix)
                .append(Component.literal(baseName));

        event.getToolTip().set(0, newName);
    }
}
