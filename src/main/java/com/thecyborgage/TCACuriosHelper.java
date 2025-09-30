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
  public static boolean addEntityCoreEnergy(LivingEntity entity, int energy) {
    return addEntityCoreEnergy(entity, energy, false);
  }

  public static boolean addEntityCoreEnergy(LivingEntity entity, int energy, boolean simulate) {
    Optional<IEnergyStorage> optionalEnergyStorage =
        TCACuriosHelper.getEntityCoreEnergyStorage(entity);

    if (optionalEnergyStorage.isEmpty()) {
      return false;
    }

    IEnergyStorage coreEnergyStorage = optionalEnergyStorage.get();

    return coreEnergyStorage.receiveEnergy(energy, simulate) != 0;
  }

  public static boolean consumeEntityCoreEnergy(LivingEntity entity, int energy) {
    return consumeEntityCoreEnergy(entity, energy, false);
  }

  public static boolean consumeEntityCoreEnergy(LivingEntity entity, int energy, boolean simulate) {
    Optional<IEnergyStorage> optionalEnergyStorage =
        TCACuriosHelper.getEntityCoreEnergyStorage(entity);

    if (optionalEnergyStorage.isEmpty()) {
      return false;
    }

    IEnergyStorage coreEnergyStorage = optionalEnergyStorage.get();

    return coreEnergyStorage.extractEnergy(energy, simulate) != 0;
  }

  public static Optional<IEnergyStorage> getEntityCoreEnergyStorage(LivingEntity entity) {
    return getEntityCoreItemStack(entity)
        .map((stack) -> stack.getCapability(Capabilities.EnergyStorage.ITEM));
  }

  private static Optional<ItemStack> getEntityCoreItemStack(LivingEntity entity) {
    Optional<ICuriosItemHandler> optionalItemHandler = CuriosApi.getCuriosInventory(entity);

    if (optionalItemHandler.isEmpty()) {
      return Optional.empty();
    }

    Optional<SlotResult> optionalCoreSlot = optionalItemHandler.get().findCurio("core", 0);

    return optionalCoreSlot.map(SlotResult::stack);
  }
}
