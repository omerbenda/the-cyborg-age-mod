package com.thecyborgage.blocks;

import com.thecyborgage.menus.CoreWorkbenchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CoreWorkbenchBlock extends Block {
  public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

  public CoreWorkbenchBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);

    builder.add(FACING);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    return this.defaultBlockState()
        .setValue(FACING, context.getHorizontalDirection().getOpposite());
  }

  @Override
  protected InteractionResult useWithoutItem(
      BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
    if (level.isClientSide()) {
      return InteractionResult.SUCCESS;
    }

    player.openMenu(state.getMenuProvider(level, pos));

    return InteractionResult.CONSUME;
  }

  @Nullable
  @Override
  protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
    return new SimpleMenuProvider(
        (id, inventory, player) ->
            new CoreWorkbenchMenu(id, inventory, ContainerLevelAccess.create(level, pos)),
        Component.translatable("menu.title.thecyborgage.core_workbench"));
  }

  @Override
  public BlockState rotate(BlockState state, Rotation rotation) {
    return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, Mirror mirror) {
    return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
  }
}
