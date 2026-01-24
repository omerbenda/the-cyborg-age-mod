package com.thecyborgage.items;

import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.init.TCADataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PlayerRadarItem extends Item implements ICurioItem {
  public PlayerRadarItem(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);

    LivingEntity entity = slotContext.entity();
    Level level = entity.level();

    if (!level.isClientSide()) {
      int ticks = stack.getOrDefault(TCADataComponents.PLAYER_RADAR_TICK_COUNTER, 0);
      int tickRate = TCAServerConfig.CONFIG.playerRadarSearchTickRate.getAsInt();

      if (ticks >= tickRate) {
        setNearestPlayer(entity, stack);
        stack.set(TCADataComponents.PLAYER_RADAR_TICK_COUNTER, 0);
      } else if (TCACuriosHelper.consumeEntityCoreEnergy(
          entity, TCAServerConfig.CONFIG.playerRadarDischargeRate.getAsInt())) {
        stack.set(TCADataComponents.PLAYER_RADAR_TICK_COUNTER, Math.min(ticks + 1, tickRate));
      }
    }
  }

  private static void setNearestPlayer(LivingEntity entity, ItemStack stack) {
    Optional<ServerPlayer> optionalNearestPlayer = getNearestPlayer(entity);

    if (optionalNearestPlayer.isEmpty()) {
      stack.remove(TCADataComponents.PLAYER_RADAR_NEAREST_PLAYER);

      return;
    }

    ServerPlayer nearestPlayer = optionalNearestPlayer.get();
    stack.set(TCADataComponents.PLAYER_RADAR_NEAREST_PLAYER, nearestPlayer.getName().getString());
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

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

    tooltipComponents.add(
            Component.translatable("thecyborgage.player_radar.tooltip").withStyle(ChatFormatting.GRAY));

    String nearestPlayer = stack.get(TCADataComponents.PLAYER_RADAR_NEAREST_PLAYER);

    if (nearestPlayer != null) {
      tooltipComponents.add(
          Component.translatable(
              "thecyborgage.player_radar.nearest_player_tooltip", nearestPlayer).withStyle(ChatFormatting.GRAY));
    }
  }
}
