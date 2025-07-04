package com.yichenxbohan.markedfordeath.util;

import com.yichenxbohan.markedfordeath.item.WeaponQuality;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class WeaponQualityUtils {
    private static final String TAG_QUALITY = "WeaponQuality";
    private static final Random random = new Random();

    // 權重抽取品質
    public static WeaponQuality randomQuality() {
        double totalWeight = Arrays.stream(WeaponQuality.values()).mapToDouble(q -> q.weight).sum();
        double roll = random.nextDouble() * totalWeight;
        double cumulative = 0;

        for (WeaponQuality quality : WeaponQuality.values()) {
            cumulative += quality.weight;
            if (roll <= cumulative) return quality;
        }

        return WeaponQuality.TRASH; // 保底
    }

    public static boolean hasQuality(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(TAG_QUALITY);
    }


    public static void setQuality(ItemStack stack, WeaponQuality quality) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString(TAG_QUALITY, quality.name());
    }

    public static WeaponQuality getQuality(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_QUALITY)) {
            try {
                return WeaponQuality.valueOf(tag.getString(TAG_QUALITY));
            } catch (IllegalArgumentException ignored) {}
        }
        return WeaponQuality.COMMON;
    }
}
