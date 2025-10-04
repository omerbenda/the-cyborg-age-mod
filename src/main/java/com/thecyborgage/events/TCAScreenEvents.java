package com.thecyborgage.events;

import com.mojang.blaze3d.platform.Window;
import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.init.TCADataComponents;
import com.thecyborgage.init.TCAItems;
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

import java.util.Optional;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class TCAScreenEvents {
  @SubscribeEvent
  public static void onPostRenderGui(RenderGuiLayerEvent.Post evt) {
    Minecraft minecraft = Minecraft.getInstance();
    Player player = minecraft.player;
    GuiGraphics graphics = evt.getGuiGraphics();

    TCACuriosHelper.getEntityCurioItem(player, TCAItems.CYBORG_VISOR.get())
        .ifPresent((stack) -> renderVisor(graphics));
  }

  private static void renderVisor(GuiGraphics graphics) {
    renderSidebar(graphics);
    renderCoreEnergy(graphics);
  }

  private static void renderSidebar(GuiGraphics graphics) {
    Minecraft minecraft = Minecraft.getInstance();
    Window window = minecraft.getWindow();

    Optional<ItemStack> optionalPlayerRadar =
        TCACuriosHelper.getEntityCurioItem(minecraft.player, TCAItems.PLAYER_RADAR.get());

    if (optionalPlayerRadar.isEmpty()) {
      return;
    }

    ItemStack playerRadar = optionalPlayerRadar.get();
    String nearestPlayer = playerRadar.get(TCADataComponents.PLAYER_RADAR_NEAREST_PLAYER);

    if (nearestPlayer == null) {
      return;
    }

    Component nearestPlayerText =
        Component.translatable("thecyborgage.cyborg_visor.nearest_player", nearestPlayer);
    Font font = minecraft.font;

    graphics.drawString(
        font,
        nearestPlayerText,
        window.getGuiScaledWidth() - font.width(nearestPlayerText),
        0,
        0x00ff00);
  }

  private static void renderCoreEnergy(GuiGraphics graphics) {
    Minecraft minecraft = Minecraft.getInstance();
    Window window = minecraft.getWindow();
    Optional<IEnergyStorage> optionalEnergyStorage =
        TCACuriosHelper.getEntityCoreEnergyStorage(minecraft.player);

    if (optionalEnergyStorage.isEmpty()) {
      return;
    }

    IEnergyStorage energyStorage = optionalEnergyStorage.get();
    String text = String.valueOf(energyStorage.getEnergyStored());
    Font font = minecraft.font;

    graphics.drawString(
        font,
        text,
        window.getGuiScaledWidth() / 2 + 10,
        (window.getGuiScaledHeight() - font.lineHeight) / 2,
        0xffffff);
  }
}
