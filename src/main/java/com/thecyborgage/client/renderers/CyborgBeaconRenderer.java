package com.thecyborgage.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.client.models.CyborgBeaconModel;
import com.thecyborgage.entities.CyborgBeaconEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class CyborgBeaconRenderer extends EntityRenderer<CyborgBeaconEntity> {
  private static final ResourceLocation TEXTURE =
      ResourceLocation.fromNamespaceAndPath(
          TheCyborgAgeMod.MOD_ID, "textures/entity/cyborg_beacon.png");
  private final CyborgBeaconModel<CyborgBeaconEntity> model;

  public CyborgBeaconRenderer(EntityRendererProvider.Context context) {
    super(context);
    this.model = new CyborgBeaconModel<>(context.bakeLayer(CyborgBeaconModel.LAYER_LOCATION));
  }

  @Override
  public void render(
      CyborgBeaconEntity entity,
      float entityYaw,
      float partialTick,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight) {
    poseStack.pushPose();

    poseStack.translate(0.0F, 1.5F, 0.0F);
    poseStack.scale(-1.0F, -1.0F, 1.0F);

    VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));

    this.model.renderRod(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

    int bulbColor = 0xFFFFFFFF;
    int bulbLight = packedLight;

    if (entity.isFlashing()) {
      float pulse = (float) (Math.sin((entity.tickCount + partialTick) * 0.4) * 0.5 + 0.5);

      int red = 255;
      int green = (int) (255 * (1.0F - pulse));
      int blue = (int) (255 * (1.0F - pulse));

      bulbColor = (255 << 24) | (red << 16) | (green << 8) | blue;

      bulbLight = LightTexture.FULL_BRIGHT;
    }

    this.model.renderBulb(
        poseStack, vertexConsumer, bulbLight, OverlayTexture.NO_OVERLAY, bulbColor);

    poseStack.popPose();
    super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
  }

  @Override
  public ResourceLocation getTextureLocation(CyborgBeaconEntity entity) {
    return TEXTURE;
  }
}
