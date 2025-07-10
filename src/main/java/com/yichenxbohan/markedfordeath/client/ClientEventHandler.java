package com.yichenxbohan.markedfordeath.client;

import com.yichenxbohan.markedfordeath.entity.ModEntities;
import com.yichenxbohan.markedfordeath.item.ModelSoulArmor;
import com.yichenxbohan.markedfordeath.markedfordeath;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.yichenxbohan.markedfordeath.client.TrackingEyeRenderer;

@Mod.EventBusSubscriber(modid = markedfordeath.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TRACKING_EYE.get(), TrackingEyeRenderer::new);

    }
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                ModelSoulArmor.LAYER_LOCATION,
                ModelSoulArmor::createBodyLayer
        );
    }

}
