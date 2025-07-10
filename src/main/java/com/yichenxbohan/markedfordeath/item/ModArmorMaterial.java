package com.yichenxbohan.markedfordeath.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;

public class ModArmorMaterial {
    public static final ArmorMaterial SOUL = new ArmorMaterial() {
        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return 30 * 25;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            return switch (slot) {
                case HEAD -> 3;
                case CHEST -> 8;
                case LEGS -> 6;
                case FEET -> 3;
                default -> 0;
            };
        }

        @Override public int getEnchantmentValue() { return 25; }

        @Override public SoundEvent getEquipSound() { return SoundEvents.ARMOR_EQUIP_NETHERITE; }

        @Override public Ingredient getRepairIngredient() { return Ingredient.of(Items.NETHER_STAR); }

        @Override public String getName() { return "markedfordeath:soul"; }

        @Override public float getToughness() { return 3f; }

        @Override public float getKnockbackResistance() { return 0.1f; }
    };
}
