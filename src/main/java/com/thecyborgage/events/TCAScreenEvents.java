package com.thecyborgage.events;

import com.mojang.blaze3d.platform.Window;
import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
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
    Optional<IEnergyStorage> playerCoreEnergyStorage =
        TCACuriosHelper.getEntityCoreEnergyStorage(player);

    if (playerCoreEnergyStorage.isPresent()) {
      IEnergyStorage energyStorage = playerCoreEnergyStorage.get();

      GuiGraphics graphics = evt.getGuiGraphics();
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
  }
}
