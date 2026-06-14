package com.thecyborgage.network.packets;

import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ToggleValuePayload(ToggleValue value, boolean toggled)
    implements CustomPacketPayload {
  public static final Type<ToggleValuePayload> TYPE =
      new Type<>(ResourceLocation.fromNamespaceAndPath(TheCyborgAgeMod.MOD_ID, "toggle_value"));

  public static final StreamCodec<FriendlyByteBuf, ToggleValuePayload> STREAM_CODEC =
      StreamCodec.of(ToggleValuePayload::encode, ToggleValuePayload::decode);

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  private static void encode(FriendlyByteBuf buffer, ToggleValuePayload payload) {
    buffer.writeVarInt(payload.value().ordinal());
    buffer.writeBoolean(payload.toggled());
  }

  private static ToggleValuePayload decode(FriendlyByteBuf buffer) {
    int ordinal = buffer.readVarInt();
    ToggleValue value = ToggleValue.values()[ordinal];
    boolean toggled = buffer.readBoolean();

    return new ToggleValuePayload(value, toggled);
  }

  public enum ToggleValue {
    CYBORG_JUMP_LEG,
    MAGNET_CHIP,
  }
}
