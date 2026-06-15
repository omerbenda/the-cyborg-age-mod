package com.thecyborgage.network;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCAAttachments;
import com.thecyborgage.init.TCAItems;
import com.thecyborgage.init.TCASounds;
import com.thecyborgage.network.packets.PulseEffectPayload;
import com.thecyborgage.network.packets.TriggerActionPayload;
import com.thecyborgage.network.packets.ToggleValuePayload;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public class ServerPayloadHandler {
  public static void handleTriggerAction(TriggerActionPayload payload, IPayloadContext context) {
    context.enqueueWork(
        () -> {
          Player player = context.player();

          if (payload.action() == TriggerActionPayload.TriggerAction.PULSE_CHIP) {
            triggerPulseChip(player);
          }
        });
  }

  private static void triggerPulseChip(Player player) {
    if (TCACuriosHelper.getEntityCurioItem(player, TCAItems.PULSE_CHIP.get()).isEmpty()) {
      return;
    }

    if (player.getCooldowns().isOnCooldown(TCAItems.PULSE_CHIP.get())) {
      return;
    }

    int energyCost = TCAServerConfig.CONFIG.pulseChipEnergyCost.get();

    if (!TCACuriosHelper.consumeEntityCoreEnergy(player, energyCost)) {
      player.sendSystemMessage(
          Component.translatable("thecyborgage.system.pulse_chip_no_energy")
              .withStyle(ChatFormatting.GRAY));

      return;
    }

    double radius = TCAServerConfig.CONFIG.pulseChipRadius.get();
    double strength = TCAServerConfig.CONFIG.pulseChipStrength.get();
    AABB searchBox = player.getBoundingBox().inflate(radius);
    Level level = player.level();

    List<LivingEntity> nearby = level.getEntitiesOfClass(LivingEntity.class, searchBox);

    for (LivingEntity target : nearby) {
      if (target == player) {
        continue;
      }

      Vec3 direction = target.position().subtract(player.position());
      double dist = direction.length();

      if (dist > 0) {
        Vec3 push = direction.normalize().scale(strength);
        target.setDeltaMovement(target.getDeltaMovement().add(push.x, strength * 0.4, push.z));
        target.hasImpulse = true;
      }
    }

    player
        .getCooldowns()
        .addCooldown(TCAItems.PULSE_CHIP.get(), TCAServerConfig.CONFIG.pulseChipCooldown.get());

    level.playSound(
        null,
        player.blockPosition(),
        TCASounds.PULSE_CHIP_PULSE.get(),
        SoundSource.PLAYERS,
        2.0F,
        0.9F + level.getRandom().nextFloat() * 0.2F);

    if (level instanceof ServerLevel serverLevel) {
      spawnPulseParticles(serverLevel, player);
    }

    PacketDistributor.sendToPlayer((ServerPlayer) player, new PulseEffectPayload());
  }

  private static void spawnPulseParticles(ServerLevel level, Player player) {
    double x = player.getX();
    double y = player.getY() + player.getBbHeight() * 0.5;
    double z = player.getZ();

    level.sendParticles(ParticleTypes.SONIC_BOOM, x, y, z, 1, 0, 0, 0, 0);
    level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z, 80, 2.8, 2.8, 2.8, 0.06);
    level.sendParticles(ParticleTypes.GUST, x, y, z, 1, 0, 0, 0, 0);
  }

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
