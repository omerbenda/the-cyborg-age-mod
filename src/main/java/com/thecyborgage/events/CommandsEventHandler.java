package com.thecyborgage.events;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
import org.jetbrains.annotations.NotNull;

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
                            .then(
                                Commands.argument("amount", IntegerArgumentType.integer(0))
                                    .executes(CommandsEventHandler::executeSetEnergy)))));
  }

  private static int executeSetEnergy(CommandContext<CommandSourceStack> context)
      throws CommandSyntaxException {
    Collection<? extends Entity> targets = EntityArgument.getEntities(context, "targets");
    int amount = IntegerArgumentType.getInteger(context, "amount");
    int affectedCount = 0;

    for (Entity target : targets) {
      if (target instanceof LivingEntity validTarget
          && TCACuriosHelper.setEntityCoreEnergy(validTarget, amount)) {
        affectedCount++;
      }
    }

    CommandSourceStack contextSource = context.getSource();
    Component successComponent = getSuccessComponent(affectedCount, amount);

    contextSource.sendSuccess(() -> successComponent, true);

    return affectedCount;
  }

  @NotNull
  private static Component getSuccessComponent(int affectedCount, int amount) {
    if (affectedCount == 1) {
      return Component.translatable("thecyborgage.set_energy_command.singular", amount);
    }

    return Component.translatable("thecyborgage.set_energy_command.plural", amount, affectedCount);
  }
}
