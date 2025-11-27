package com.thecyborgage.items;

import com.thecyborgage.TCAConfig;
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
        Attributes.ARMOR,
        AttributeModifier.Operation.ADD_VALUE);
  }

  @Override
  public double getAmount(SlotContext slotContext, ItemStack stack) {
    return TCAConfig.CONFIG.energyArmorArmorValue.getAsInt();
  }

  @Override
  public int getEnergyUsage(SlotContext slotContext, ItemStack stack) {
    return TCAConfig.CONFIG.energyArmorDischargeRate.getAsInt();
  }
}
