package com.thecyborgage.menus;

import com.thecyborgage.init.TCABlocks;
import com.thecyborgage.init.TCADataComponents;
import com.thecyborgage.init.TCAMenuTypes;
import com.thecyborgage.items.CyborgCoreItem;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class CoreWorkbenchMenu extends AbstractContainerMenu {
  private final Inventory inventory;
  private final ContainerLevelAccess access;
  private final SimpleContainer coreInventory;
  private boolean hasSetContents;

  public CoreWorkbenchMenu(int containerId, Inventory inventory) {
    this(containerId, inventory, ContainerLevelAccess.NULL);
  }

  public CoreWorkbenchMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
    super(TCAMenuTypes.CORE_WORKBENCH_MENU.get(), containerId);

    this.hasSetContents = false;
    this.inventory = inventory;
    this.access = access;
    this.coreInventory = new SimpleContainer(5);
    this.coreInventory.addListener(this::onCoreInventoryChanged);

    this.addWorkbenchSlots();
    this.addInventorySlots();
  }

  private void addWorkbenchSlots() {
    this.addSlot(new Slot(this.coreInventory, 0, 80, 35));

    this.addSlot(new Slot(this.coreInventory, 1, 41, 21));
    this.addSlot(new Slot(this.coreInventory, 2, 41, 49));

    this.addSlot(new Slot(this.coreInventory, 3, 119, 21));
    this.addSlot(new Slot(this.coreInventory, 4, 119, 49));
  }

  private void addInventorySlots() {
    for (int y = 0; y < 3; ++y) {
      for (int x = 0; x < 9; ++x) {
        this.addSlot(new Slot(this.inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
      }
    }

    for (int x = 0; x < 9; ++x) {
      this.addSlot(new Slot(this.inventory, x, 8 + x * 18, 142));
    }
  }

  private void onCoreInventoryChanged(Container container) {
    ItemStack coreSlotStack = this.coreInventory.getItem(0);

    if (coreSlotStack.isEmpty() || !(coreSlotStack.getItem() instanceof CyborgCoreItem)) {
      this.hasSetContents = false;
      return;
    }

    ItemContainerContents contents = coreSlotStack.get(TCADataComponents.ENERGY_ITEM_STORAGE);

    if (contents == null || this.hasSetContents) {
      return;
    }

    this.hasSetContents = true;
    int slotIndex = 1;

    for (ItemStack itemStack : contents.nonEmptyItemsCopy()) {
      this.coreInventory.setItem(slotIndex, itemStack);
      slotIndex++;
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
