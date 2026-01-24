package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.items.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.stream.Stream;

public class TCAItems {
  public static final DeferredRegister.Items ITEMS =
      DeferredRegister.createItems(TheCyborgAgeMod.MOD_ID);

  public static final DeferredItem<Item> CYBORG_FRAGMENT =
      ITEMS.registerSimpleItem("cyborg_fragment");

  public static final DeferredItem<Item> CORE_INTEGRATION_CIRCUIT =
      ITEMS.registerItem(
          "core_integration_circuit",
          (properties) ->
              new CurioSlotUnlockerItem(
                  properties,
                  "core",
                  ResourceLocation.fromNamespaceAndPath(
                      TheCyborgAgeMod.MOD_ID, "core_integration_circuit"),
                  1,
                  AttributeModifier.Operation.ADD_VALUE) {
                @Override
                public void appendHoverText(
                    ItemStack stack,
                    TooltipContext context,
                    List<Component> tooltipComponents,
                    TooltipFlag tooltipFlag) {
                  super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

                  tooltipComponents.add(
                      Component.translatable("thecyborgage.core_integration_circuit.tooltip")
                          .withStyle(ChatFormatting.GRAY));
                }
              });
  public static final DeferredItem<CyborgCoreItem> CYBORG_CORE =
      ITEMS.registerItem(
          "cyborg_core",
          (properties) ->
              new CyborgCoreItem(
                  properties
                      .stacksTo(1)
                      .component(
                          DataComponents.CONTAINER,
                          ItemContainerContents.fromItems(
                              Stream.generate(() -> ItemStack.EMPTY).limit(4).toList()))));
  public static final DeferredItem<Item> CORE_BATTERY =
      ITEMS.registerItem(
          "core_battery",
          (properties ->
              new Item(properties.stacksTo(1)) {
                @Override
                public void appendHoverText(
                    ItemStack stack,
                    TooltipContext context,
                    List<Component> tooltipComponents,
                    TooltipFlag tooltipFlag) {
                  super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

                  tooltipComponents.add(
                      Component.translatable("thecyborgage.core_battery.tooltip")
                          .withStyle(ChatFormatting.GRAY));
                }
              }));
  public static final DeferredItem<NightVisionLensItem> NIGHT_VISION_LENS =
      ITEMS.registerItem(
          "night_vision_lens", (properties) -> new NightVisionLensItem(properties.stacksTo(1)));
  public static final DeferredItem<CyborgLegItem> CYBORG_LEG =
      ITEMS.registerItem("cyborg_leg", (properties) -> new CyborgLegItem(properties.stacksTo(1)));
  public static final DeferredItem<CyborgJumpLegItem> CYBORG_JUMP_LEG =
      ITEMS.registerItem(
          "cyborg_jump_leg", (properties -> new CyborgJumpLegItem(properties.stacksTo(1))));
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
  public static final DeferredItem<EnergyArmorItem> ENERGY_ARMOR =
      ITEMS.registerItem(
          "energy_armor", (properties -> new EnergyArmorItem(properties.stacksTo(1))));

  public static final DeferredItem<BlockItem> CORE_WORKBENCH =
      ITEMS.registerSimpleBlockItem(TCABlocks.CORE_WORKBENCH);

  public static void register(IEventBus bus) {
    ITEMS.register(bus);
  }
}
