package com.thecyborgage.client.events;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.client.screens.CoreWorkbenchScreen;
import com.thecyborgage.init.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID, value = Dist.CLIENT)
public class ScreenInitClientEventHandler {
  @SubscribeEvent
  public static void onRegisterMenuScreens(RegisterMenuScreensEvent evt) {
    evt.register(TCAMenuTypes.CORE_WORKBENCH_MENU.get(), CoreWorkbenchScreen::new);
  }
}
