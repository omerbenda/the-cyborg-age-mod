package com.thecyborgage;

import com.thecyborgage.init.TCACreativeModeTabs;
import com.thecyborgage.init.TCADataComponents;
import com.thecyborgage.init.TCAItems;
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
    TCAItems.register(modEventBus);
    TCACreativeModeTabs.register(modEventBus);
  }
}
