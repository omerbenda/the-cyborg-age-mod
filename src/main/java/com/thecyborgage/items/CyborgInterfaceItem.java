package com.thecyborgage.items;

import com.thecyborgage.TCACuriosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.Optional;

public class CyborgInterfaceItem extends Item {
  public CyborgInterfaceItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    BlockPos pos = context.getClickedPos();
    Direction direction = context.getClickedFace();

    IEnergyStorage blockEnergyStorage =
        level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, direction);

    if (blockEnergyStorage == null) {
      return super.useOn(context);
    }

    Player player = context.getPlayer();
    Optional<IEnergyStorage> optionalCoreEnergyStorage =
        TCACuriosHelper.getEntityCoreEnergyStorage(player);

    if (optionalCoreEnergyStorage.isEmpty()) {
      return super.useOn(context);
    }

    IEnergyStorage coreEnergyStorage = optionalCoreEnergyStorage.get();

    int recValue =
        blockEnergyStorage.receiveEnergy(coreEnergyStorage.extractEnergy(100, true), true);

    blockEnergyStorage.receiveEnergy(coreEnergyStorage.extractEnergy(recValue, false), false);

    return InteractionResult.SUCCESS;
  }
}
