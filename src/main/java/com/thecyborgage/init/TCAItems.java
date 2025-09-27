package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.items.CyborgCoreItem;
import com.thecyborgage.items.CyborgLegItem;
import com.thecyborgage.items.NightVisionLensItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCAItems {
  public static final DeferredRegister.Items ITEMS =
      DeferredRegister.createItems(TheCyborgAgeMod.MOD_ID);

  public static final DeferredItem<CyborgCoreItem> CYBORG_CORE =
      ITEMS.registerItem("cyborg_core", (properties) -> new CyborgCoreItem(properties.stacksTo(1)));
  public static final DeferredItem<NightVisionLensItem> NIGHT_VISION_LENS =
      ITEMS.registerItem(
          "night_vision_lens", (properties) -> new NightVisionLensItem(properties.stacksTo(1)));
  public static final DeferredItem<CyborgLegItem> CYBORG_LEG =
      ITEMS.registerItem("cyborg_leg", (properties) -> new CyborgLegItem(properties.stacksTo(1)));

  public static void register(IEventBus bus) {
    ITEMS.register(bus);
  }
}
