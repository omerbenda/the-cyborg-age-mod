package com.thecyborgage.items;

import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class EnergyArmorItem extends AttributeCyborgItem {
  public EnergyArmorItem(Properties properties) {
    super(
        properties,
        ResourceLocation.fromNamespaceAndPath(
            TheCyborgAgeMod.MOD_ID, "attribute.energy_armor.armor_modifier"),
        Attributes.ARMOR);
  }

  @Override
  public double getAmount(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.energyArmorArmorValue.getAsInt();
  }

  @Override
  public int getEnergyUsage(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.energyArmorDischargeRate.getAsInt();
  }

  @Override
  public AttributeModifier.Operation getOperation(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.energyArmorOperation.get();
  }
}
