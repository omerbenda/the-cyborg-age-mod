package com.thecyborgage.items;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CyborgLegItem extends Item implements ICurioItem {
  public static final int ENERGY_USAGE = 10;
  public static final float SPEED_INCREASE = 0.15F;
  public static final ResourceLocation SPEED_MODIFIER_RESOURCE =
      ResourceLocation.fromNamespaceAndPath(
          TheCyborgAgeMod.MOD_ID, "attribute.cyborg_leg.speed_modifier");

  public CyborgLegItem(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);

    LivingEntity entity = slotContext.entity();
    AttributeMap attributes = entity.getAttributes();
    AttributeInstance speedAttribute = attributes.getInstance(Attributes.MOVEMENT_SPEED);

    if (speedAttribute == null) {
      return;
    }

    if (TCACuriosHelper.tryConsumeEntityCoreEnergy(entity, ENERGY_USAGE)) {
      speedAttribute.addOrUpdateTransientModifier(
          new AttributeModifier(
              SPEED_MODIFIER_RESOURCE,
              SPEED_INCREASE,
              AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    } else {
      speedAttribute.removeModifier(SPEED_MODIFIER_RESOURCE);
    }
  }

  @Override
  public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
    ICurioItem.super.onUnequip(slotContext, newStack, stack);

    LivingEntity entity = slotContext.entity();
    AttributeMap attributes = entity.getAttributes();
    AttributeInstance speedAttribute = attributes.getInstance(Attributes.MOVEMENT_SPEED);

    if (speedAttribute == null) {
      return;
    }

    speedAttribute.removeModifier(SPEED_MODIFIER_RESOURCE);
  }
}
