package com.thecyborgage.items;

import com.thecyborgage.init.TCADataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class NightVisionLens extends Item implements ICurioItem {
  private static final int ENERGY_USAGE = 10;

  public NightVisionLens(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);

    Optional<ICuriosItemHandler> optionalItemHandler =
        CuriosApi.getCuriosInventory(slotContext.entity());

    if (optionalItemHandler.isEmpty()) {
      return;
    }

    Optional<SlotResult> optionalSlotResult = optionalItemHandler.get().findCurio("core", 0);

    if (optionalSlotResult.isEmpty()) {
      return;
    }

    ItemStack coreItemStack = optionalSlotResult.get().stack();
    int coreEnergy = coreItemStack.getOrDefault(TCADataComponents.CORE_ENERGY, 0);

    if (coreEnergy >= ENERGY_USAGE) {
      slotContext.entity().addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0));
      coreItemStack.set(TCADataComponents.CORE_ENERGY, Math.max(coreEnergy - ENERGY_USAGE, 0));
    }
  }
}
