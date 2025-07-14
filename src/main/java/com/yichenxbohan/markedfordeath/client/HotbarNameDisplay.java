package com.yichenxbohan.markedfordeath.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mojang.blaze3d.vertex.PoseStack;

@Mod.EventBusSubscriber(modid = "markedfordeath", value = Dist.CLIENT)
public class HotbarNameDisplay {

    private static ItemStack lastItem = ItemStack.EMPTY;

    // 每 tick 記錄玩家現在拿什麼
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                ItemStack current = mc.player.getMainHandItem();
                if (!ItemStack.isSameItemSameTags(current, lastItem)) {
                    lastItem = current;
                }
            }
        }
    }

    // 渲染 HUD（顯示物品名稱）
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (!lastItem.isEmpty() && mc.screen == null) {
            PoseStack poseStack = event.getPoseStack();
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            int x = 5; // 左邊偏移 5 像素
            int y = screenHeight - 20; // 距離下方 20 像素（不要太貼邊）

            Component nameComponent = lastItem.getItem().getName(lastItem);
            mc.font.drawShadow(poseStack, nameComponent, x, y, 0xFFFFFF);
        }
    }
}
