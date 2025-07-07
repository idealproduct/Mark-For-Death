package com.yichenxbohan.markedfordeath.event;

import com.yichenxbohan.markedfordeath.item.WeaponQuality;
import com.yichenxbohan.markedfordeath.util.WeaponQualityUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
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
                if (isVanillaSword(stack) && !WeaponQualityHandler.hasQualityLore(stack) && !WeaponQualityUtils.hasQuality(stack)) {
                    WeaponQuality quality = WeaponQualityUtils.randomQuality();
                    WeaponQualityUtils.setQuality(stack, quality);
                    WeaponQualityHandler.applyQualityLore(stack, quality);
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

class WeaponQualityHandler {

    // 判斷物品是否已經有品質Lore
    public static boolean hasQualityLore(ItemStack stack) {
        if (!stack.hasTag()) return false;

        CompoundTag tag = stack.getTag();
        if (!tag.contains("display")) return false;

        CompoundTag display = tag.getCompound("display");
        if (!display.contains("Lore")) return false;

        ListTag loreList = display.getList("Lore", 8); // 8 = StringTag
        return !loreList.isEmpty();
    }

    // 加上品質和增強的Lore，不回傳東西
    public static void applyQualityLore(ItemStack stack, WeaponQuality quality) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag display = tag.getCompound("display");
        if (display == null) display = new CompoundTag();

        ListTag loreList = display.getList("Lore", 8);

        // 清空原有Lore，改你自己想要的也行
        loreList.clear();

        // 新增品質名稱的Lore（帶顏色）
        loreList.add(StringTag.valueOf("\"" + quality.color + quality.displayName + "\""));

        // 加增強效果描述（示範）
        switch (quality) {
            case TRASH -> loreList.add(StringTag.valueOf("\"§7攻擊力減少 1\""));
            case COMMON -> loreList.add(StringTag.valueOf("\"§f無特殊增強\""));
            case RARE -> loreList.add(StringTag.valueOf("\"§a增加 1 點攻擊力\""));
            case FINE -> loreList.add(StringTag.valueOf("\"§b增加 2 點攻擊力\""));
            case EPIC -> loreList.add(StringTag.valueOf("\"§3攻擊速度提升\""));
            case ANCIENT -> loreList.add(StringTag.valueOf("\"§5暴擊機率提升\""));
            case MYTHIC -> loreList.add(StringTag.valueOf("\"§d吸血效果\""));
            case LEGENDARY -> loreList.add(StringTag.valueOf("\"§6火焰附加效果\""));
            case CREATION -> loreList.add(StringTag.valueOf("\"§e擊退加強\""));
            case PRIMAL -> loreList.add(StringTag.valueOf("\"§4穿透防禦\""));
            case CHAOTIC -> loreList.add(StringTag.valueOf("\"§c全方位增強\""));
        }

        tag.put("display", display);
        stack.setTag(tag);
    }
}
