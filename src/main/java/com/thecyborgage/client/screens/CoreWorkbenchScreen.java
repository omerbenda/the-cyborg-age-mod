package com.thecyborgage.client.screens;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.menus.CoreWorkbenchMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CoreWorkbenchScreen extends AbstractContainerScreen<CoreWorkbenchMenu> {
  private static final ResourceLocation BACKGROUND_TEXTURE =
      ResourceLocation.fromNamespaceAndPath(
          TheCyborgAgeMod.MOD_ID, "textures/gui/container/core_workbench.png");

  public CoreWorkbenchScreen(CoreWorkbenchMenu menu, Inventory playerInventory, Component title) {
    super(menu, playerInventory, title);
  }

  @Override
  protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
    int x = this.leftPos;
    int y = (this.height - this.imageHeight) / 2;
    graphics.blit(BACKGROUND_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
  }
}
