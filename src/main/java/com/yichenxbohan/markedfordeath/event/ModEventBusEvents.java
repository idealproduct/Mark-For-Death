package com.yichenxbohan.markedfordeath.event;

import com.yichenxbohan.markedfordeath.entity.ModEntities;
import com.yichenxbohan.markedfordeath.entity.boss.TowerGuardianEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "markedfordeath", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void onEntityAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.TOWER_GUARDIAN.get(), TowerGuardianEntity.createAttributes().build());
    }
}
