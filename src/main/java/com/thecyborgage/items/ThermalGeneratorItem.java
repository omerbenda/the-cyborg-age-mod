package com.thecyborgage.items;

import com.thecyborgage.TCACuriosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ThermalGeneratorItem extends Item implements ICurioItem {
  private static final float TEMPERATURE_COEFFICIENT = 2.0F;
  private static final float RAIN_COEFFICIENT = 0.2F;

  public ThermalGeneratorItem(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);
    LivingEntity entity = slotContext.entity();
    BlockPos blockPos = entity.blockPosition();
    Level level = entity.level();

    float temperature = level.getBiome(blockPos).value().getBaseTemperature();
    boolean isRaining = level.isRainingAt(blockPos);

    float heatValue = temperature * TEMPERATURE_COEFFICIENT;

    if (isRaining) {
      heatValue *= RAIN_COEFFICIENT;
    }

    TCACuriosHelper.addEntityCoreEnergy(entity, Math.max((int) heatValue, 0));
  }
}
