package com.thecyborgage.data;

import com.thecyborgage.init.TCADataComponents;
import com.thecyborgage.items.CyborgCoreItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class CoreEnergyStorage implements IEnergyStorage {
  private final ItemStack coreStack;
  private final CyborgCoreItem coreItem;

  public CoreEnergyStorage(ItemStack coreStack) {
    this.coreStack = coreStack;

    if (!(coreStack.getItem() instanceof CyborgCoreItem castedCoreItem)) {
      throw new RuntimeException();
    }

    this.coreItem = castedCoreItem;
  }

  private int getStackEnergy() {
    return this.coreStack.getOrDefault(TCADataComponents.CORE_ENERGY, 0);
  }

  private void setStackEnergy(int energy) {
    this.coreStack.set(TCADataComponents.CORE_ENERGY, energy);
  }

  @Override
  public int receiveEnergy(int energy, boolean simulate) {
    int stackEnergy = this.getStackEnergy();
    int maxEnergy = this.getMaxEnergyStored();
    int toReceive = Math.min(energy, maxEnergy - stackEnergy);

    if (!simulate && toReceive > 0) {
      this.setStackEnergy(stackEnergy + toReceive);
    }

    return toReceive;
  }

  @Override
  public int extractEnergy(int energy, boolean simulate) {
    int stackEnergy = this.getStackEnergy();

    if (energy > stackEnergy) {
      return -1;
    }

    if (!simulate) {
      this.setStackEnergy(stackEnergy - energy);
    }

    return energy;
  }

  @Override
  public int getEnergyStored() {
    return this.getStackEnergy();
  }

  @Override
  public int getMaxEnergyStored() {
    return this.coreItem.getMaxEnergy();
  }

  @Override
  public boolean canExtract() {
    return true;
  }

  @Override
  public boolean canReceive() {
    return true;
  }
}
