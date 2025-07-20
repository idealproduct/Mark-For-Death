package com.yichenxbohan.markedfordeath.client.model;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class MeteorRGBModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("markedfordeath", "meteor"), "main");
	private final ModelPart bb_main;

	public MeteorRGBModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(88, 80).addBox(-8.0F, -1.0F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 151).addBox(-9.0F, -3.0F, -9.0F, 18.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(152, 92).addBox(-9.0F, -3.0F, 8.0F, 18.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(114, 153).addBox(-9.0F, -35.0F, -9.0F, 18.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(152, 153).addBox(-9.0F, -35.0F, 8.0F, 18.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 92).addBox(-8.0F, -36.0F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(108, 156).addBox(0.0F, 4.0F, -8.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 7.0F, 4.0F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(140, 156).addBox(4.0F, 4.0F, -8.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 8.0F, 6.0F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 125).addBox(-4.0F, -14.0F, -8.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -12.0F, 2.0F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(76, 156).addBox(0.0F, 4.0F, -8.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -43.0F, -4.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(64, 92).addBox(4.0F, 4.0F, -8.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -44.0F, -6.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(120, 97).addBox(-4.0F, -14.0F, -8.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -24.0F, -2.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 139).addBox(-2.0F, -14.0F, -8.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -21.0F, 4.0F, -1.5708F, 0.0F, 1.5708F));

		PartDefinition cube_r8 = bb_main.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(112, 113).addBox(-6.0F, -14.0F, -8.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -19.0F, 2.0F, -1.5708F, 0.0F, 1.5708F));

		PartDefinition cube_r9 = bb_main.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(88, 60).addBox(-10.0F, -14.0F, -8.0F, 18.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -17.0F, 0.0F, -1.5708F, 0.0F, 1.5708F));

		PartDefinition cube_r10 = bb_main.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(128, 129).addBox(-2.0F, -14.0F, -8.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -21.0F, 3.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r11 = bb_main.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(56, 113).addBox(-6.0F, -14.0F, -8.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -19.0F, 1.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r12 = bb_main.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(88, 40).addBox(-10.0F, -14.0F, -8.0F, 18.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.0F, -1.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r13 = bb_main.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(88, 129).addBox(-2.0F, -14.0F, -8.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -21.0F, -4.0F, 1.5708F, 0.0F, 1.5708F));

		PartDefinition cube_r14 = bb_main.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 109).addBox(-6.0F, -14.0F, -8.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -19.0F, -2.0F, 1.5708F, 0.0F, 1.5708F));

		PartDefinition cube_r15 = bb_main.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(88, 20).addBox(-10.0F, -14.0F, -8.0F, 18.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -17.0F, 0.0F, 1.5708F, 0.0F, 1.5708F));

		PartDefinition cube_r16 = bb_main.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(48, 129).addBox(-2.0F, -14.0F, -8.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -21.0F, -3.0F, 3.1416F, 0.0F, 1.5708F));

		PartDefinition cube_r17 = bb_main.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(64, 97).addBox(-6.0F, -14.0F, -8.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -19.0F, -1.0F, 3.1416F, 0.0F, 1.5708F));

		PartDefinition cube_r18 = bb_main.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(88, 0).addBox(-10.0F, -14.0F, -8.0F, 18.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.0F, 1.0F, 3.1416F, 0.0F, 1.5708F));

		PartDefinition cube_r19 = bb_main.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(152, 89).addBox(-19.0F, -3.0F, -1.0F, 20.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(82, 150).addBox(-19.0F, 25.0F, -1.0F, 20.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -30.0F, -10.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r20 = bb_main.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(86, 147).addBox(-21.0F, -3.0F, -1.0F, 22.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 144).addBox(-21.0F, 21.0F, -1.0F, 22.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -28.0F, -11.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r21 = bb_main.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(40, 147).addBox(-21.0F, -3.0F, -1.0F, 22.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 141).addBox(-21.0F, 21.0F, -1.0F, 22.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -28.0F, 10.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r22 = bb_main.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(132, 144).addBox(-21.0F, -3.0F, -1.0F, 22.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(86, 141).addBox(-21.0F, 21.0F, -1.0F, 22.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -28.0F, -10.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r23 = bb_main.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(86, 144).addBox(-21.0F, -3.0F, -1.0F, 22.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 141).addBox(-21.0F, 21.0F, -1.0F, 22.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -28.0F, -10.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r24 = bb_main.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(152, 86).addBox(-19.0F, -3.0F, -1.0F, 20.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 150).addBox(-19.0F, 25.0F, -1.0F, 20.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -30.0F, -9.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r25 = bb_main.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(152, 83).addBox(-19.0F, -3.0F, -1.0F, 20.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 147).addBox(-19.0F, 25.0F, -1.0F, 20.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -30.0F, -9.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r26 = bb_main.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(38, 156).addBox(-17.0F, -3.0F, -1.0F, 18.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 153).addBox(-17.0F, 29.0F, -1.0F, 18.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -32.0F, -8.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r27 = bb_main.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(0, 154).addBox(-17.0F, -3.0F, -1.0F, 18.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(38, 153).addBox(-17.0F, 29.0F, -1.0F, 18.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -32.0F, -8.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r28 = bb_main.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(152, 80).addBox(-19.0F, -3.0F, -1.0F, 20.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(124, 150).addBox(-19.0F, 25.0F, -1.0F, 20.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -30.0F, 9.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r29 = bb_main.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(0, 69).addBox(-14.0F, -13.0F, -8.0F, 22.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.0F, -15.0F, 3.0F, 3.1416F, 0.0F, 1.5708F));

		PartDefinition cube_r30 = bb_main.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(0, 46).addBox(-14.0F, -13.0F, -8.0F, 22.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -15.0F, 3.0F, 3.1416F, 0.0F, 1.5708F));

		PartDefinition cube_r31 = bb_main.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(0, 23).addBox(-14.0F, -13.0F, -8.0F, 22.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -15.0F, -24.0F, -1.5708F, 0.0F, 1.5708F));

		PartDefinition cube_r32 = bb_main.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -13.0F, -8.0F, 22.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -15.0F, -1.0F, -1.5708F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			// 使用更高頻率的時間（每秒循環 10 次以上）
			float time = (System.currentTimeMillis() % 1000L) / 1000.0f; // 每秒循環一次
			time *= 2.0f; // 提高頻率（例如 10 倍）

			float r = (float)(Math.sin(time * 2 * Math.PI + 0.0f) * 0.5f + 0.5f);
			float g = (float)(Math.sin(time * 2 * Math.PI + 2.0f) * 0.5f + 0.5f);
			float b = (float)(Math.sin(time * 2 * Math.PI + 4.0f) * 0.5f + 0.5f);

			bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, r, g, b, alpha);

	}


}