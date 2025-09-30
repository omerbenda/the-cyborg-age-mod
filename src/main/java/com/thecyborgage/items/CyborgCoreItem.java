package com.thecyborgage.items;

import com.thecyborgage.init.TCADataComponents;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CyborgCoreItem extends Item implements ICurioItem {
  private static final int MAX_ENERGY = 10000;

  public CyborgCoreItem(Properties properties) {
    super(properties);
  }

  public int getMaxEnergy() {
    return MAX_ENERGY;
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
