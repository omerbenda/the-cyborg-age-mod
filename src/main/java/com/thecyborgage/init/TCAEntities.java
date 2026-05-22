package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.entities.CyborgBeaconEntity;
import com.thecyborgage.entities.CyborgEntity;
import com.thecyborgage.entities.CyborgScoutEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCAEntities {
  public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
      DeferredRegister.create(Registries.ENTITY_TYPE, TheCyborgAgeMod.MOD_ID);

  public static final Supplier<EntityType<CyborgEntity>> CYBORG =
      ENTITY_TYPES.register(
          "cyborg",
          (res) ->
              EntityType.Builder.of(CyborgEntity::new, MobCategory.MONSTER)
                  .sized(0.6F, 1.8F)
                  .build(res.toString()));

  public static final Supplier<EntityType<CyborgScoutEntity>> CYBORG_SCOUT =
      ENTITY_TYPES.register(
          "cyborg_scout",
          (res) ->
              EntityType.Builder.of(CyborgScoutEntity::new, MobCategory.MONSTER)
                  .sized(0.6F, 1.8F)
                  .build(res.toString()));

  public static final Supplier<EntityType<CyborgBeaconEntity>> CYBORG_BEACON =
      ENTITY_TYPES.register(
          "cyborg_beacon",
          (res) ->
              EntityType.Builder.of(CyborgBeaconEntity::new, MobCategory.MISC)
                  .sized(0.5F, 0.5F)
                  .build(res.toString()));

  public static void register(IEventBus bus) {
    ENTITY_TYPES.register(bus);
  }
}
