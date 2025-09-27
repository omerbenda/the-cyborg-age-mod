package com.thecyborgage.items;

import com.thecyborgage.init.TCADataComponents;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CyborgCoreItem extends Item implements ICurioItem {
  private static final int MAX_ENERGY = 10000;
  private static final int RECHARGE_RATE = 10;

  public CyborgCoreItem(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);

    if (slotContext.entity().isSprinting()) {
      int energy = stack.getOrDefault(TCADataComponents.CORE_ENERGY, 0);
      stack.set(TCADataComponents.CORE_ENERGY, Math.min(energy + RECHARGE_RATE, MAX_ENERGY));
    }
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

    tooltipComponents.add(
        Component.translatable(
            "thecyborgage.cyborg_core.energy_tooltip",
            stack.getOrDefault(TCADataComponents.CORE_ENERGY, 0)));
  }
}
