package com.thecyborgage.events;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.network.ServerPayloadHandler;
import com.thecyborgage.network.packets.TriggerActionPayload;
import com.thecyborgage.network.packets.ToggleValuePayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class NetworkEventHandler {
  @SubscribeEvent
  public static void registerPayloads(RegisterPayloadHandlersEvent evt) {
    PayloadRegistrar registrar = evt.registrar("1");

    registrar.playToServer(
        ToggleValuePayload.TYPE,
        ToggleValuePayload.STREAM_CODEC,
        ServerPayloadHandler::handleToggleValue);

    registrar.playToServer(
        TriggerActionPayload.TYPE,
        TriggerActionPayload.STREAM_CODEC,
        ServerPayloadHandler::handleTriggerAction);
  }
}
