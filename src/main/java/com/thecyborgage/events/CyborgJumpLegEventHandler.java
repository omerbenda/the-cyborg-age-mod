package com.thecyborgage.events;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCAAttachments;
import com.thecyborgage.init.TCAItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import java.util.Optional;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class CyborgJumpLegEventHandler {
  @SubscribeEvent
  public static void onEntityJump(LivingEvent.LivingJumpEvent evt) {
    LivingEntity entity = evt.getEntity();
    Optional<IEnergyStorage> optionalCoreEnergyStorage =
        TCACuriosHelper.getEntityCoreEnergyStorage(entity);

    if (optionalCoreEnergyStorage.isPresent()) {
      Optional<ItemStack> optionalJumpLeg =
          TCACuriosHelper.getEntityCurioItem(entity, TCAItems.CYBORG_JUMP_LEG.get());

      if (optionalJumpLeg.isPresent()
          && entity.getData(TCAAttachments.CYBORG_JUMP_LEG_TOGGLE_STATE)) {
        IEnergyStorage coreEnergyStorage = optionalCoreEnergyStorage.get();

        TCACuriosHelper.consumeEntityCoreEnergy(
            entity,
            Math.min(
                TCAServerConfig.CONFIG.cyborgJumpLegJumpDischarge.getAsInt(),
                coreEnergyStorage.getEnergyStored()));
      }
    }
  }
}
