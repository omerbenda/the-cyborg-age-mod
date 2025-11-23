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

  public TCAConfig(ModConfigSpec.Builder builder) {
    builder.push("items");

    builder.push("cyborg_core");

    this.cyborgCoreMaxEnergy =
        builder
            .translation("thecyborgage.configuration.cyborg_core.max_energy")
            .defineInRange("max_energy", 10_000, 0, Integer.MAX_VALUE);

    builder.pop();

    builder.pop();
  }
}
