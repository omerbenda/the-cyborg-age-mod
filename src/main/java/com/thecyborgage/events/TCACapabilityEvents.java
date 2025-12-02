package com.thecyborgage.events;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.data.CoreEnergyStorage;
import com.thecyborgage.init.TCAItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class TCACapabilityEvents {
  @SubscribeEvent
  public static void registerCapabilities(RegisterCapabilitiesEvent evt) {
    registerCurioItem(evt, TCAItems.CYBORG_CORE);
    registerCurioItem(evt, TCAItems.NIGHT_VISION_LENS);
    registerCurioItem(evt, TCAItems.CYBORG_LEG);
    registerCurioItem(evt, TCAItems.CYBORG_JUMP_LEG);
    registerCurioItem(evt, TCAItems.CYBORG_GENERATOR_LEG);
    registerCurioItem(evt, TCAItems.SOLAR_HAT);
    registerCurioItem(evt, TCAItems.THERMAL_GENERATOR);
    registerCurioItem(evt, TCAItems.CYBORG_VISOR);
    registerCurioItem(evt, TCAItems.PLAYER_RADAR);

    evt.registerItem(
        Capabilities.EnergyStorage.ITEM,
        (stack, context) -> new CoreEnergyStorage(stack),
        TCAItems.CYBORG_CORE);
  }

  private static void registerCurioItem(RegisterCapabilitiesEvent evt, ItemLike item) {
    evt.registerItem(
        CuriosCapability.ITEM,
        (stack, context) ->
            new ICurio() {
              @Override
              public ItemStack getStack() {
                return stack;
              }
            },
        item);
  }
}
