package com.thecyborgage.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class PlayerRadarModel extends Model {
  public static final ModelLayerLocation LAYER_LOCATION =
      new ModelLayerLocation(
          ResourceLocation.fromNamespaceAndPath(TheCyborgAgeMod.MOD_ID, "player_radar"), "main");

  private final ModelPart root;
  private final ModelPart radarDish;

  public PlayerRadarModel(ModelPart root) {
    super(RenderType::entityCutoutNoCull);
    this.root = root;
    this.radarDish = root.getChild("base").getChild("radar_dish");
  }

  public static LayerDefinition createLayer() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();

    PartDefinition base =
        partdefinition.addOrReplaceChild(
            "base",
            CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 2.0F, 8.0F),
            PartPose.ZERO);

    base.addOrReplaceChild(
        "radar_dish",
        CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -12.0F, -1.0F, 4.0F, 3.0F, 2.0F),
        PartPose.ZERO);

    return LayerDefinition.create(meshdefinition, 32, 32);
  }

  public void setupAnim(float ageInTicks) {
    this.radarDish.yRot = ageInTicks * 0.15F;
  }

  @Override
  public void renderToBuffer(
      PoseStack poseStack,
      VertexConsumer vertexConsumer,
      int packedLight,
      int packedOverlay,
      int color) {
    this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
  }
}
