package com.thecyborgage.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class CyborgBeaconModel<T extends Entity> extends EntityModel<T> {
  public static final ModelLayerLocation LAYER_LOCATION =
      new ModelLayerLocation(
          ResourceLocation.fromNamespaceAndPath(TheCyborgAgeMod.MOD_ID, "cyborg_beacon"), "main");

  private final ModelPart rod;
  private final ModelPart bulb;

  public CyborgBeaconModel(ModelPart root) {
    this.rod = root.getChild("rod");
    this.bulb = root.getChild("bulb");
  }

  public static LayerDefinition createBodyLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();

    partdefinition.addOrReplaceChild(
        "rod",
        CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 14.0F, 4.0F),
        PartPose.ZERO);

    partdefinition.addOrReplaceChild(
        "bulb",
        CubeListBuilder.create().texOffs(16, 0).addBox(-4.0F, 2.0F, -4.0F, 8.0F, 8.0F, 8.0F),
        PartPose.ZERO);

    return LayerDefinition.create(meshdefinition, 64, 64);
  }

  @Override
  public void setupAnim(
      T entity,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch) {}

  @Override
  public void renderToBuffer(
      PoseStack poseStack,
      VertexConsumer vertexConsumer,
      int packedLight,
      int packedOverlay,
      int color) {
    this.renderRod(poseStack, vertexConsumer, packedLight, packedOverlay);
    this.renderBulb(poseStack, vertexConsumer, packedLight, packedOverlay, color);
  }

  public void renderRod(
      PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
    this.rod.render(poseStack, vertexConsumer, packedLight, packedOverlay, 0xFFFFFFFF);
  }

  public void renderBulb(
      PoseStack poseStack,
      VertexConsumer vertexConsumer,
      int packedLight,
      int packedOverlay,
      int colorARGB) {
    this.bulb.render(poseStack, vertexConsumer, packedLight, packedOverlay, colorARGB);
  }
}
