package com.thecyborgage.events;

import com.mojang.blaze3d.platform.Window;
import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.init.TCADataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class TCAScreenEvents {
  @SubscribeEvent
  public static void onPostRenderGui(RenderGuiLayerEvent.Post evt) {
    Minecraft minecraft = Minecraft.getInstance();
    Player player = minecraft.player;
    GuiGraphics graphics = evt.getGuiGraphics();

    TCACuriosHelper.getEntityCoreEnergyStorage(player)
        .ifPresent((energyStorage) -> renderCoreEnergy(graphics, energyStorage));
    TCACuriosHelper.getEntityCyborgVisor(player).ifPresent((stack) -> renderVisor(graphics, stack));
  }

  private static void renderCoreEnergy(GuiGraphics graphics, IEnergyStorage energyStorage) {
    Minecraft minecraft = Minecraft.getInstance();
    Window window = minecraft.getWindow();
    String text = String.valueOf(energyStorage.getEnergyStored());
    Font font = minecraft.font;

    graphics.drawString(
        font,
        text,
        window.getGuiScaledWidth() / 2 + 8,
        (window.getGuiScaledHeight() - font.lineHeight) / 2,
        0xffffff);
  }

  private static void renderVisor(GuiGraphics graphics, ItemStack stack) {
    Minecraft minecraft = Minecraft.getInstance();
    Window window = minecraft.getWindow();
    String nearestPlayer = stack.get(TCADataComponents.VISOR_NEAREST_PLAYER);

    if (nearestPlayer == null) {
      return;
    }

    Component text =
        Component.translatable("thecyborgage.cyborg_visor.nearest_player", nearestPlayer);
    Font font = minecraft.font;

    graphics.drawString(font, text, window.getGuiScaledWidth() - font.width(text), 0, 0x00ff00);
  }
}
