package com.thecyborgage.items;

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
  public static final float SPEED_INCREASE = 0.5F;
  public static final int ENERGY_USAGE = 50;

  public CyborgLegItem(Properties properties) {
    super(
        properties,
        SPEED_MODIFIER_RESOURCE,
        Attributes.MOVEMENT_SPEED,
        SPEED_INCREASE,
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
        ENERGY_USAGE);
  }

  @Override
  public boolean shouldConsumeEnergy(SlotContext slotContext, ItemStack stack) {
    return TCAEntityHelper.isEntityMovingHorizontal(slotContext.entity());
  }
}
