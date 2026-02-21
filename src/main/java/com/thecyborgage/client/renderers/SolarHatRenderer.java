package com.thecyborgage.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.client.models.SolarHatModel;
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

public class SolarHatRenderer implements ICurioRenderer {
  private static final ResourceLocation TEXTURE =
      ResourceLocation.fromNamespaceAndPath(
          TheCyborgAgeMod.MOD_ID, "textures/entity/curios/solar_hat.png");
  private SolarHatModel model;

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
    if (!(renderLayerParent.getModel() instanceof HumanoidModel<?> humanoidModel)) {
      return;
    }

    matrixStack.pushPose();

    humanoidModel.head.translateAndRotate(matrixStack);

    SolarHatModel hatModel = getModel();

    VertexConsumer vertexConsumer =
        ItemRenderer.getArmorFoilBuffer(
            renderTypeBuffer, RenderType.armorCutoutNoCull(TEXTURE), stack.hasFoil());

    hatModel.renderToBuffer(
        matrixStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

    matrixStack.popPose();
  }

  private SolarHatModel getModel() {
    if (this.model == null) {
      this.model =
          new SolarHatModel(
              Minecraft.getInstance().getEntityModels().bakeLayer(SolarHatModel.LAYER_LOCATION));
    }
    return this.model;
  }
}
