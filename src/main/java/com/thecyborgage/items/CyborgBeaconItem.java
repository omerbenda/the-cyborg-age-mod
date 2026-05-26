package com.thecyborgage.items;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.init.TCAEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CyborgBeaconItem extends Item {
  public CyborgBeaconItem(Properties properties) {
    super(properties);
  }

  @Override
  public @NotNull InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();

    BlockPos placePos = context.getClickedPos().relative(context.getClickedFace());

    if (level.getBlockState(placePos.below()).isAir()) {
      return InteractionResult.PASS;
    }

    if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) {
      return InteractionResult.SUCCESS;
    }

    Entity beaconEntity =
        TCAEntities.CYBORG_BEACON.get().spawn(serverLevel, placePos, MobSpawnType.TRIGGERED);

    if (beaconEntity == null) {
      TheCyborgAgeMod.LOGGER.error("Could not create Cyborg Beacon entity.");

      return InteractionResult.FAIL;
    }

    ItemStack itemStack = context.getItemInHand();
    itemStack.consume(1, context.getPlayer());

    return InteractionResult.CONSUME;
  }
}
