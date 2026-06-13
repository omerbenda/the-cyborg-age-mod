package com.thecyborgage.events;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCAItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class MetabolicChipEventHandler {
  @SubscribeEvent
  public static void onItemFinishedUsing(LivingEntityUseItemEvent.Finish evt) {
    if (!(evt.getEntity() instanceof Player player)) {
      return;
    }

    if (!evt.getItem().has(DataComponents.FOOD)) {
      return;
    }

    TCACuriosHelper.getEntityCurioItem(player, TCAItems.METABOLIC_CHIP.get())
        .ifPresent(
            (chip) -> {
              int cost = TCAServerConfig.CONFIG.metabolicChipEatDischarge.getAsInt();
              int bonus = TCAServerConfig.CONFIG.metabolicChipHungerBonus.getAsInt();

              if (bonus > 0 && TCACuriosHelper.consumeEntityCoreEnergy(player, cost)) {
                player.getFoodData().eat(bonus, 0.0f);
              }
            });
  }
}
