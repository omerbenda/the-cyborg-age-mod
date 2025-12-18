package com.thecyborgage.menus.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FilterSlot extends Slot {
  private final Item item;

  public FilterSlot(Item item, Container container, int slot, int x, int y) {
    super(container, slot, x, y);

    this.item = item;
  }

  @Override
  public boolean mayPlace(ItemStack stack) {
    return stack.is(this.item);
  }
}
