package com.thecyborgage.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class TCAKeybinds {
  public static final KeyMapping TOGGLE_CYBORG_JUMP_LEG =
      new KeyMapping(
          "key.thecyborgage.toggle_cyborg_jump_leg",
          KeyConflictContext.IN_GAME,
          InputConstants.Type.KEYSYM,
          GLFW.GLFW_KEY_UNKNOWN,
          "key.categories.thecyborgage");

  public static final KeyMapping TOGGLE_MAGNET_CHIP =
      new KeyMapping(
          "key.thecyborgage.toggle_magnet_chip",
          KeyConflictContext.IN_GAME,
          InputConstants.Type.KEYSYM,
          GLFW.GLFW_KEY_UNKNOWN,
          "key.categories.thecyborgage");

  public static final KeyMapping TRIGGER_PULSE_CHIP =
      new KeyMapping(
          "key.thecyborgage.trigger_pulse_chip",
          KeyConflictContext.IN_GAME,
          InputConstants.Type.KEYSYM,
          GLFW.GLFW_KEY_UNKNOWN,
          "key.categories.thecyborgage");

  public static final KeyMapping OPEN_TOGGLE_CIRCLE =
      new KeyMapping(
          "key.thecyborgage.open_toggle_circle",
          KeyConflictContext.IN_GAME,
          InputConstants.Type.KEYSYM,
          GLFW.GLFW_KEY_R,
          "key.categories.thecyborgage");

  public static boolean isPhysicallyDown(KeyMapping mapping) {
    long window = Minecraft.getInstance().getWindow().getWindow();
    InputConstants.Key key = mapping.getKey();

    if (key.getType() == InputConstants.Type.MOUSE) {
      return GLFW.glfwGetMouseButton(window, key.getValue()) == GLFW.GLFW_PRESS;
    }

    return GLFW.glfwGetKey(window, key.getValue()) == GLFW.GLFW_PRESS;
  }
}
