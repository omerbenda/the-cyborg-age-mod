package com.thecyborgage;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
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
    int coreEnergy = coreItemStack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored();

    return coreEnergy >= energy;
  }

  public static boolean tryConsumeEntityCoreEnergy(LivingEntity entity, int energy) {
    Optional<ItemStack> optionalCoreItemStack = getEntityCoreItemStack(entity);

    if (optionalCoreItemStack.isEmpty()) {
      return false;
    }

    ItemStack coreItemStack = optionalCoreItemStack.get();
    IEnergyStorage coreEnergyStorage = coreItemStack.getCapability(Capabilities.EnergyStorage.ITEM);

    if (coreEnergyStorage == null || coreEnergyStorage.getEnergyStored() < energy) {
      return false;
    }

    coreEnergyStorage.extractEnergy(energy, false);

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
