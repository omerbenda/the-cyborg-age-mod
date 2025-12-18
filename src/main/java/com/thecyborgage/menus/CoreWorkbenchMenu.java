package com.thecyborgage.menus;

import com.thecyborgage.init.TCABlocks;
import com.thecyborgage.init.TCADataComponents;
import com.thecyborgage.init.TCAItems;
import com.thecyborgage.init.TCAMenuTypes;
import com.thecyborgage.menus.slots.CoreBatterySlot;
import com.thecyborgage.menus.slots.FilterSlot;
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
  private final SimpleContainer coreContainer;
  private final SimpleContainer coreInventory;

  public CoreWorkbenchMenu(int containerId, Inventory inventory) {
    this(containerId, inventory, ContainerLevelAccess.NULL);
  }

  public CoreWorkbenchMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
    super(TCAMenuTypes.CORE_WORKBENCH_MENU.get(), containerId);

    this.inventory = inventory;
    this.access = access;
    this.coreContainer = new SimpleContainer(1);
    this.coreContainer.addListener(this::onCoreContainerChanged);
    this.coreInventory = new SimpleContainer(4);
    this.coreInventory.addListener(this::onCoreInventoryChanged);

    this.addWorkbenchSlots();
    this.addInventorySlots();
  }

  private void addWorkbenchSlots() {
    this.addSlot(new FilterSlot(TCAItems.CYBORG_CORE.get(), this.coreContainer, 0, 80, 35));

    this.addSlot(new CoreBatterySlot(this, this.coreInventory, 0, 41, 21));
    this.addSlot(new CoreBatterySlot(this, this.coreInventory, 1, 41, 49));

    this.addSlot(new CoreBatterySlot(this, this.coreInventory, 2, 119, 21));
    this.addSlot(new CoreBatterySlot(this, this.coreInventory, 3, 119, 49));
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

  private void onCoreContainerChanged(Container container) {
    ItemStack coreSlotStack = this.coreContainer.getItem(0);

    if (coreSlotStack.isEmpty()) {
      this.coreInventory.clearContent();
      return;
    }

    ItemContainerContents contents = coreSlotStack.get(TCADataComponents.ENERGY_ITEM_STORAGE);

    if (contents == null) {
      return;
    }

    int slotIndex = 0;
    for (ItemStack itemStack : contents.nonEmptyItemsCopy()) {
      this.coreInventory.setItem(slotIndex, itemStack);
      slotIndex++;
    }
  }

  private void onCoreInventoryChanged(Container container) {
    ItemStack coreSlotStack = this.coreContainer.getItem(0);

    if (coreSlotStack.isEmpty()) {
      return;
    }

    ItemContainerContents newContents =
        ItemContainerContents.fromItems(this.coreInventory.getItems().stream().limit(4).toList());
    coreSlotStack.set(TCADataComponents.ENERGY_ITEM_STORAGE, newContents);
  }

  public boolean hasCore() {
    return !this.coreContainer.isEmpty();
  }

  @Override
  public ItemStack quickMoveStack(Player player, int i) {
    return null;
  }

  @Override
  public boolean stillValid(Player player) {
    return AbstractContainerMenu.stillValid(this.access, player, TCABlocks.CORE_WORKBENCH.get());
  }

  @Override
  public void removed(Player player) {
    super.removed(player);
    this.access.execute((pLevel, pPos) -> this.clearContainer(player, this.coreContainer));
  }
}
