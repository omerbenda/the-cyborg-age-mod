package com.thecyborgage.events;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.entities.CyborgEntity;
import com.thecyborgage.init.TCAEntities;
import com.thecyborgage.init.TCAItems;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import java.util.Optional;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class TCAEntityEvents {
  @SubscribeEvent
  public static void onAttributeCreate(EntityAttributeCreationEvent event) {
    event.put(TCAEntities.CYBORG.get(), Monster.createMonsterAttributes().build());
  }

  @SubscribeEvent
  public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent evt) {
    evt.register(
        TCAEntities.CYBORG.get(),
        SpawnPlacementTypes.ON_GROUND,
        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
        CyborgEntity::checkMobSpawnRules,
        RegisterSpawnPlacementsEvent.Operation.REPLACE);
  }

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

  @SubscribeEvent
  public static void onEntityJump(LivingEvent.LivingJumpEvent evt) {
    LivingEntity entity = evt.getEntity();
    Optional<IEnergyStorage> optionalCoreEnergyStorage =
        TCACuriosHelper.getEntityCoreEnergyStorage(entity);

    if (optionalCoreEnergyStorage.isPresent()) {
      Optional<ItemStack> optionalJumpLeg =
          TCACuriosHelper.getEntityCurioItem(entity, TCAItems.CYBORG_JUMP_LEG.get());

      if (optionalJumpLeg.isPresent()) {
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
