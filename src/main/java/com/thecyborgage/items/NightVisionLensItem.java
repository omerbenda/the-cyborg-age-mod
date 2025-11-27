package com.thecyborgage.items;

import com.thecyborgage.TCAConfig;
import com.thecyborgage.TCACuriosHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class NightVisionLensItem extends Item implements ICurioItem {
  public NightVisionLensItem(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);
    LivingEntity entity = slotContext.entity();

    if (TCACuriosHelper.consumeEntityCoreEnergy(
        entity, TCAConfig.CONFIG.nightVisionLensDischargeRate.getAsInt())) {
      entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0));
    }
  }
}
