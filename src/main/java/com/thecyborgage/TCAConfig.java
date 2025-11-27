package com.thecyborgage;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class TCAConfig {
  public static final TCAConfig CONFIG;
  public static final ModConfigSpec CONFIG_SPEC;

  static {
    Pair<TCAConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(TCAConfig::new);

    CONFIG = pair.getLeft();
    CONFIG_SPEC = pair.getRight();
  }

  public final ModConfigSpec.IntValue cyborgCoreMaxEnergy;
  public final ModConfigSpec.IntValue generatorLegChargeRate;
  public final ModConfigSpec.DoubleValue cyborgLegSpeedBoost;
  public final ModConfigSpec.IntValue cyborgLegDischargeRate;
  public final ModConfigSpec.IntValue solarHatChargeRate;
  public final ModConfigSpec.IntValue playerRadarSearchTickRate;
  public final ModConfigSpec.IntValue playerRadarDischargeRate;
  public final ModConfigSpec.IntValue nightVisionLensDischargeRate;
  public final ModConfigSpec.DoubleValue thermalGeneratorTempCoefficient;
  public final ModConfigSpec.DoubleValue thermalGeneratorRainCoefficient;
  public final ModConfigSpec.IntValue energyArmorArmorValue;
  public final ModConfigSpec.IntValue energyArmorDischargeRate;
  public final ModConfigSpec.IntValue energyArmorHitDischarge;

  public TCAConfig(ModConfigSpec.Builder builder) {
    builder.push("items");

    builder.push("cyborg_core");

    this.cyborgCoreMaxEnergy =
        builder.defineInRange("cyborg_core_max_energy", 10_000, 0, Integer.MAX_VALUE);

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

    builder.push("solar_hat");

    this.solarHatChargeRate =
        builder.defineInRange("solar_hat_charge_rate", 50, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.push("player_radar");

    this.playerRadarSearchTickRate =
        builder.defineInRange("player_radar_search_rate", 20, 1, Integer.MAX_VALUE);

    this.playerRadarDischargeRate =
        builder.defineInRange("player_radar_discharge_rate", 25, 0, Integer.MAX_VALUE);

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

    builder.pop();
  }
}
