package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.items.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCAItems {
  public static final DeferredRegister.Items ITEMS =
      DeferredRegister.createItems(TheCyborgAgeMod.MOD_ID);

  public static final DeferredItem<Item> CYBORG_FRAGMENT =
      ITEMS.registerSimpleItem("cyborg_fragment");
  public static final DeferredItem<CyborgCoreItem> CYBORG_CORE =
      ITEMS.registerItem("cyborg_core", (properties) -> new CyborgCoreItem(properties.stacksTo(1)));
  public static final DeferredItem<NightVisionLensItem> NIGHT_VISION_LENS =
      ITEMS.registerItem(
          "night_vision_lens", (properties) -> new NightVisionLensItem(properties.stacksTo(1)));
  public static final DeferredItem<CyborgLegItem> CYBORG_LEG =
      ITEMS.registerItem("cyborg_leg", (properties) -> new CyborgLegItem(properties.stacksTo(1)));
  public static final DeferredItem<CyborgGeneratorLegItem> CYBORG_GENERATOR_LEG =
      ITEMS.registerItem(
          "cyborg_generator_leg",
          (properties -> new CyborgGeneratorLegItem(properties.stacksTo(1))));
  public static final DeferredItem<SolarHatItem> SOLAR_HAT =
      ITEMS.registerItem("solar_hat", (properties -> new SolarHatItem(properties.stacksTo(1))));
  public static final DeferredItem<ThermalGeneratorItem> THERMAL_GENERATOR =
      ITEMS.registerItem(
          "thermal_generator", (properties -> new ThermalGeneratorItem(properties.stacksTo(1))));
  public static final DeferredItem<Item> CYBORG_VISOR =
      ITEMS.registerSimpleItem("cyborg_visor", new Item.Properties().stacksTo(1));
  public static final DeferredItem<PlayerRadarItem> PLAYER_RADAR =
      ITEMS.registerItem(
          "player_radar", (properties -> new PlayerRadarItem(properties.stacksTo(1))));
  public static final DeferredItem<AttributeCyborgItem> ENERGY_ARMOR =
      ITEMS.registerItem(
          "energy_armor",
          (properties ->
              new AttributeCyborgItem(
                  properties.stacksTo(1),
                  ResourceLocation.fromNamespaceAndPath(
                      TheCyborgAgeMod.MOD_ID, "attribute.energy_armor.armor_modifier"),
                  Attributes.ARMOR,
                  6,
                  AttributeModifier.Operation.ADD_VALUE,
                  5)));

  public static void register(IEventBus bus) {
    ITEMS.register(bus);
  }
}
