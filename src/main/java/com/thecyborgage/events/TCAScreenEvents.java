package com.thecyborgage.events;

import com.mojang.blaze3d.platform.Window;
import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.items.CyborgCoreItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import java.util.Optional;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class TCAScreenEvents {
  @SubscribeEvent
  public static void onPostRenderGui(RenderGuiLayerEvent.Post evt) {
    Minecraft minecraft = Minecraft.getInstance();
    Player player = minecraft.player;
    Optional<ItemStack> playerCoreItem = TCACuriosHelper.getEntityCoreItemStack(player);

    if (playerCoreItem.isPresent()) {
      ItemStack coreItemStack = playerCoreItem.get();
      int energy = CyborgCoreItem.getCoreEnergy(coreItemStack);

      GuiGraphics graphics = evt.getGuiGraphics();
      Window window = minecraft.getWindow();
      String text = String.valueOf(energy);
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
