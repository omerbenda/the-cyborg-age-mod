package com.thecyborgage.network;

import com.thecyborgage.network.packets.PulseEffectPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
  private static Runnable pulseFlashCallback = () -> {};

  public static void setPulseFlashCallback(Runnable callback) {
    pulseFlashCallback = callback;
  }

  public static void handlePulseEffect(PulseEffectPayload payload, IPayloadContext context) {
    context.enqueueWork(pulseFlashCallback);
  }
}
