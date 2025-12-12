package com.thecyborgage.menus;

import com.thecyborgage.init.TCABlocks;
import com.thecyborgage.init.TCAMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CoreWorkbenchMenu extends AbstractContainerMenu {
  private final Inventory inventory;
  private final ContainerLevelAccess access;

  public CoreWorkbenchMenu(int containerId, Inventory inventory) {
    this(containerId, inventory, ContainerLevelAccess.NULL);
  }

  public CoreWorkbenchMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
    super(TCAMenuTypes.CORE_WORKBENCH_MENU.get(), containerId);

    this.inventory = inventory;
    this.access = access;

    this.addMenuSlots();
  }

  private void addMenuSlots() {
    for (int y = 0; y < 3; ++y) {
      for (int x = 0; x < 9; ++x) {
        this.addSlot(new Slot(this.inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
      }
    }

    for (int x = 0; x < 9; ++x) {
      this.addSlot(new Slot(this.inventory, x, 8 + x * 18, 142));
    }
  }

  @Override
  public ItemStack quickMoveStack(Player player, int i) {
    return null;
  }

  @Override
  public boolean stillValid(Player player) {
    return AbstractContainerMenu.stillValid(this.access, player, TCABlocks.CORE_WORKBENCH.get());
  }
}
