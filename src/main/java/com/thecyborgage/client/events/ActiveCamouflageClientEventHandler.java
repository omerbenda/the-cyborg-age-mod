package com.thecyborgage.client.events;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.init.TCAAttachments;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID, value = Dist.CLIENT)
public class ActiveCamouflageClientEventHandler {
  @SubscribeEvent
  public static void onRenderLivingEntity(RenderLivingEvent.Pre<?, ?> evt) {
    LivingEntity entity = evt.getEntity();

    if (entity.getData(TCAAttachments.ACTIVE_CAMOUFLAGE_STATE)) {
      evt.setCanceled(true);
    }
  }
}
