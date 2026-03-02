package com.thecyborgage.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thecyborgage.client.models.PlayerRadarModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class PlayerRadarRenderer implements ICurioRenderer {
  private static final ResourceLocation TEXTURE =
      ResourceLocation.fromNamespaceAndPath("thecyborgage", "textures/models/curios/radar_hat.png");
  private PlayerRadarModel model;

  @Override
  public <T extends LivingEntity, M extends EntityModel<T>> void render(
      ItemStack stack,
      SlotContext slotContext,
      PoseStack matrixStack,
      RenderLayerParent<T, M> renderLayerParent,
      MultiBufferSource renderTypeBuffer,
      int light,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch) {
    LivingEntity entity = slotContext.entity();

    matrixStack.pushPose();

    if (renderLayerParent.getModel() instanceof HumanoidModel<?> humanoidModel) {
      ICurioRenderer.followHeadRotations(entity, humanoidModel.head);
      humanoidModel.head.translateAndRotate(matrixStack);
    }

    PlayerRadarModel playerRadarModel = this.getModel();

    playerRadarModel.setupAnim(ageInTicks);

    VertexConsumer vertexConsumer =
        ItemRenderer.getArmorFoilBuffer(
            renderTypeBuffer, RenderType.entityCutoutNoCull(TEXTURE), stack.hasFoil());

    playerRadarModel.renderToBuffer(
        matrixStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

    matrixStack.popPose();
  }

  private PlayerRadarModel getModel() {
    if (this.model == null) {
      this.model =
          new PlayerRadarModel(
              Minecraft.getInstance().getEntityModels().bakeLayer(PlayerRadarModel.LAYER_LOCATION));
    }

    return this.model;
  }
}
