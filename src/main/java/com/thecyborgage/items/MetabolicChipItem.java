package com.thecyborgage.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class MetabolicChipItem extends Item implements ICurioItem {
  public MetabolicChipItem(Properties properties) {
    super(properties);
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

    tooltipComponents.add(
        Component.translatable("thecyborgage.metabolic_chip.tooltip")
            .withStyle(ChatFormatting.GRAY));
  }
}
