package com.yichenxbohan.markedfordeath.item;

import com.yichenxbohan.markedfordeath.markedfordeath;
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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
