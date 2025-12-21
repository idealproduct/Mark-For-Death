package com.yichenxbohan.markedfordeath.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yichenxbohan.markedfordeath.client.model.MeteorModel;
import com.yichenxbohan.markedfordeath.entity.MeteorEntity;
import com.yichenxbohan.markedfordeath.entity.MeteorUltraEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MeteorUltraRenderer extends EntityRenderer<MeteorUltraEntity> {
    private final EntityModel<MeteorUltraEntity> model;

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation("markedfordeath", "meteor"), "main");

    public MeteorUltraRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MeteorModel<>(context.bakeLayer(LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(MeteorUltraEntity entity) {
        return new ResourceLocation("markedfordeath", "textures/entity/meteor.png");
    }

    @Override
    public void render(MeteorUltraEntity entity, float yaw, float partialTicks,
                       PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {


        matrixStack.pushPose();
        // 這裡調整大小，3D模型會放大或縮小
        float scale = 35.0F;  // 2倍大
        matrixStack.scale(scale, scale, scale);

        model.renderToBuffer(matrixStack,
                buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity))),
                packedLight, OverlayTexture.NO_OVERLAY,
                1f, 0.6f, 1f, 1f);
        matrixStack.popPose();
        super.render(entity, yaw, partialTicks, matrixStack, buffer, packedLight);
    }
}
