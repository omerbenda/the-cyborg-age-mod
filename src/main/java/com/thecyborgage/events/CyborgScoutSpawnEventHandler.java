package com.thecyborgage.events;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCAEntities;
import com.thecyborgage.init.TCASounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class CyborgScoutSpawnEventHandler {
  private static final int MIN_SPAWN_DISTANCE = 20;
  private static final int SPAWN_DISTANCE_ADDITION = 20;

  private static final Random random = new Random();
  private static int tickCounter = 0;

  @SubscribeEvent
  public static void onLevelTick(LevelTickEvent.Post evt) {
    Level level = evt.getLevel();

    if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) {
      return;
    }

    tickCounter++;

    if (tickCounter >= getSpawnInterval()) {
      tickCounter = 0;
      MinecraftServer server = serverLevel.getServer();

      if (isScoutSpawnEnabled(server)) {
        if (random.nextInt(getSpawnChance()) == 0) {
          ServerPlayer selectedPlayer = selectPlayer(server);

          if (selectedPlayer != null) {
            spawnAroundPlayer(selectedPlayer);
          }
        }
      }
    }
  }

  private static boolean isScoutSpawnEnabled(MinecraftServer server) {
    ServerLevel endLevel = server.getLevel(Level.END);

    if (endLevel == null) {
      return false;
    }

    EndDragonFight dragonFight = endLevel.getDragonFight();

    return dragonFight != null && dragonFight.hasPreviouslyKilledDragon();
  }

  @Nullable
  private static ServerPlayer selectPlayer(MinecraftServer server) {
    PlayerList playerList = server.getPlayerList();
    List<ServerPlayer> players =
        playerList.getPlayers().stream()
            .filter((player) -> !player.isCreative() && !player.isSpectator())
            .toList();

    if (players.isEmpty()) {
      return null;
    }

    return players.get(random.nextInt(players.size()));
  }

  private static void spawnAroundPlayer(ServerPlayer player) {
    Level level = player.level();

    if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) {
      return;
    }

    double distance = MIN_SPAWN_DISTANCE + random.nextInt(SPAWN_DISTANCE_ADDITION);
    double angle = random.nextDouble() * 2 * Math.PI;

    int offsetX = (int) (Math.cos(angle) * distance);
    int offsetZ = (int) (Math.sin(angle) * distance);

    BlockPos playerPos = player.blockPosition();
    BlockPos spawnPosXZ = playerPos.offset(offsetX, 0, offsetZ);

    BlockPos finalSpawnPos =
        serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, spawnPosXZ);

    TCAEntities.CYBORG_SCOUT.get().spawn(serverLevel, finalSpawnPos, MobSpawnType.EVENT);

    TheCyborgAgeMod.LOGGER.info(
        "Spawned a cyborg scout around " + player.getGameProfile().getName());

    level.playSound(
        null,
        finalSpawnPos,
        TCASounds.CYBORG_SCOUT_SPAWN.get(),
        SoundSource.HOSTILE,
        2.0F,
        0.9F + level.getRandom().nextFloat() * 0.2F);
  }

  private static int getSpawnInterval() {
    return TCAServerConfig.CONFIG.cyborgScoutSpawnInterval.getAsInt();
  }

  private static int getSpawnChance() {
    return TCAServerConfig.CONFIG.cyborgScoutSpawnChance.getAsInt();
  }
}
