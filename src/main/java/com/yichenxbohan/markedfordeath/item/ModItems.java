package com.yichenxbohan.markedfordeath.item;

import com.yichenxbohan.markedfordeath.markedfordeath;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, markedfordeath.MODID);

    public static final RegistryObject<Item> BLADE_OF_TRUST = ITEMS.register("blade_of_trust",
            () -> new BladeOfTrustItem(Tiers.IRON, 4, -2.4F, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> SMOKE_BOMB = ITEMS.register("smoke_bomb",
            () -> new SmokeBombItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> TRACKING_EYE = ITEMS.register("tracking_eye",
            () -> new TrackingEye(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> SOUL_DEVOURER = ITEMS.register("soul_devourer",
            () -> new SoulDevourerItem(Tiers.IRON, 4, -2.4F, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> SOUL_HELMET =
            ITEMS.register("soul_helmet", () -> new SoulArmorItem(ModArmorMaterial.SOUL, EquipmentSlot.HEAD, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT).durability(9999)));

    public static final RegistryObject<Item> SOUL_CHESTPLATE =
            ITEMS.register("soul_chestplate", () -> new SoulArmorItem(ModArmorMaterial.SOUL, EquipmentSlot.CHEST, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT).durability(9999)));

    public static final RegistryObject<Item> SOUL_LEGGINGS =
            ITEMS.register("soul_leggings", () -> new SoulArmorItem(ModArmorMaterial.SOUL, EquipmentSlot.LEGS, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT).durability(9999)));

    public static final RegistryObject<Item> SOUL_BOOTS =
            ITEMS.register("soul_boots", () -> new SoulArmorItem(ModArmorMaterial.SOUL, EquipmentSlot.FEET, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT).durability(9999)));

    public static final RegistryObject<Item> GUN = ITEMS.register("gun",
            () -> new GunItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> AMMO = ITEMS.register("ammo",
            () -> new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> METEOR = ITEMS.register("meteor",
            () -> new Item(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> METEOR_STICK = ITEMS.register("meteor_stick",
            () -> new MeteorStick(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> DASH = ITEMS.register("dash",
            () -> new DashItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> FLASH_EYE = ITEMS.register("flash_eye",
            () -> new FlashEyesItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_COMBAT)));

        public static final RegistryObject<Item> BEDROCK_WALL = ITEMS.register("bedrock_wall",
            () -> new BedrockWallItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> SPACE_HOURGLASS = ITEMS.register("space_hourglass",
            () -> new SpaceHourglassitem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_MISC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
