package com.thecyborgage.items;

import com.google.common.collect.Multimap;
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

  public AttributeCyborgItem(
      Properties properties,
      ResourceLocation modifierResLoc,
      Holder<Attribute> attribute) {
    super(properties);

    this.modifierResLoc = modifierResLoc;
    this.attribute = attribute;
  }

  public abstract double getAmount(SlotContext slotContext, ItemStack stack);

  public abstract int getEnergyUsage(SlotContext slotContext, ItemStack stack);

  public abstract AttributeModifier.Operation getOperation(SlotContext slotContext, ItemStack stack);

  public boolean shouldConsumeEnergy(SlotContext slotContext, ItemStack stack) {
    return true;
  }

  public boolean shouldApplyAttribute(SlotContext slotContext, ItemStack stack) {
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

    ResourceLocation formattedResLoc = formatResourceLocation(this.modifierResLoc, slotContext);

    if (TCACuriosHelper.consumeEntityCoreEnergy(
        entity, this.getMinEnergyRequired(slotContext, stack), true)) {
      if (this.shouldApplyAttribute(slotContext, stack)) {
        attributeInstance.addOrUpdateTransientModifier(
            this.createAttributeModifier(slotContext, stack));
      } else {
        attributeInstance.removeModifier(formattedResLoc);
      }

      if (this.shouldConsumeEnergy(slotContext, stack)) {
        TCACuriosHelper.consumeEntityCoreEnergy(entity, this.getEnergyUsage(slotContext, stack));
      }
    } else {
      attributeInstance.removeModifier(formattedResLoc);
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

    attributeInstance.removeModifier(formatResourceLocation(this.modifierResLoc, slotContext));
  }

  @Override
  public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
      SlotContext slotContext, ResourceLocation id, ItemStack stack) {
    Multimap<Holder<Attribute>, AttributeModifier> modifierMap =
        ICurioItem.super.getAttributeModifiers(slotContext, id, stack);

    modifierMap.put(this.attribute, createAttributeModifier(slotContext, stack));

    return modifierMap;
  }

  private AttributeModifier createAttributeModifier(SlotContext slotContext, ItemStack stack) {
    return new AttributeModifier(
        formatResourceLocation(this.modifierResLoc, slotContext),
        this.getAmount(slotContext, stack),
        this.getOperation(slotContext, stack));
  }

  private static ResourceLocation formatResourceLocation(
      ResourceLocation resLoc, SlotContext slotContext) {
    return resLoc.withSuffix("." + slotContext.identifier() + "." + slotContext.index());
  }
}
