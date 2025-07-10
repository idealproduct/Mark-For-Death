package com.yichenxbohan.markedfordeath.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

public class SoulArmorItem extends ArmorItem {

    public SoulArmorItem(ArmorMaterial material, EquipmentSlot slot, Item.Properties props) {
        super(material, slot, props);
    }

    @OnlyIn(Dist.CLIENT)
    public HumanoidModel<?> getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
        return new ModelSoulArmor(Minecraft.getInstance().getEntityModels().bakeLayer(ModelSoulArmor.LAYER_LOCATION));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, net.minecraft.world.entity.player.Player player) {
        if (!level.isClientSide && this.slot == EquipmentSlot.CHEST) {
            // 只有穿著胸甲時觸發效果
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1,false, false));
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        var map = super.getAttributeModifiers(slot, stack);
        if (slot == this.getSlot()) {
            var builder = ImmutableMultimap.<net.minecraft.world.entity.ai.attributes.Attribute, AttributeModifier>builder();
            builder.putAll(map);

            // 增加最大生命 +10
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.nameUUIDFromBytes("soul_armor".getBytes()),
                    "SoulArmorHealthBoost", 10.0, AttributeModifier.Operation.ADDITION));

            return builder.build();
        }
        return map;
    }
}
