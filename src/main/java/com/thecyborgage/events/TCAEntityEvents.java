package com.thecyborgage.events;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.init.TCAEntities;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID)
public class TCAEntityEvents {
  @SubscribeEvent
  public static void onAttributeCreate(EntityAttributeCreationEvent event) {
    event.put(TCAEntities.CYBORG.get(), Monster.createMonsterAttributes().build());
  }
}
