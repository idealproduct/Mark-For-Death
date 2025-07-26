package com.yichenxbohan.markedfordeath.client.renderer;

import com.yichenxbohan.markedfordeath.entity.boss.TowerGuardianEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TowerGuardianRenderer extends HumanoidMobRenderer<TowerGuardianEntity, HumanoidModel<TowerGuardianEntity>> {

    public TowerGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(TowerGuardianEntity entity) {
        return new ResourceLocation("markedfordeath", "textures/entity/tower_guardian.png");
    }
}
