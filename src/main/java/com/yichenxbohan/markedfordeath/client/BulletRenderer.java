package com.yichenxbohan.markedfordeath.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yichenxbohan.markedfordeath.entity.BulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BulletRenderer extends EntityRenderer<BulletEntity> {
    private final ItemRenderer itemRenderer;

    public BulletRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(BulletEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F); // 子彈變小一點
        this.itemRenderer.renderStatic(
                new ItemStack(Items.IRON_NUGGET),
                net.minecraft.client.renderer.block.model.ItemTransforms.TransformType.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                0 // 隨便一個 seed（entity.getId() 也可）
        );
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BulletEntity entity) {
        return null; // 使用物品貼圖，不用實體專屬貼圖
    }
}