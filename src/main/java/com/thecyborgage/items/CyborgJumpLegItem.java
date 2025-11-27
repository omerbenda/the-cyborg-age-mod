package com.thecyborgage.items;

import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class CyborgJumpLegItem extends AttributeCyborgItem {
  public CyborgJumpLegItem(Properties properties) {
    super(
        properties,
        ResourceLocation.fromNamespaceAndPath(
            TheCyborgAgeMod.MOD_ID, "attribute.cyborg_jump_leg.jump_modifier"),
        Attributes.JUMP_STRENGTH,
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
  }

  @Override
  public double getAmount(SlotContext slotContext, ItemStack stack) {
    return 1;
  }

  @Override
  public int getEnergyUsage(SlotContext slotContext, ItemStack stack) {
    return 0;
  }
}
