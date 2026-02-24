package com.thecyborgage.events;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.entities.CyborgEntity;
import com.thecyborgage.init.TCAEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class EntityInitEventHandler {
  @SubscribeEvent
  public static void onAttributeCreate(EntityAttributeCreationEvent event) {
    event.put(TCAEntities.CYBORG.get(), Monster.createMonsterAttributes().build());
  }

  @SubscribeEvent
  public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent evt) {
    evt.register(
        TCAEntities.CYBORG.get(),
        SpawnPlacementTypes.ON_GROUND,
        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
        CyborgEntity::checkMobSpawnRules,
        RegisterSpawnPlacementsEvent.Operation.REPLACE);
  }
}
