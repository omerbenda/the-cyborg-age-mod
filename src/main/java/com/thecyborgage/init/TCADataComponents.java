package com.thecyborgage.init;

import com.mojang.serialization.Codec;
import com.thecyborgage.TheCyborgAgeMod;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCADataComponents {
  public static final DeferredRegister.DataComponents DATA_COMPONENTS =
      DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, TheCyborgAgeMod.MOD_ID);

  public static final Supplier<DataComponentType<Integer>> CORE_ENERGY =
      DATA_COMPONENTS.registerComponentType(
          "core_energy",
          builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

  public static final Supplier<DataComponentType<ItemContainerContents>> ENERGY_ITEM_STORAGE =
      DATA_COMPONENTS.registerComponentType(
          "energy_item_storage",
          builder ->
              builder
                  .persistent(ItemContainerContents.CODEC)
                  .networkSynchronized(ItemContainerContents.STREAM_CODEC));

  public static final Supplier<DataComponentType<String>> PLAYER_RADAR_NEAREST_PLAYER =
      DATA_COMPONENTS.registerComponentType(
          "player_radar_nearest_player",
          builder ->
              builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));
  public static final Supplier<DataComponentType<Integer>> PLAYER_RADAR_TICK_COUNTER =
      DATA_COMPONENTS.registerComponentType(
          "player_radar_tick_counter", builder -> builder.persistent(Codec.INT));

  public static void register(IEventBus bus) {
    DATA_COMPONENTS.register(bus);
  }
}
