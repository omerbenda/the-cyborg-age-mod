package com.thecyborgage.network.packets;

import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PulseEffectPayload() implements CustomPacketPayload {
  public static final Type<PulseEffectPayload> TYPE =
      new Type<>(ResourceLocation.fromNamespaceAndPath(TheCyborgAgeMod.MOD_ID, "pulse_effect"));

  public static final StreamCodec<FriendlyByteBuf, PulseEffectPayload> STREAM_CODEC =
      StreamCodec.of((buf, payload) -> {}, buf -> new PulseEffectPayload());

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
