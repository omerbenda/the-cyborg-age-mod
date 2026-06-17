package com.thecyborgage.network.packets;

import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record TriggerActionPayload(TriggerAction action) implements CustomPacketPayload {
  public static final Type<TriggerActionPayload> TYPE =
      new Type<>(ResourceLocation.fromNamespaceAndPath(TheCyborgAgeMod.MOD_ID, "trigger_action"));

  public static final StreamCodec<FriendlyByteBuf, TriggerActionPayload> STREAM_CODEC =
      StreamCodec.of(TriggerActionPayload::encode, TriggerActionPayload::decode);

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  private static void encode(FriendlyByteBuf buffer, TriggerActionPayload payload) {
    buffer.writeVarInt(payload.action().ordinal());
  }

  private static TriggerActionPayload decode(FriendlyByteBuf buffer) {
    return new TriggerActionPayload(TriggerAction.values()[buffer.readVarInt()]);
  }

  public enum TriggerAction {
    PULSE_CHIP,
  }
}
