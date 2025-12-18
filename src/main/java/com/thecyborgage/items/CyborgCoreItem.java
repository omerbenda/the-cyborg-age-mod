package com.thecyborgage.items;

import com.google.common.collect.Iterables;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCADataComponents;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CyborgCoreItem extends Item implements ICurioItem {
  public CyborgCoreItem(Properties properties) {
    super(properties);
  }

  public int getMaxEnergy(ItemStack itemStack) {
    int baseEnergy = TCAServerConfig.CONFIG.cyborgCoreMaxEnergy.getAsInt();

    ItemContainerContents contents = itemStack.get(TCADataComponents.ENERGY_ITEM_STORAGE);

    if (contents == null) {
      return baseEnergy;
    }

    return baseEnergy
        + Iterables.size(contents.nonEmptyItemsCopy())
            * TCAServerConfig.CONFIG.coreBatteryStorage.getAsInt();
  }

  @Override
  public void inventoryTick(
      ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
    super.inventoryTick(stack, level, entity, slotId, isSelected);

    Integer energyStorage = stack.get(TCADataComponents.CORE_ENERGY);

    if (energyStorage == null) {
      return;
    }

    int maxEnergy = this.getMaxEnergy(stack);

    if (energyStorage > maxEnergy) {
      stack.set(TCADataComponents.CORE_ENERGY, maxEnergy);
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
    tooltipComponents.add(
        Component.translatable(
            "thecyborgage.cyborg_core.max_energy_tooltip", this.getMaxEnergy(stack)));
  }
}
