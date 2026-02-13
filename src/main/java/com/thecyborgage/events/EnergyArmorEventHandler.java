package com.thecyborgage.events;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCAItems;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Optional;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class EnergyArmorEventHandler {
  @SubscribeEvent
  public static void onEntityDamaged(LivingDamageEvent.Post evt) {
    LivingEntity entity = evt.getEntity();
    Optional<IEnergyStorage> optionalCoreEnergyStorage =
        TCACuriosHelper.getEntityCoreEnergyStorage(entity);

    if (optionalCoreEnergyStorage.isPresent()) {
      Optional<ItemStack> optionalEnergyArmor =
          TCACuriosHelper.getEntityCurioItem(entity, TCAItems.ENERGY_ARMOR.get());

      if (!evt.getSource().is(DamageTypeTags.BYPASSES_ARMOR) && optionalEnergyArmor.isPresent()) {
        IEnergyStorage coreEnergyStorage = optionalCoreEnergyStorage.get();

        TCACuriosHelper.consumeEntityCoreEnergy(
            entity,
            Math.min(
                TCAServerConfig.CONFIG.energyArmorHitDischarge.getAsInt(),
                coreEnergyStorage.getEnergyStored()));
      }
    }
  }
}
