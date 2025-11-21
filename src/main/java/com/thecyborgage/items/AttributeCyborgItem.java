package com.thecyborgage.items;

import com.thecyborgage.TCACuriosHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Optional;

public class AttributeCyborgItem extends Item implements ICurioItem {
  private final ResourceLocation modifierResLoc;
  private final Holder<Attribute> attribute;
  private final double amount;
  private final AttributeModifier.Operation operation;
  private final int energyUsage;

  public AttributeCyborgItem(
      Properties properties,
      ResourceLocation modifierResLoc,
      Holder<Attribute> attribute,
      double amount,
      AttributeModifier.Operation operation,
      int energyUsage) {
    super(properties);

    this.modifierResLoc = modifierResLoc;
    this.attribute = attribute;
    this.amount = amount;
    this.operation = operation;
    this.energyUsage = energyUsage;
  }

  public boolean shouldConsumeEnergy(SlotContext slotContext, ItemStack stack) {
    return true;
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

    Optional<IEnergyStorage> optionalCoreEnergyStorage =
        TCACuriosHelper.getEntityCoreEnergyStorage(entity);

    if (optionalCoreEnergyStorage.isEmpty()) {
      return;
    }

    IEnergyStorage coreEnergyStorage = optionalCoreEnergyStorage.get();

    if (coreEnergyStorage.extractEnergy(this.energyUsage, true) != -1) {
      attributeInstance.addOrUpdateTransientModifier(this.createAttributeModifier());

      if (this.shouldConsumeEnergy(slotContext, stack)) {
        coreEnergyStorage.extractEnergy(this.energyUsage, false);
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

  private AttributeModifier createAttributeModifier() {
    return new AttributeModifier(this.modifierResLoc, this.amount, this.operation);
  }
}
