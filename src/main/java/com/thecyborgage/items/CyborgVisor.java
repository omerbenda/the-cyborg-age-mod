package com.thecyborgage.items;

import com.thecyborgage.init.TCADataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CyborgVisor extends Item implements ICurioItem {
  public CyborgVisor(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);

    LivingEntity entity = slotContext.entity();
    Level level = entity.level();

    if (!level.isClientSide()) {
      setNearestPlayer(entity, stack);
    }
  }

  private void setNearestPlayer(LivingEntity entity, ItemStack stack) {
    Optional<ServerPlayer> optionalNearestPlayer = getNearestPlayer(entity);

    if (optionalNearestPlayer.isEmpty()) {
      stack.remove(TCADataComponents.VISOR_NEAREST_PLAYER);

      return;
    }

    ServerPlayer nearestPlayer = optionalNearestPlayer.get();
    stack.set(TCADataComponents.VISOR_NEAREST_PLAYER, nearestPlayer.getName().getString());
  }

  private static Optional<ServerPlayer> getNearestPlayer(LivingEntity entity) {
    Level level = entity.level();

    if (level.isClientSide()) {
      throw new RuntimeException("getNearestPlayer should only be called from server side!");
    }

    ServerLevel serverLevel = (ServerLevel) level;
    List<ServerPlayer> players = serverLevel.players();

    return players.stream()
        .filter((player) -> !player.is(entity))
        .min(Comparator.comparingDouble(entity::distanceToSqr));
  }
}
