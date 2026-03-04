package com.thecyborgage.events;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Collection;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class CommandsEventHandler {
  @SubscribeEvent
  public static void onRegisterCommands(RegisterCommandsEvent evt) {
    CommandDispatcher<CommandSourceStack> dispatcher = evt.getDispatcher();

    dispatcher.register(
        Commands.literal("cyborg")
            .requires((source) -> source.hasPermission(2))
            .then(
                Commands.literal("energy")
                    .then(
                        Commands.argument("targets", EntityArgument.entities())
                            .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                            .executes(
                                (context) -> {
                                  Collection<? extends Entity> targets =
                                      EntityArgument.getEntities(context, "targets");
                                  int amount = IntegerArgumentType.getInteger(context, "amount");
                                  int affectedCount = 0;

                                  for (Entity target : targets) {
                                    if (target instanceof LivingEntity validTarget
                                        && TCACuriosHelper.setEntityCoreEnergy(
                                            validTarget, amount)) {
                                      affectedCount++;
                                    }
                                  }

                                  CommandSourceStack contextSource = context.getSource();

                                  if (affectedCount == 1) {
                                    contextSource.sendSuccess(
                                        () ->
                                            Component.translatable(
                                                "thecyborgage.set_energy_command.singular", amount),
                                        true);
                                  } else {
                                    final int usedAffectedCount = affectedCount;

                                    contextSource.sendSuccess(
                                        () ->
                                            Component.translatable(
                                                "thecyborgage.set_energy_command.plural",
                                                amount,
                                                usedAffectedCount),
                                        true);
                                  }

                                  return affectedCount;
                                })))));
  }
}
