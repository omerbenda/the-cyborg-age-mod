package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCACreativeModeTabs {
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheCyborgAgeMod.MOD_ID);

  public static final Supplier<CreativeModeTab> THE_CYBORG_AGE_TAB =
      CREATIVE_MODE_TABS.register(
          "the_cyborg_age_tab",
          () ->
              CreativeModeTab.builder()
                  .title(Component.translatable("itemGroup.thecyborgage.the_cyborg_age_tab"))
                  .icon(() -> new ItemStack(TCAItems.CYBORG_CORE.get()))
                  .displayItems(
                      (param, output) -> {
                        output.accept(TCAItems.CYBORG_CORE);
                        output.accept(TCAItems.NIGHT_VISION_LENS);
                        output.accept(TCAItems.CYBORG_LEG);
                        output.accept(TCAItems.CYBORG_GENERATOR_LEG);
                        output.accept(TCAItems.SOLAR_HAT);
                        output.accept(TCAItems.THERMAL_GENERATOR);
                      })
                  .build());

  public static void register(IEventBus bus) {
    CREATIVE_MODE_TABS.register(bus);
  }
}
