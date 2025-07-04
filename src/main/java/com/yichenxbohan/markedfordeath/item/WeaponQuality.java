package com.yichenxbohan.markedfordeath.item;

import net.minecraft.ChatFormatting;

public enum WeaponQuality {
    TRASH("垃圾", 30, ChatFormatting.GRAY),
    COMMON("普通", 25, ChatFormatting.WHITE),
    RARE("稀有", 15, ChatFormatting.GREEN),
    FINE("精品", 10, ChatFormatting.AQUA),
    EPIC("罕見", 7, ChatFormatting.DARK_AQUA),
    ANCIENT("上古", 5, ChatFormatting.DARK_PURPLE),
    MYTHIC("神話", 3, ChatFormatting.LIGHT_PURPLE),
    LEGENDARY("傳奇", 2, ChatFormatting.GOLD),
    CREATION("創世", 1, ChatFormatting.YELLOW),
    PRIMAL("原初", 0.5, ChatFormatting.DARK_RED),
    CHAOTIC("渾沌", 0.2, ChatFormatting.BOLD);

    public final String displayName;
    public final double weight;
    public final ChatFormatting color;

    WeaponQuality(String displayName, double weight, ChatFormatting color) {
        this.displayName = displayName;
        this.weight = weight;
        this.color = color;
    }
}
