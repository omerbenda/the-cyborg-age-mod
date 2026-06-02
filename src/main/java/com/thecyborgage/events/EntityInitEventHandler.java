package com.thecyborgage.events;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.entities.CyborgEntity;
import com.thecyborgage.entities.CyborgScoutEntity;
import com.thecyborgage.init.TCAEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class EntityInitEventHandler {
  @SubscribeEvent
  public static void onAttributeCreate(EntityAttributeCreationEvent event) {
    event.put(TCAEntities.CYBORG.get(), CyborgEntity.createCyborgAttributes().build());
    event.put(
        TCAEntities.CYBORG_SCOUT.get(), CyborgScoutEntity.createCyborgScoutAttributes().build());
  }
}
