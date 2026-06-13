package com.thecyborgage.compat;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCAItems;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.neoforge.common.NeoForge;
import squeek.appleskin.api.event.FoodValuesEvent;

public class MetabolicChipAppleSkinCompat {
  public static void register() {
    NeoForge.EVENT_BUS.addListener(MetabolicChipAppleSkinCompat::onFoodValues);
  }

  private static void onFoodValues(FoodValuesEvent evt) {
    if (evt.player == null) {
      return;
    }

    TCACuriosHelper.getEntityCurioItem(evt.player, TCAItems.METABOLIC_CHIP.get())
        .ifPresent(
            chip -> {
              int bonus = TCAServerConfig.CONFIG.metabolicChipHungerBonus.getAsInt();
              int cost = TCAServerConfig.CONFIG.metabolicChipEatDischarge.getAsInt();

              if (bonus <= 0) {
                return;
              }

              if (!TCACuriosHelper.consumeEntityCoreEnergy(evt.player, cost, true)) {
                return;
              }

              FoodProperties base = evt.modifiedFoodProperties;
              evt.modifiedFoodProperties =
                  new FoodProperties(
                      base.nutrition() + bonus,
                      base.saturation(),
                      base.canAlwaysEat(),
                      base.eatSeconds(),
                      base.usingConvertsTo(),
                      base.effects());
            });
  }
}
