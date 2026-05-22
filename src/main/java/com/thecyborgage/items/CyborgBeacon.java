package com.thecyborgage.items;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.init.TCAEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CyborgBeacon extends Item {
  public CyborgBeacon(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(
      Level level, Player player, InteractionHand usedHand) {
    ItemStack itemStack = player.getItemInHand(usedHand);

    if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) {
      return InteractionResultHolder.success(itemStack);
    }

    Entity beaconEntity =
        TCAEntities.CYBORG_BEACON
            .get()
            .spawn(serverLevel, player.blockPosition(), MobSpawnType.TRIGGERED);

    if (beaconEntity == null) {
      TheCyborgAgeMod.LOGGER.error("Could not create Cyborg Beacon entity.");

      return InteractionResultHolder.fail(itemStack);
    }

    itemStack.consume(1, player);

    return InteractionResultHolder.consume(itemStack);
  }
}
