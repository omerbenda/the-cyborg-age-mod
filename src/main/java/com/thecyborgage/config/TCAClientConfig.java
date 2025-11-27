package com.thecyborgage.config;

import com.thecyborgage.enums.RenderLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class TCAClientConfig {
  public static final TCAClientConfig CONFIG;
  public static final ModConfigSpec CONFIG_SPEC;

  static {
    Pair<TCAClientConfig, ModConfigSpec> pair =
        new ModConfigSpec.Builder().configure(TCAClientConfig::new);

    CONFIG = pair.getLeft();
    CONFIG_SPEC = pair.getRight();
  }

  public final ModConfigSpec.EnumValue<RenderLocation> coreEnergyRenderLocation;

  public TCAClientConfig(ModConfigSpec.Builder builder) {
    builder.push("items");

    this.coreEnergyRenderLocation =
        builder.defineEnum("core_energy_render_location", RenderLocation.CROSSHAIR);

    builder.pop();
  }
}
