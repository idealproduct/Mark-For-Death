package com.yichenxbohan.markedfordeath.entity;

import com.yichenxbohan.markedfordeath.markedfordeath;
import com.yichenxbohan.markedfordeath.entity.TrackingEyeEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, markedfordeath.MODID);

    public static final RegistryObject<EntityType<TrackingEyeEntity>> TRACKING_EYE = ENTITIES.register(
            "tracking_eye",
            () -> EntityType.Builder.<TrackingEyeEntity>of(TrackingEyeEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f) // 大小
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("tracking_eye")
    );

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
