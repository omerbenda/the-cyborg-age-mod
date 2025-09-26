package com.thecyborgage.events;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.init.TCAItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class TCACuriosEvents {
  @SubscribeEvent
  public static void registerCapabilities(RegisterCapabilitiesEvent evt) {
    registerItem(evt, TCAItems.CYBORG_CORE);
    registerItem(evt, TCAItems.NIGHT_VISION_LENS);
    registerItem(evt, TCAItems.CYBORG_LEG);
  }

  private static void registerItem(RegisterCapabilitiesEvent evt, ItemLike item) {
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
