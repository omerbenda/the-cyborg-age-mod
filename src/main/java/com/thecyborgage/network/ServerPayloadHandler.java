package com.thecyborgage.network;

import com.thecyborgage.init.TCAAttachments;
import com.thecyborgage.network.packets.ToggleValuePayload;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
  public static void handleToggleValue(ToggleValuePayload payload, IPayloadContext context) {
    context.enqueueWork(
        () -> {
          Player player = context.player();
          boolean toggled = payload.toggled();

          if (payload.value() == ToggleValuePayload.ToggleValue.CYBORG_JUMP_LEG) {
            player.setData(TCAAttachments.CYBORG_JUMP_LEG_TOGGLE_STATE, toggled);
            player.sendSystemMessage(
                Component.translatable(
                        toggled
                            ? "thecyborgage.system.cyborg_jump_leg_state_enabled"
                            : "thecyborgage.system.cyborg_jump_leg_state_disabled")
                    .withStyle(ChatFormatting.GRAY));
          } else if (payload.value() == ToggleValuePayload.ToggleValue.MAGNET_CHIP) {
            player.setData(TCAAttachments.MAGNET_CHIP_TOGGLE_STATE, toggled);
            player.sendSystemMessage(
                Component.translatable(
                        toggled
                            ? "thecyborgage.system.magnet_chip_state_enabled"
                            : "thecyborgage.system.magnet_chip_state_disabled")
                    .withStyle(ChatFormatting.GRAY));
          }
        });
  }
}
