package com.thecyborgage.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SolarHatModel extends Model {
  public static final ModelLayerLocation LAYER_LOCATION =
      new ModelLayerLocation(
          ResourceLocation.fromNamespaceAndPath(TheCyborgAgeMod.MOD_ID, "solar_hat"), "main");
  private final ModelPart panelAssembly;

  public SolarHatModel(ModelPart root) {
    super(RenderType::entityCutoutNoCull);
    this.panelAssembly = root.getChild("head").getChild("solar_assembly");
  }

  public static LayerDefinition createLayer() {
    MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
    PartDefinition partdefinition = meshdefinition.getRoot();
    PartDefinition head = partdefinition.getChild("head");

    PartDefinition solarAssembly =
        head.addOrReplaceChild("solar_assembly", CubeListBuilder.create(), PartPose.ZERO);

    solarAssembly.addOrReplaceChild(
        "rod",
        CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -14.0F, -0.5F, 1.0F, 6.0F, 1.0F),
        PartPose.ZERO);

    solarAssembly.addOrReplaceChild(
        "panel",
        CubeListBuilder.create().texOffs(0, 8).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 1.0F, 8.0F),
        PartPose.offsetAndRotation(0.0F, -14.0F, 0.0F, 0.5F, 0.0F, 0.0F));

    return LayerDefinition.create(meshdefinition, 32, 32);
  }

  @Override
  public void renderToBuffer(
      PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
    this.panelAssembly.render(poseStack, buffer, packedLight, packedOverlay, color);
  }
}
