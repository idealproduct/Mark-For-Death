package com.yichenxbohan.markedfordeath.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class ModelSoulArmor extends HumanoidModel<LivingEntity> {

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(new ResourceLocation("markedfordeath", "soul_armor"), "main");

	public ModelSoulArmor(ModelPart root) {
		super(root, RenderType::entityCutoutNoCull);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition root = mesh.getRoot();

		// 頭盔範例（可以改成你模型中定義的造型）
		root.addOrReplaceChild("head",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-4F, -8F, -4F, 8, 8, 8),
				PartPose.ZERO);

		// 胸甲
		root.addOrReplaceChild("body",
				CubeListBuilder.create()
						.texOffs(16, 16)
						.addBox(-4F, 0F, -2F, 8, 12, 4),
				PartPose.ZERO);

		// 左右手
		root.addOrReplaceChild("right_arm",
				CubeListBuilder.create()
						.texOffs(40, 16)
						.addBox(-3F, -2F, -2F, 4, 12, 4),
				PartPose.ZERO);

		root.addOrReplaceChild("left_arm",
				CubeListBuilder.create()
						.texOffs(40, 16)
						.mirror()
						.addBox(-1F, -2F, -2F, 4, 12, 4),
				PartPose.ZERO);

		// 護腿
		root.addOrReplaceChild("right_leg",
				CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(-2F, 0F, -2F, 4, 12, 4),
				PartPose.ZERO);

		root.addOrReplaceChild("left_leg",
				CubeListBuilder.create()
						.texOffs(0, 16)
						.mirror()
						.addBox(-2F, 0F, -2F, 4, 12, 4),
				PartPose.ZERO);

		// 靴子可以額外加（可選）
		// ...

		return LayerDefinition.create(mesh, 64, 64); // 根據你貼圖大小設定（如 64x64）
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount,
						  float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer buffer, int light,
							   int overlay, float red, float green, float blue, float alpha) {
		super.renderToBuffer(stack, buffer, light, overlay, red, green, blue, alpha);
	}
}
