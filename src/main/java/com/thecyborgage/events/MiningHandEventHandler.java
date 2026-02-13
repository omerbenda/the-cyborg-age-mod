package com.thecyborgage.events;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCAItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class MiningHandEventHandler {
  @SubscribeEvent
  public static void onBlockBreak(BlockEvent.BreakEvent evt) {
    Player player = evt.getPlayer();

    if (shouldUseMiningHand(player)) {
      TCACuriosHelper.consumeEntityCoreEnergy(player, getEnergyCost(player));
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
      // Increase speed to wood speed if increasing harvest tier to wooden
      float originalSpeed = evt.getOriginalSpeed();
      float woodSpeed = Tiers.WOOD.getSpeed();
      evt.setNewSpeed(originalSpeed * woodSpeed);
    }
  }

  private static boolean shouldUseMiningHand(Player player) {
    return hasMiningHandHarvest(player)
        && TCACuriosHelper.consumeEntityCoreEnergy(player, getEnergyCost(player), true);
  }

  private static boolean shouldUseMiningHandHarvest(Player player, BlockState blockState) {
    return TCAServerConfig.CONFIG.miningHandIncreaseHarvest.getAsBoolean()
        && !player.getMainHandItem().isCorrectToolForDrops(blockState)
        && !blockState.is(Tiers.WOOD.getIncorrectBlocksForDrops())
        && !ItemStack.EMPTY.isCorrectToolForDrops(blockState);
  }

  private static boolean hasMiningHandHarvest(Player player) {
    return TCACuriosHelper.getEntityCurioItem(player, TCAItems.MINING_HAND.get()).isPresent();
  }

  private static int getEnergyCost(LivingEntity entity) {
    Optional<List<ItemStack>> optionalMiningHandList =
        TCACuriosHelper.getEntityCurioItemList(entity, TCAItems.MINING_HAND.get());

    if (optionalMiningHandList.isEmpty()) {
      throw new RuntimeException("Can't get energy cost for entity with no mining hand items");
    }

    return TCAServerConfig.CONFIG.miningHandDischarge.getAsInt()
        * optionalMiningHandList.get().size();
  }
}
