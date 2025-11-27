package com.thecyborgage.client;

import com.mojang.blaze3d.platform.Window;
import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.config.TCAClientConfig;
import com.thecyborgage.enums.RenderLocation;
import com.thecyborgage.init.TCADataComponents;
import com.thecyborgage.init.TCAItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.Optional;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID, value = Dist.CLIENT)
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
    renderNearCrosshair(graphics);
  }

  private static void renderSidebar(GuiGraphics graphics) {
    final int textColor = 0x00ff00;

    Minecraft minecraft = Minecraft.getInstance();
    Window window = minecraft.getWindow();
    Player player = minecraft.player;
    Font font = minecraft.font;

    int drawHeight = 0;

    if (TCAClientConfig.CONFIG.coreEnergyRenderLocation.get() == RenderLocation.SIDEBAR) {
      Optional<IEnergyStorage> optionalEnergyStorage =
          TCACuriosHelper.getEntityCoreEnergyStorage(minecraft.player);

      if (optionalEnergyStorage.isPresent()) {
        IEnergyStorage energyStorage = optionalEnergyStorage.get();
        int energy = energyStorage.getEnergyStored();
        Component text =
            Component.translatable(
                "thecyborgage.cyborg_visor.core_energy", formatStringNumber(energy));

        graphics.drawString(
            font, text, window.getGuiScaledWidth() - font.width(text), drawHeight, textColor);

        drawHeight += font.lineHeight;
      }
    }

    Optional<ItemStack> optionalPlayerRadar =
        TCACuriosHelper.getEntityCurioItem(player, TCAItems.PLAYER_RADAR.get());

    if (optionalPlayerRadar.isPresent()) {
      ItemStack playerRadar = optionalPlayerRadar.get();
      String nearestPlayer = playerRadar.get(TCADataComponents.PLAYER_RADAR_NEAREST_PLAYER);

      if (nearestPlayer != null) {
        Component nearestPlayerText =
            Component.translatable("thecyborgage.cyborg_visor.nearest_player", nearestPlayer);

        graphics.drawString(
            font,
            nearestPlayerText,
            window.getGuiScaledWidth() - font.width(nearestPlayerText),
            drawHeight,
            textColor);

        drawHeight += font.lineHeight;
      }
    }

    Optional<ItemStack> optionalThermalGenerator =
        TCACuriosHelper.getEntityCurioItem(player, TCAItems.THERMAL_GENERATOR.get());

    if (optionalThermalGenerator.isPresent()) {
      Component temperatureText =
          Component.translatable(
              "thecyborgage.cyborg_visor.temperature",
              minecraft.level.getBiome(player.blockPosition()).value().getBaseTemperature());

      graphics.drawString(
          font,
          temperatureText,
          window.getGuiScaledWidth() - font.width(temperatureText),
          drawHeight,
          textColor);

      drawHeight += font.lineHeight;
    }
  }

  private static void renderNearCrosshair(GuiGraphics graphics) {
    Minecraft minecraft = Minecraft.getInstance();
    Window window = minecraft.getWindow();

    if (TCAClientConfig.CONFIG.coreEnergyRenderLocation.get() == RenderLocation.CROSSHAIR) {
      Optional<IEnergyStorage> optionalEnergyStorage =
          TCACuriosHelper.getEntityCoreEnergyStorage(minecraft.player);

      if (optionalEnergyStorage.isEmpty()) {
        return;
      }

      IEnergyStorage energyStorage = optionalEnergyStorage.get();
      int energy = energyStorage.getEnergyStored();
      String text = formatStringNumber(energy);

      Font font = minecraft.font;

      graphics.drawString(
          font,
          text,
          window.getGuiScaledWidth() / 2 + 10,
          (window.getGuiScaledHeight() - font.lineHeight) / 2,
          0xffffff);
    }
  }

  private static String formatStringNumber(int number) {
    if (number >= 1_000_000_000) {
      return String.format("%.2fB", number / 1_000_000_000F);
    } else if (number >= 1_000_000) {
      return String.format("%.2fM", number / 1_000_000F);
    } else if (number >= 1_000) {
      return String.format("%.2fK", number / 1_000F);
    }

    return String.valueOf(number);
  }
}
