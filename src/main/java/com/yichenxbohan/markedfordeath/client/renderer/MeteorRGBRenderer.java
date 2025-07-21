package com.yichenxbohan.markedfordeath.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yichenxbohan.markedfordeath.client.model.MeteorModel;
import com.yichenxbohan.markedfordeath.entity.MeteorRGBEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.yichenxbohan.markedfordeath.util.RenderUtils.hslToRgb;

public class MeteorRGBRenderer extends EntityRenderer<MeteorRGBEntity> {
    private final EntityModel<MeteorRGBEntity> model;

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation("markedfordeath", "meteor"), "main");

    public MeteorRGBRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MeteorModel<>(context.bakeLayer(LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MeteorRGBEntity entity) {
        return new ResourceLocation("markedfordeath", "textures/entity/meteor_rgb.png");
    }

    @Override
    public void render(@NotNull MeteorRGBEntity entity, float yaw, float partialTicks,
                       PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {

        matrixStack.pushPose();
        // 這裡調整大小，3D模型會放大或縮小
        float scale = 35.0F;  // 2倍大
        matrixStack.scale(scale, scale, scale);

        // RGB Calculation
        final long CYCLE_DURATION = 3000;
        long elapsedInCycle = System.currentTimeMillis() % CYCLE_DURATION;
        var rgb = hslToRgb((float) ((elapsedInCycle / (double) CYCLE_DURATION) * 360.0), 0.5f, 0.5f);
        float r = rgb[0], g = rgb[1], b = rgb[2];

        model.renderToBuffer(matrixStack,
                buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity))),
                packedLight, OverlayTexture.NO_OVERLAY,
                r, g, b, 1.0f);
        matrixStack.popPose();

        super.render(entity, yaw, partialTicks, matrixStack, buffer, packedLight);
    }
}
