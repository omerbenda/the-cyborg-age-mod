package com.thecyborgage.items;

import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.TCAEntityHelper;
import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class CyborgLegItem extends AttributeCyborgItem {
  public static final ResourceLocation SPEED_MODIFIER_RESOURCE =
      ResourceLocation.fromNamespaceAndPath(
          TheCyborgAgeMod.MOD_ID, "attribute.cyborg_leg.speed_modifier");

  public CyborgLegItem(Properties properties) {
    super(properties, SPEED_MODIFIER_RESOURCE, Attributes.MOVEMENT_SPEED);
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
  public AttributeModifier.Operation getOperation(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.cyborgLegOperation.get();
  }

  @Override
  public boolean shouldConsumeEnergy(SlotContext slotContext, ItemStack stack) {
    return TCAEntityHelper.isEntityMovingHorizontal(slotContext.entity());
  }
}
