package com.thecyborgage.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class TCAKeybinds {
  public static final KeyMapping TOGGLE_CYBORG_JUMP_LEG =
      new KeyMapping(
          "key.thecyborgage.toggle_cyborg_jump_leg",
          KeyConflictContext.IN_GAME,
          InputConstants.Type.KEYSYM,
          GLFW.GLFW_KEY_V,
          "key.categories.thecyborgage");
}
