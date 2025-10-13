package com.thecyborgage.client;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.client.renderers.CyborgRenderer;
import com.thecyborgage.init.TCAEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = TheCyborgAgeMod.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID, value = Dist.CLIENT)
public class TheCyborgAgeClient {
  public TheCyborgAgeClient(ModContainer container) {}

  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent evt) {
    EntityRenderers.register(TCAEntities.CYBORG.get(), CyborgRenderer::new);
  }
}
