package com.yichenxbohan.markedfordeath;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.yichenxbohan.markedfordeath.markedfordeath.MODID;

public class ModSounds {
    // Create DeferredRegister
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);//

    // Register sound event
    public static final RegistryObject<SoundEvent> METEOR_FALL_SOUND =
            SOUND_EVENTS.register("meteorfall",
                    () -> new SoundEvent(new ResourceLocation(MODID, "meteorfall")));

    public static final RegistryObject<SoundEvent> RGB_METEOR_FALL_SOUND =
            SOUND_EVENTS.register("rgbmeteorfall",
                    () -> new SoundEvent(new ResourceLocation(MODID, "rgbmeteorfall")));
}
