package com.thecyborgage.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thecyborgage.blocks.blockentities.CyborgBeaconBlockEntity;
import com.thecyborgage.init.TCABlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CyborgBeaconBlock extends BaseEntityBlock {
  public static final MapCodec<CyborgBeaconBlock> CODEC =
      RecordCodecBuilder.mapCodec(
          instance -> instance.group(propertiesCodec()).apply(instance, CyborgBeaconBlock::new));

  public static final IntegerProperty FLASH_STAGE = IntegerProperty.create("flash_stage", 0, 3);

  private static final VoxelShape ROD_SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D);
  private static final VoxelShape BULB_SHAPE = Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D);
  private static final VoxelShape COMPLETE_SHAPE = Shapes.or(ROD_SHAPE, BULB_SHAPE);

  public CyborgBeaconBlock(Properties properties) {
    super(properties);

    this.registerDefaultState(this.stateDefinition.any().setValue(FLASH_STAGE, 0));
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FLASH_STAGE);
  }

  @Override
  protected VoxelShape getShape(
      BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return COMPLETE_SHAPE;
  }

  @Override
  public RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new CyborgBeaconBlockEntity(pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
      Level level, BlockState state, BlockEntityType<T> type) {
    return level.isClientSide()
        ? null
        : createTickerHelper(
            type, TCABlockEntities.CYBORG_BEACON.get(), CyborgBeaconBlockEntity::tick);
  }
}
