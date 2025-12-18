package com.thecyborgage.menus.slots;

import com.thecyborgage.init.TCAItems;
import com.thecyborgage.menus.CoreWorkbenchMenu;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class CoreBatterySlot extends FilterSlot {
  private final CoreWorkbenchMenu menu;

  public CoreBatterySlot(CoreWorkbenchMenu menu, Container container, int slot, int x, int y) {
    super(TCAItems.CORE_BATTERY.get(), container, slot, x, y);

    this.menu = menu;
  }

  @Override
  public boolean mayPlace(ItemStack stack) {
    return super.mayPlace(stack) && menu.hasCore();
  }
}
