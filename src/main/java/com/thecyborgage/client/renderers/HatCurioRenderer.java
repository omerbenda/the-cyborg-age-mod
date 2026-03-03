package com.thecyborgage.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
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

public class HatCurioRenderer implements ICurioRenderer {
  private final ResourceLocation texture;
  protected final Model model;

  public HatCurioRenderer(ResourceLocation texture, Model model) {
    this.texture = texture;
    this.model = model;
  }

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

    VertexConsumer vertexConsumer =
        ItemRenderer.getArmorFoilBuffer(
            renderTypeBuffer, RenderType.armorCutoutNoCull(this.texture), stack.hasFoil());

    this.model.renderToBuffer(
        matrixStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

    matrixStack.popPose();
  }
}
