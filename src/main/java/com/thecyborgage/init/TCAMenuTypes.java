package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.menus.CoreWorkbenchMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCAMenuTypes {
  public static final DeferredRegister<MenuType<?>> MENU_TYPES =
      DeferredRegister.create(BuiltInRegistries.MENU, TheCyborgAgeMod.MOD_ID);

  public static final Supplier<MenuType<CoreWorkbenchMenu>> CORE_WORKBENCH_MENU =
      MENU_TYPES.register(
          "core_workbench",
          () -> new MenuType<>(CoreWorkbenchMenu::new, FeatureFlags.DEFAULT_FLAGS));

  public static void register(IEventBus bus) {
    MENU_TYPES.register(bus);
  }
}
