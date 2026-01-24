package com.thecyborgage.items;

import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.TCAEntityHelper;
import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class CyborgLegItem extends AttributeCyborgItem {
  public static final ResourceLocation SPEED_MODIFIER_RESOURCE =
      ResourceLocation.fromNamespaceAndPath(
          TheCyborgAgeMod.MOD_ID, "attribute.cyborg_leg.speed_modifier");

  public CyborgLegItem(Properties properties) {
    super(
        properties,
        SPEED_MODIFIER_RESOURCE,
        Attributes.MOVEMENT_SPEED,
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
  }

  @Override
  public double getAmount(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.cyborgLegSpeedBoost.getAsDouble();
  }

  @Override
  public int getEnergyUsage(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.cyborgLegDischargeRate.getAsInt();
  }

  @Override
  public boolean shouldConsumeEnergy(SlotContext slotContext, ItemStack stack) {
    return TCAEntityHelper.isEntityMovingHorizontal(slotContext.entity());
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

    tooltipComponents.add(
        Component.translatable("thecyborgage.cyborg_leg.tooltip").withStyle(ChatFormatting.GRAY));
  }
}
