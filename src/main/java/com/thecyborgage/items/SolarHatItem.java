package com.thecyborgage.items;

import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.TCACuriosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class SolarHatItem extends Item implements ICurioItem {
  public SolarHatItem(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);

    LivingEntity entity = slotContext.entity();

    if (receivesSun(entity.level(), entity.blockPosition())) {
      TCACuriosHelper.addEntityCoreEnergy(entity, TCAServerConfig.CONFIG.solarHatChargeRate.getAsInt());
    }
  }

  private static boolean receivesSun(Level level, BlockPos blockPos) {
    return level.isDay() && level.canSeeSky(blockPos);
  }
}
