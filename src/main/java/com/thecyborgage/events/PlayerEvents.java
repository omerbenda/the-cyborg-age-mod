package com.thecyborgage.events;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.init.TCAItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class PlayerEvents {
  public static final int MINING_HAND_MINE_DISCHARGE = 100; // TODO: Move to config

  @SubscribeEvent
  public static void onBlockBreak(BlockEvent.BreakEvent evt) {
    Player player = evt.getPlayer();

    if (shouldUseMiningHand(player)) {
      TCACuriosHelper.consumeEntityCoreEnergy(player, MINING_HAND_MINE_DISCHARGE);
    }
  }

  @SubscribeEvent
  public static void onHarvestCheck(PlayerEvent.HarvestCheck evt) {
    Player player = evt.getEntity();
    BlockState state = evt.getTargetBlock();

    if (shouldUseMiningHand(player) && shouldUseMiningHandHarvest(player, state)) {
      evt.setCanHarvest(true);
    }
  }

  @SubscribeEvent
  public static void onBreakSpeed(PlayerEvent.BreakSpeed evt) {
    Player player = evt.getEntity();
    BlockState state = evt.getState();

    if (shouldUseMiningHand(player) && shouldUseMiningHandHarvest(player, state)) {
      float originalSpeed = evt.getOriginalSpeed();
      float woodSpeed = Tiers.WOOD.getSpeed();
      evt.setNewSpeed(originalSpeed * woodSpeed);
    }
  }

  private static boolean shouldUseMiningHand(Player player) {
    return hasMiningHandHarvest(player)
        && TCACuriosHelper.consumeEntityCoreEnergy(player, MINING_HAND_MINE_DISCHARGE, true);
  }

  private static boolean shouldUseMiningHandHarvest(Player player, BlockState blockState) {
    return !player.getMainHandItem().isCorrectToolForDrops(blockState)
        && !blockState.is(Tiers.WOOD.getIncorrectBlocksForDrops())
        && !ItemStack.EMPTY.isCorrectToolForDrops(blockState);
  }

  private static boolean hasMiningHandHarvest(Player player) {
    return TCACuriosHelper.getEntityCurioItem(player, TCAItems.MINING_HAND.get()).isPresent();
  }
}
