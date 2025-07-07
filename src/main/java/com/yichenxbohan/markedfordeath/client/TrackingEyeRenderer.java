package com.yichenxbohan.markedfordeath.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class TrackingEyeRenderer extends EntityRenderer<Entity> {

    private final ItemRenderer itemRenderer;
    private final ItemStack itemStack;

    public TrackingEyeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
        // 請換成你自己註冊的TrackingEye物品
        this.itemStack = new ItemStack(com.yichenxbohan.markedfordeath.item.ModItems.TRACKING_EYE.get());
    }

    @Override
    public void render(Entity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // 飄浮動畫：上下微動
        float time = entity.tickCount + partialTicks;
        float floatOffset = (float) Math.sin(time * 0.1f) * 0.1f;
        poseStack.translate(0.0D, floatOffset, 0.0D);

        // 旋轉動畫
        poseStack.mulPose(com.mojang.math.Vector3f.YP.rotationDegrees(time * 4.0f));

        // 縮放大小
        float scale = 0.5f;
        poseStack.scale(scale, scale, scale);

        // 用ItemRenderer渲染物品
        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.NONE, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.getId());



        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity entity) {
        // 用ItemRenderer渲染物品，這裡返回null即可
        return null;
    }
}
