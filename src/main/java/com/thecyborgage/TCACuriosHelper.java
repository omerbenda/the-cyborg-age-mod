package com.thecyborgage;

import com.thecyborgage.init.TCADataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class TCACuriosHelper {
  public static boolean canConsumeEntityCoreEnergy(LivingEntity entity, int energy) {
    Optional<ItemStack> optionalCoreItemStack = getEntityCoreItemStack(entity);

    if (optionalCoreItemStack.isEmpty()) {
      return false;
    }

    ItemStack coreItemStack = optionalCoreItemStack.get();
    int coreEnergy = coreItemStack.getOrDefault(TCADataComponents.CORE_ENERGY, 0);

    return coreEnergy >= energy;
  }

  public static boolean tryConsumeEntityCoreEnergy(LivingEntity entity, int energy) {
    Optional<ItemStack> optionalCoreItemStack = getEntityCoreItemStack(entity);

    if (optionalCoreItemStack.isEmpty()) {
      return false;
    }

    ItemStack coreItemStack = optionalCoreItemStack.get();
    int coreEnergy = coreItemStack.getOrDefault(TCADataComponents.CORE_ENERGY, 0);

    if (coreEnergy < energy) {
      return false;
    }

    coreItemStack.set(TCADataComponents.CORE_ENERGY, Math.max(coreEnergy - energy, 0));

    return true;
  }

  public static Optional<ItemStack> getEntityCoreItemStack(LivingEntity entity) {
    Optional<ICuriosItemHandler> optionalItemHandler = CuriosApi.getCuriosInventory(entity);

    if (optionalItemHandler.isEmpty()) {
      return Optional.empty();
    }

    Optional<SlotResult> optionalCoreSlot = optionalItemHandler.get().findCurio("core", 0);

    return optionalCoreSlot.map(SlotResult::stack);
  }
}
