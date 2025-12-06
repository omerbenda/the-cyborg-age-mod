package com.thecyborgage.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class CurioSlotUnlockerItem extends Item {
  private final String slot;
  private final ResourceLocation modifierResLoc;
  private final double amount;
  private final AttributeModifier.Operation operation;

  public CurioSlotUnlockerItem(
      Properties properties,
      String slot,
      ResourceLocation modifierResLoc,
      double amount,
      AttributeModifier.Operation operation) {
    super(properties);

    this.slot = slot;
    this.modifierResLoc = modifierResLoc;
    this.amount = amount;
    this.operation = operation;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(
      Level level, Player player, InteractionHand usedHand) {
    Optional<ICuriosItemHandler> optionalCurioInventory = CuriosApi.getCuriosInventory(player);

    if (optionalCurioInventory.isEmpty()) {
      return super.use(level, player, usedHand);
    }

    ICuriosItemHandler curioInventory = optionalCurioInventory.get();

    boolean exists =
        curioInventory.getModifiers().get(this.slot).stream()
            .anyMatch((modifier) -> modifier.is(this.modifierResLoc));

    curioInventory.addPermanentSlotModifier(
        this.slot, this.modifierResLoc, this.amount, this.operation);

    ItemStack usedItemStack = player.getItemInHand(usedHand);

    if (!exists) {
      usedItemStack.consume(1, player);
    }

    return InteractionResultHolder.consume(usedItemStack);
  }
}
