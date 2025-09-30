package com.thecyborgage.events;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.data.CoreEnergyStorage;
import com.thecyborgage.init.TCAItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class TCACapabilityEvents {
  @SubscribeEvent
  public static void registerCapabilities(RegisterCapabilitiesEvent evt) {
    evt.registerItem(
        Capabilities.EnergyStorage.ITEM,
        (stack, context) -> new CoreEnergyStorage(stack),
        TCAItems.CYBORG_CORE);
  }
}
