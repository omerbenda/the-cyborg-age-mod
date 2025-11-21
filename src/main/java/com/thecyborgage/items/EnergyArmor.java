package com.thecyborgage.items;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class EnergyArmor extends Item implements ICurioItem {
  public static final int ENERGY_USAGE = 50;
  public static final int ARMOR_INCREASE = 6;
  public static final ResourceLocation ARMOR_MODIFIER_RESOURCE =
      ResourceLocation.fromNamespaceAndPath(
          TheCyborgAgeMod.MOD_ID, "attribute.energy_armor.armor_modifier");

  public EnergyArmor(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);

    LivingEntity entity = slotContext.entity();
    AttributeMap attributes = entity.getAttributes();
    AttributeInstance armorAttribute = attributes.getInstance(Attributes.ARMOR);

    if (armorAttribute == null) {
      return;
    }

    if (TCACuriosHelper.consumeEntityCoreEnergy(entity, ENERGY_USAGE, true)) {
      armorAttribute.addOrUpdateTransientModifier(this.createAttributeModifier());
      TCACuriosHelper.consumeEntityCoreEnergy(entity, ENERGY_USAGE);
    } else {
      armorAttribute.removeModifier(ARMOR_MODIFIER_RESOURCE);
    }
  }

  @Override
  public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
    ICurioItem.super.onEquip(slotContext, prevStack, stack);

    LivingEntity entity = slotContext.entity();
    AttributeMap attributes = entity.getAttributes();
    AttributeInstance armorAttribute = attributes.getInstance(Attributes.ARMOR);

    if (armorAttribute == null) {
      return;
    }

    armorAttribute.addOrUpdateTransientModifier(this.createAttributeModifier());
  }

  @Override
  public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
    ICurioItem.super.onUnequip(slotContext, newStack, stack);

    LivingEntity entity = slotContext.entity();
    AttributeMap attributes = entity.getAttributes();
    AttributeInstance armorAttribute = attributes.getInstance(Attributes.ARMOR);

    if (armorAttribute == null) {
      return;
    }

    armorAttribute.removeModifier(ARMOR_MODIFIER_RESOURCE);
  }

  private AttributeModifier createAttributeModifier() {
    return new AttributeModifier(
        ARMOR_MODIFIER_RESOURCE, ARMOR_INCREASE, AttributeModifier.Operation.ADD_VALUE);
  }
}
