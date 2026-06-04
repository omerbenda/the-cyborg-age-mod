package com.thecyborgage.blocks.blockentities;

import com.thecyborgage.blocks.CyborgBeaconBlock;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCABlockEntities;
import com.thecyborgage.init.TCAEntities;
import com.thecyborgage.init.TCASounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CyborgBeaconBlockEntity extends BlockEntity {
  private static final int[] flashSequence = {0, 1, 2, 3, 2, 1};

  private int currentWave;
  private int ticksUntilNextWave;
  private final List<UUID> activeInvasionMobs;

  public CyborgBeaconBlockEntity(BlockPos pos, BlockState state) {
    super(TCABlockEntities.CYBORG_BEACON.get(), pos, state);

    this.currentWave = 1;
    this.ticksUntilNextWave = getWaveDelay();
    this.activeInvasionMobs = new ArrayList<>();
  }

  public static void tick(Level level, BlockPos pos, BlockState state, CyborgBeaconBlockEntity be) {
    if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) {
      return;
    }

    boolean removed =
        be.activeInvasionMobs.removeIf(
            uuid -> {
              Entity entity = serverLevel.getEntity(uuid);

              return entity == null || !entity.isAlive();
            });

    if (removed) {
      setChanged(level, pos, state);
    }

    if (!be.activeInvasionMobs.isEmpty()) {
      trySetFlashStage(level, pos, state, 0);

      return;
    }

    be.ticksUntilNextWave--;

    if (be.ticksUntilNextWave <= 0) {
      be.spawnWave(serverLevel);
      be.ticksUntilNextWave = getWaveDelay();
      be.currentWave++;

      if (be.currentWave > getWaveCount()) {
        level.destroyBlock(pos, false);

        return;
      }

      setChanged(level, pos, state);

      return;
    }

    int flashSeqIndex = ((getWaveDelay() - be.ticksUntilNextWave) / 5) % flashSequence.length;
    int flashStage = flashSequence[flashSeqIndex];
    trySetFlashStage(level, pos, state, flashStage);
  }

  private void spawnWave(ServerLevel level) {
    int spawnCount = getSpawnCount(this.currentWave);
    BlockPos pos = this.getBlockPos();

    for (int i = 0; i < spawnCount; i++) {
      double angle = level.random.nextDouble() * Math.PI * 2;
      int distance = 10 + level.random.nextInt(10);
      int spawnX = pos.getX() + (int) (Math.cos(angle) * distance);
      int spawnZ = pos.getZ() + (int) (Math.sin(angle) * distance);
      int spawnY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, spawnX, spawnZ);

      BlockPos spawnPos = new BlockPos(spawnX, spawnY, spawnZ);

      Mob mob = TCAEntities.CYBORG.get().create(level);

      if (mob != null) {
        mob.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0, 0);
        mob.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.EVENT, null);
        level.addFreshEntity(mob);

        activeInvasionMobs.add(mob.getUUID());
      }
    }

    level.playSound(
        null,
        pos,
        TCASounds.CYBORG_BEACON_WAVE_START.get(),
        SoundSource.BLOCKS,
        2.0F,
        0.9F + level.getRandom().nextFloat() * 0.2F);
  }

  private static void trySetFlashStage(Level level, BlockPos pos, BlockState state, int stage) {
    if (state.getValue(CyborgBeaconBlock.FLASH_STAGE) != stage) {
      level.setBlock(pos, state.setValue(CyborgBeaconBlock.FLASH_STAGE, stage), 3);
    }
  }

  private static int getSpawnCount(int waveNum) {
    return waveNum * getCyborgCount();
  }

  @Override
  protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.saveAdditional(tag, registries);
    tag.putInt("CurrentWave", this.currentWave);
    tag.putInt("TicksUntilNextWave", this.ticksUntilNextWave);

    ListTag uuidList = new ListTag();

    for (UUID uuid : this.activeInvasionMobs) {
      uuidList.add(NbtUtils.createUUID(uuid));
    }

    tag.put("ActiveInvasionMobs", uuidList);
  }

  @Override
  protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.loadAdditional(tag, registries);
    this.currentWave = tag.getInt("CurrentWave");
    this.ticksUntilNextWave = tag.getInt("TicksUntilNextWave");

    this.activeInvasionMobs.clear();
    if (tag.contains("ActiveInvasionMobs", Tag.TAG_LIST)) {
      ListTag uuidList = tag.getList("ActiveInvasionMobs", Tag.TAG_INT_ARRAY);

      for (Tag value : uuidList) {
        this.activeInvasionMobs.add(NbtUtils.loadUUID(value));
      }
    }
  }

  private static int getWaveCount() {
    return TCAServerConfig.CONFIG.cyborgBeaconWaveCount.getAsInt();
  }

  private static int getWaveDelay() {
    return TCAServerConfig.CONFIG.cyborgBeaconWaveDelay.getAsInt();
  }

  private static int getCyborgCount() {
    return TCAServerConfig.CONFIG.cyborgBeaconCyborgsCount.getAsInt();
  }
}
