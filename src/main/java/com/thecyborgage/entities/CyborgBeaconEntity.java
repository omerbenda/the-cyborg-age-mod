package com.thecyborgage.entities;

import com.thecyborgage.init.TCAEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CyborgBeaconEntity extends Entity {
  private static final int MAX_WAVES = 3;
  private static final int WAVE_DELAY = 160;

  private static final EntityDataAccessor<Boolean> IS_FLASHING =
      SynchedEntityData.defineId(CyborgBeaconEntity.class, EntityDataSerializers.BOOLEAN);

  private int currentWave;
  private int ticksUntilNextSpawn;
  private int flashTicks = 0;
  private final List<UUID> activeInvasionMobs;

  public CyborgBeaconEntity(EntityType<?> entityType, Level level) {
    super(entityType, level);

    this.noPhysics = false;
    this.currentWave = 1;
    this.ticksUntilNextSpawn = WAVE_DELAY;
    this.activeInvasionMobs = new ArrayList<>();
  }

  @Override
  public void tick() {
    super.tick();

    Level level = this.level();

    if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
      if (this.flashTicks > 0) {
        this.flashTicks--;

        if (this.flashTicks == 0) {
          this.entityData.set(IS_FLASHING, false);
        }
      }

      this.setDeltaMovement(0, 0, 0);

      activeInvasionMobs.removeIf(
          uuid -> {
            Entity entity = serverLevel.getEntity(uuid);

            return entity == null || !entity.isAlive();
          });

      if (this.activeInvasionMobs.isEmpty() && this.ticksUntilNextSpawn <= 0) {
        if (this.currentWave < MAX_WAVES) {
          this.currentWave++;
          this.ticksUntilNextSpawn = WAVE_DELAY;
        } else {
          this.discard();

          return;
        }
      }

      if (this.ticksUntilNextSpawn > 0) {
        this.ticksUntilNextSpawn--;

        if (this.ticksUntilNextSpawn == 0) {
          this.spawnWave(serverLevel);
        }
      }
    }
  }

  private void spawnWave(ServerLevel level) {
    this.flashTicks = 60;
    this.entityData.set(IS_FLASHING, true);

    int spawnCount = currentWave * 3;

    for (int i = 0; i < spawnCount; i++) {
      double angle = level.random.nextDouble() * Math.PI * 2;
      int distance = 15 + level.random.nextInt(10);
      int spawnX = (int) this.getX() + (int) (Math.cos(angle) * distance);
      int spawnZ = (int) this.getZ() + (int) (Math.sin(angle) * distance);
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
  }

  @Override
  public boolean hurt(DamageSource source, float amount) {
    return false;
  }

  @Override
  public boolean isPickable() {
    return true;
  }

  @Override
  public boolean isPushable() {
    return false;
  }

  public boolean isFlashing() {
    return this.entityData.get(IS_FLASHING);
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    builder.define(IS_FLASHING, false);
  }

  @Override
  protected void readAdditionalSaveData(CompoundTag tag) {
    this.currentWave = tag.getInt("CurrentWave");
    this.ticksUntilNextSpawn = tag.getInt("TicksUntilNextSpawn");
  }

  @Override
  protected void addAdditionalSaveData(CompoundTag tag) {
    tag.putInt("CurrentWave", this.currentWave);
    tag.putInt("TicksUntilNextSpawn", this.ticksUntilNextSpawn);
  }
}
