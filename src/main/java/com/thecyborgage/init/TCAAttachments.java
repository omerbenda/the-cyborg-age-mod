package com.thecyborgage.init;

import com.mojang.serialization.Codec;
import com.thecyborgage.TheCyborgAgeMod;
import java.util.function.Supplier;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class TCAAttachments {
  public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
      DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TheCyborgAgeMod.MOD_ID);

  public static final Supplier<AttachmentType<Boolean>> CYBORG_JUMP_LEG_TOGGLE_STATE =
      ATTACHMENT_TYPES.register(
          "cyborg_jump_leg_toggle_state",
          () ->
              AttachmentType.builder(() -> false)
                  .serialize(Codec.BOOL)
                  .sync(ByteBufCodecs.BOOL)
                  .copyOnDeath()
                  .build());

  public static final Supplier<AttachmentType<Boolean>> MAGNET_CHIP_TOGGLE_STATE =
      ATTACHMENT_TYPES.register(
          "magnet_chip_toggle_state",
          () ->
              AttachmentType.builder(() -> false)
                  .serialize(Codec.BOOL)
                  .sync(ByteBufCodecs.BOOL)
                  .copyOnDeath()
                  .build());

  public static final Supplier<AttachmentType<Boolean>> ACTIVE_CAMOUFLAGE_STATE =
      ATTACHMENT_TYPES.register(
          "active_camouflage_state",
          () ->
              AttachmentType.builder(() -> false)
                  .serialize(Codec.BOOL)
                  .sync(ByteBufCodecs.BOOL)
                  .copyOnDeath()
                  .build());

  public static void register(IEventBus bus) {
    ATTACHMENT_TYPES.register(bus);
  }
}
