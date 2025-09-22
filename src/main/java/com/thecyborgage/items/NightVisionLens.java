package com.thecyborgage.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class NightVisionLens extends Item implements ICurioItem {
  public NightVisionLens(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);

    slotContext.entity().addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 0));
  }
}
