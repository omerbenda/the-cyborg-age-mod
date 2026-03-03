package com.thecyborgage.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thecyborgage.client.models.AgeableModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class AgingHatCurioRenderer extends HatCurioRenderer {
  public AgingHatCurioRenderer(ResourceLocation texture, AgeableModel model) {
    super(texture, model);
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
    super.render(
        stack,
        slotContext,
        matrixStack,
        renderLayerParent,
        renderTypeBuffer,
        light,
        limbSwing,
        limbSwingAmount,
        partialTicks,
        ageInTicks,
        netHeadYaw,
        headPitch);

    ((AgeableModel) this.model).setAge(ageInTicks);
  }
}
