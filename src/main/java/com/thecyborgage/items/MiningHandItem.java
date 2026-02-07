package com.thecyborgage.items;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.config.TCAServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class MiningHandItem extends AttributeCyborgItem {
  public MiningHandItem(Properties properties) {
    super(
        properties,
        ResourceLocation.fromNamespaceAndPath(
            TheCyborgAgeMod.MOD_ID, "attribute.mining_hand.mining_speed_modifier"),
        Attributes.BLOCK_BREAK_SPEED,
        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
  }

  @Override
  public double getAmount(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.miningHandValue.getAsDouble();
  }

  @Override
  public int getEnergyUsage(SlotContext slotContext, ItemStack stack) {
    return 0;
  }

  @Override
  public int getMinEnergyRequired(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.miningHandDischarge.getAsInt();
  }
}
