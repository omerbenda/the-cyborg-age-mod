package com.thecyborgage.client.events;

import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID, value = Dist.CLIENT)
public class PulseChipClientEffectHandler {
  private static int flashTicks = 0;
  private static final int FLASH_DURATION = 15;
  private static boolean renderedThisFrame = false;

  public static void triggerFlash() {
    flashTicks = FLASH_DURATION;
  }

  @SubscribeEvent
  public static void onClientTick(ClientTickEvent.Post evt) {
    renderedThisFrame = false;
    if (flashTicks > 0) {
      flashTicks--;
    }
  }

  @SubscribeEvent
  public static void onRenderGuiLayer(RenderGuiLayerEvent.Post evt) {
    if (flashTicks <= 0 || renderedThisFrame) {
      return;
    }

    renderedThisFrame = true;

    Minecraft mc = Minecraft.getInstance();
    GuiGraphics graphics = evt.getGuiGraphics();
    int width = mc.getWindow().getGuiScaledWidth();
    int height = mc.getWindow().getGuiScaledHeight();

    float progress = (float) flashTicks / FLASH_DURATION;
    int alpha = (int) (progress * 110);
    int color = (alpha << 24) | 0x55BBFF;

    graphics.fill(0, 0, width, height, color);
  }
}
