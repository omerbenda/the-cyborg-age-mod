package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.items.NightVisionLens;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCAItems {
  public static final DeferredRegister.Items ITEMS =
      DeferredRegister.createItems(TheCyborgAgeMod.MOD_ID);

  public static final DeferredItem<NightVisionLens> NIGHT_VISION_LENS =
      ITEMS.registerItem("night_vision_lens", NightVisionLens::new);

  public static void register(IEventBus bus) {
    ITEMS.register(bus);
  }
}
