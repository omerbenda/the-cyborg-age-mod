package com.thecyborgage.items;

import com.thecyborgage.TCAConfig;
import com.thecyborgage.TCACuriosHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CyborgGeneratorLegItem extends Item implements ICurioItem {
  public CyborgGeneratorLegItem(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);
    LivingEntity entity = slotContext.entity();

    if (entity.isSprinting()) {
      TCACuriosHelper.addEntityCoreEnergy(entity, TCAConfig.CONFIG.generatorLegChargeRate.getAsInt());
    }
  }
}
