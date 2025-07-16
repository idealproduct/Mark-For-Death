package com.yichenxbohan.markedfordeath.client;

import com.yichenxbohan.markedfordeath.entity.ModEntities;
import com.yichenxbohan.markedfordeath.item.ModelSoulArmor;
import com.yichenxbohan.markedfordeath.markedfordeath;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
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
    }
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TOWER_GUARDIAN.get(),
                ctx -> new HumanoidMobRenderer<>(ctx, new PlayerModel<>(ctx.bakeLayer(ModelLayers.PLAYER), false), 0.5f));
        event.registerEntityRenderer(ModEntities.METEOR.get(), MeteorRenderer::new);

    }


}
