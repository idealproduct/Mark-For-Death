package com.yichenxbohan.markedfordeath.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.yichenxbohan.markedfordeath.entity.MeteorEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MeteorRenderer extends EntityRenderer<MeteorEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/fireball.png");

    public MeteorRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(MeteorEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(MeteorEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        float age = entity.tickCount + partialTicks;
        poseStack.mulPose(Vector3f.YP.rotationDegrees(age * 20));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(age * 20));

        float scale = 0.5f;
        poseStack.scale(scale, scale, scale);

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));

        // 你這邊自己畫一個簡單方形或用頂點自行擴充

        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}
