package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCASounds {
  public static final DeferredRegister<SoundEvent> SOUNDS =
      DeferredRegister.create(Registries.SOUND_EVENT, TheCyborgAgeMod.MOD_ID);

  public static final Supplier<SoundEvent> CYBORG_SCOUT_SPAWN =
      SOUNDS.register(
          "cyborg_scout_spawn",
          () ->
              SoundEvent.createVariableRangeEvent(
                  ResourceLocation.fromNamespaceAndPath(
                      TheCyborgAgeMod.MOD_ID, "cyborg_scout_spawn")));

  public static final Supplier<SoundEvent> CYBORG_BEACON_WAVE_START =
      SOUNDS.register(
          "cyborg_beacon_wave_start",
          () ->
              SoundEvent.createVariableRangeEvent(
                  ResourceLocation.fromNamespaceAndPath(
                      TheCyborgAgeMod.MOD_ID, "cyborg_beacon_wave_start")));

  public static final Supplier<SoundEvent> PULSE_CHIP_PULSE =
      SOUNDS.register(
          "pulse_chip_pulse",
          () ->
              SoundEvent.createVariableRangeEvent(
                  ResourceLocation.fromNamespaceAndPath(
                      TheCyborgAgeMod.MOD_ID, "pulse_chip_pulse")));

  public static void register(IEventBus bus) {
    SOUNDS.register(bus);
  }
}
