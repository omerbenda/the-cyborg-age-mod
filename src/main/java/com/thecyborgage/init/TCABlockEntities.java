package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.blocks.blockentities.CyborgBeaconBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCABlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
      DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TheCyborgAgeMod.MOD_ID);

  public static final Supplier<BlockEntityType<CyborgBeaconBlockEntity>> CYBORG_BEACON =
      BLOCK_ENTITY_TYPES.register(
          "cyborg_beacon",
          () ->
              BlockEntityType.Builder.of(
                      CyborgBeaconBlockEntity::new, TCABlocks.CYBORG_BEACON.get())
                  .build(null));

  public static void register(IEventBus bus) {
    BLOCK_ENTITY_TYPES.register(bus);
  }
}
