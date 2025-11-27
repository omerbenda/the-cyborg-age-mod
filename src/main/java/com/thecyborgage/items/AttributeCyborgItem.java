package com.thecyborgage.items;

import com.thecyborgage.TCACuriosHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class AttributeCyborgItem extends Item implements ICurioItem {
  private final ResourceLocation modifierResLoc;
  private final Holder<Attribute> attribute;
  private final AttributeModifier.Operation operation;

  public AttributeCyborgItem(
      Properties properties,
      ResourceLocation modifierResLoc,
      Holder<Attribute> attribute,
      AttributeModifier.Operation operation) {
    super(properties);

    this.modifierResLoc = modifierResLoc;
    this.attribute = attribute;
    this.operation = operation;
  }

  public abstract double getAmount(SlotContext slotContext, ItemStack stack);

  public abstract int getEnergyUsage(SlotContext slotContext, ItemStack stack);

  public boolean shouldConsumeEnergy(SlotContext slotContext, ItemStack stack) {
    return true;
  }

  public int getMinEnergyRequired(SlotContext slotContext, ItemStack stack) {
    return this.getEnergyUsage(slotContext, stack);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);

    LivingEntity entity = slotContext.entity();
    AttributeMap attributes = entity.getAttributes();
    AttributeInstance attributeInstance = attributes.getInstance(this.attribute);

    if (attributeInstance == null) {
      return;
    }

    if (TCACuriosHelper.consumeEntityCoreEnergy(
        entity, this.getMinEnergyRequired(slotContext, stack), true)) {
      attributeInstance.addOrUpdateTransientModifier(
          this.createAttributeModifier(slotContext, stack));

      if (this.shouldConsumeEnergy(slotContext, stack)) {
        TCACuriosHelper.consumeEntityCoreEnergy(entity, this.getEnergyUsage(slotContext, stack));
      }
    } else {
      attributeInstance.removeModifier(this.modifierResLoc);
    }
  }

  @Override
  public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
    ICurioItem.super.onUnequip(slotContext, newStack, stack);

    LivingEntity entity = slotContext.entity();
    AttributeMap attributes = entity.getAttributes();
    AttributeInstance attributeInstance = attributes.getInstance(this.attribute);

    if (attributeInstance == null) {
      return;
    }

    attributeInstance.removeModifier(this.modifierResLoc);
  }

  private AttributeModifier createAttributeModifier(SlotContext slotContext, ItemStack stack) {
    return new AttributeModifier(
        this.modifierResLoc, this.getAmount(slotContext, stack), this.operation);
  }
}
