package com.thecyborgage.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class TCAServerConfig {
  public static final TCAServerConfig CONFIG;
  public static final ModConfigSpec CONFIG_SPEC;

  static {
    Pair<TCAServerConfig, ModConfigSpec> pair =
        new ModConfigSpec.Builder().configure(TCAServerConfig::new);

    CONFIG = pair.getLeft();
    CONFIG_SPEC = pair.getRight();
  }

  public final ModConfigSpec.IntValue cyborgCoreMaxEnergy;
  public final ModConfigSpec.IntValue coreBatteryStorage;
  public final ModConfigSpec.IntValue generatorLegChargeRate;
  public final ModConfigSpec.DoubleValue cyborgLegSpeedBoost;
  public final ModConfigSpec.IntValue cyborgLegDischargeRate;
  public final ModConfigSpec.DoubleValue cyborgJumpLegJumpValue;
  public final ModConfigSpec.IntValue cyborgJumpLegJumpDischarge;
  public final ModConfigSpec.IntValue cyborgJumpLegDischargeRate;
  public final ModConfigSpec.IntValue solarHatChargeRate;
  public final ModConfigSpec.IntValue playerRadarSearchTickRate;
  public final ModConfigSpec.IntValue playerRadarDischargeRate;
  public final ModConfigSpec.DoubleValue playerRadarRange;
  public final ModConfigSpec.IntValue nightVisionLensDischargeRate;
  public final ModConfigSpec.DoubleValue thermalGeneratorTempCoefficient;
  public final ModConfigSpec.DoubleValue thermalGeneratorRainCoefficient;
  public final ModConfigSpec.IntValue energyArmorArmorValue;
  public final ModConfigSpec.IntValue energyArmorDischargeRate;
  public final ModConfigSpec.IntValue energyArmorHitDischarge;
  public final ModConfigSpec.IntValue activeCamouflageDischargeRate;
  public final ModConfigSpec.IntValue miningHandDischarge;
  public final ModConfigSpec.DoubleValue miningHandValue;
  public final ModConfigSpec.BooleanValue miningHandIncreaseHarvest;
  public final ModConfigSpec.IntValue metabolicChipEatDischarge;
  public final ModConfigSpec.IntValue metabolicChipHungerBonus;
  public final ModConfigSpec.IntValue magnetChipDischargeRate;
  public final ModConfigSpec.DoubleValue magnetChipRange;
  public final ModConfigSpec.IntValue pulseChipEnergyCost;
  public final ModConfigSpec.DoubleValue pulseChipRadius;
  public final ModConfigSpec.DoubleValue pulseChipStrength;
  public final ModConfigSpec.IntValue pulseChipCooldown;
  public final ModConfigSpec.IntValue cyborgBeaconWaveCount;
  public final ModConfigSpec.IntValue cyborgBeaconWaveDelay;
  public final ModConfigSpec.IntValue cyborgBeaconCyborgsCount;
  public final ModConfigSpec.IntValue cyborgScoutSpawnInterval;
  public final ModConfigSpec.IntValue cyborgScoutSpawnChance;

  public TCAServerConfig(ModConfigSpec.Builder builder) {
    builder.push("items");

    builder.push("cyborg_core");

    this.cyborgCoreMaxEnergy =
        builder.defineInRange("cyborg_core_max_energy", 10_000, 0, Integer.MAX_VALUE);

    this.coreBatteryStorage =
        builder.defineInRange("core_battery_storage", 10_000, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.push("cyborg_generator_leg");

    this.generatorLegChargeRate =
        builder.defineInRange("cyborg_generator_leg_charge_rate", 100, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.push("cyborg_leg");

    this.cyborgLegSpeedBoost =
        builder.defineInRange("cyborg_leg_speed_boost", 0.5D, 0D, Integer.MAX_VALUE);

    this.cyborgLegDischargeRate =
        builder.defineInRange("cyborg_leg_discharge_rate", 50, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.push("cyborg_jump_leg");

    this.cyborgJumpLegJumpValue =
        builder.defineInRange("cyborg_jump_leg_jump_value", 0.6D, 0.0D, Double.MAX_VALUE);

    this.cyborgJumpLegJumpDischarge =
        builder.defineInRange("cyborg_jump_leg_jump_discharge", 350, 0, Integer.MAX_VALUE);

    this.cyborgJumpLegDischargeRate =
        builder.defineInRange("cyborg_jump_leg_discharge_rate", 0, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.push("solar_hat");

    this.solarHatChargeRate =
        builder.defineInRange("solar_hat_charge_rate", 50, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.push("player_radar");

    this.playerRadarSearchTickRate =
        builder.defineInRange("player_radar_search_rate", 20, 1, Integer.MAX_VALUE);

    this.playerRadarDischargeRate =
        builder.defineInRange("player_radar_discharge_rate", 25, 0, Integer.MAX_VALUE);

    this.playerRadarRange =
        builder
            .comment("Maximum range in blocks at which the Player Radar detects players. Set to -1 for infinite range.")
            .defineInRange("player_radar_range", 5000.0D, -1.0D, Double.MAX_VALUE);

    builder.pop();

    builder.push("night_vision_lens");

    this.nightVisionLensDischargeRate =
        builder.defineInRange("night_vision_lens_discharge_rate", 10, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.push("thermal_generator");

    this.thermalGeneratorTempCoefficient =
        builder.defineInRange(
            "thermal_generator_temperature_coefficient", 2.0D, 0.0D, Double.MAX_VALUE);

    this.thermalGeneratorRainCoefficient =
        builder.defineInRange("thermal_generator_rain_coefficient", 0.2D, 0.0D, Double.MAX_VALUE);

    builder.pop();

    builder.push("energy_armor");

    this.energyArmorArmorValue =
        builder.defineInRange("energy_armor_armor_value", 6, 0, Integer.MAX_VALUE);

    this.energyArmorDischargeRate =
        builder.defineInRange("energy_armor_discharge_rate", 5, 0, Integer.MAX_VALUE);

    this.energyArmorHitDischarge =
        builder.defineInRange("energy_armor_hit_discharge", 1000, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.push("active_camouflage");

    this.activeCamouflageDischargeRate =
        builder.defineInRange("active_camouflage_discharge_rate", 100, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.push("mining_hand");

    this.miningHandDischarge =
        builder.defineInRange("mining_hand_discharge", 100, 0, Integer.MAX_VALUE);

    this.miningHandValue = builder.defineInRange("mining_hand_value", 1.5D, 0.0D, Double.MAX_VALUE);

    this.miningHandIncreaseHarvest = builder.define("mining_hand_increase_harvest", true);

    builder.pop();

    builder.push("metabolic_chip");

    this.metabolicChipEatDischarge =
        builder.defineInRange("metabolic_chip_eat_discharge", 1500, 0, Integer.MAX_VALUE);

    this.metabolicChipHungerBonus =
        builder
            .comment("Number of half-shanks added to food when the Metabolic Chip is equipped.")
            .defineInRange("metabolic_chip_hunger_bonus", 1, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.push("magnet_chip");

    this.magnetChipDischargeRate =
        builder
            .comment("Energy drained per tick while the Magnet Chip is attracting nearby items.")
            .defineInRange("magnet_chip_discharge_rate", 10, 0, Integer.MAX_VALUE);

    this.magnetChipRange =
        builder
            .comment("Radius in blocks within which the Magnet Chip attracts items.")
            .defineInRange("magnet_chip_range", 8.0D, 0.0D, Double.MAX_VALUE);

    builder.pop();

    builder.push("pulse_chip");

    this.pulseChipEnergyCost =
        builder
            .comment("Energy consumed per pulse activation.")
            .defineInRange("pulse_chip_energy_cost", 2000, 0, Integer.MAX_VALUE);

    this.pulseChipRadius =
        builder
            .comment("Radius in blocks within which the Pulse Chip pushes entities.")
            .defineInRange("pulse_chip_radius", 8.0D, 0.0D, Double.MAX_VALUE);

    this.pulseChipStrength =
        builder
            .comment("Knockback strength of the pulse.")
            .defineInRange("pulse_chip_strength", 1.5D, 0.0D, Double.MAX_VALUE);

    this.pulseChipCooldown =
        builder
            .comment("Cooldown in ticks between pulse activations.")
            .defineInRange("pulse_chip_cooldown", 100, 1, Integer.MAX_VALUE);

    builder.pop();

    builder.pop();

    builder.push("blocks");

    builder.push("cyborg_beacon");

    this.cyborgBeaconWaveCount =
        builder.defineInRange("cyborg_beacon_wave_count", 3, 1, Integer.MAX_VALUE);

    this.cyborgBeaconWaveDelay =
        builder
            .comment("The delay in ticks between waves.")
            .defineInRange("cyborg_beacon_wave_delay", 160, 1, Integer.MAX_VALUE);

    this.cyborgBeaconCyborgsCount =
        builder
            .comment("The scale of cyborgs spawned in each wave.")
            .defineInRange("cyborg_beacon_cyborg_count", 3, 1, Integer.MAX_VALUE);

    builder.pop();

    builder.pop();

    builder.push("entities");

    builder.push("cyborg_scout");

    this.cyborgScoutSpawnInterval =
        builder
            .comment(
                "The interval in ticks of rolling for spawning a cyborg scout, only after the Ender Dragon was killed.")
            .defineInRange("cyborg_scout_spawn_interval", 24000, 1, Integer.MAX_VALUE);

    this.cyborgScoutSpawnChance =
        builder
            .comment("The roll made for spawning a cyborg scout around a random player.")
            .defineInRange("cyborg_scout_spawn_chance", 3, 1, Integer.MAX_VALUE);

    builder.pop();

    builder.pop();
  }
}
