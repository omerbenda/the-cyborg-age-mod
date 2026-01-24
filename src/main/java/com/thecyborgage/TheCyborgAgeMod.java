package com.thecyborgage;

import com.thecyborgage.config.TCAClientConfig;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.*;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(TheCyborgAgeMod.MOD_ID)
public class TheCyborgAgeMod {
  public static final String MOD_ID = "thecyborgage";
  public static final Logger LOGGER = LogUtils.getLogger();

  public TheCyborgAgeMod(IEventBus modEventBus, ModContainer modContainer) {
    TCADataComponents.register(modEventBus);
    TCAAttachments.register(modEventBus);
    TCAItems.register(modEventBus);
    TCABlocks.register(modEventBus);
    TCACreativeModeTabs.register(modEventBus);
    TCAEntities.register(modEventBus);
    TCAMenuTypes.register(modEventBus);

    modContainer.registerConfig(ModConfig.Type.SERVER, TCAServerConfig.CONFIG_SPEC);
    modContainer.registerConfig(ModConfig.Type.CLIENT, TCAClientConfig.CONFIG_SPEC);
  }
}
