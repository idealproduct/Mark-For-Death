package com.yichenxbohan.markedfordeath.client;

import com.yichenxbohan.markedfordeath.client.model.MeteorModel;
import com.yichenxbohan.markedfordeath.client.renderer.*;
import com.yichenxbohan.markedfordeath.entity.ModEntities;
import com.yichenxbohan.markedfordeath.item.ModelSoulArmor;
import com.yichenxbohan.markedfordeath.markedfordeath;
import net.minecraft.client.renderer.entity.EntityRenderers;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = markedfordeath.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TRACKING_EYE.get(), TrackingEyeRenderer::new);

    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // ✅ 正確做法：明確指定泛型，避免 ::new 出錯
        event.registerEntityRenderer(ModEntities.BULLET.get(), BulletRenderer::new);
    }
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                ModelSoulArmor.LAYER_LOCATION,
                ModelSoulArmor::createBodyLayer
        );
        event.registerLayerDefinition(MeteorModel.LAYER_LOCATION, MeteorModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(ModEntities.TOWER_GUARDIAN.get(), TowerGuardianRenderer::new);
        event.registerEntityRenderer(ModEntities.METEOR.get(), MeteorRenderer::new);
        event.registerEntityRenderer(ModEntities.METEORRGB.get(), MeteorRGBRenderer::new);

    }


}
