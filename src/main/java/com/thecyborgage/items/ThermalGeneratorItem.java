package com.thecyborgage.items;

import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.TCACuriosHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class ThermalGeneratorItem extends Item implements ICurioItem {
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

    double heatValue =
        temperature * TCAServerConfig.CONFIG.thermalGeneratorTempCoefficient.getAsDouble();

    if (isRaining) {
      heatValue *= TCAServerConfig.CONFIG.thermalGeneratorRainCoefficient.getAsDouble();
    }

    TCACuriosHelper.addEntityCoreEnergy(entity, Math.max((int) heatValue, 0));
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

    tooltipComponents.add(
        Component.translatable("thecyborgage.thermal_generator.tooltip")
            .withStyle(ChatFormatting.GRAY));
  }
}
