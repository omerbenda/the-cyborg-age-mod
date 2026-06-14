package com.thecyborgage.client;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.client.models.PlayerRadarModel;
import com.thecyborgage.client.models.SolarHatModel;
import com.thecyborgage.client.renderers.*;
import com.thecyborgage.client.screens.VisorActionsScreen;
import com.thecyborgage.init.TCAAttachments;
import com.thecyborgage.init.TCAEntities;
import com.thecyborgage.init.TCAItems;
import com.thecyborgage.network.packets.ToggleValuePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(value = TheCyborgAgeMod.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = TheCyborgAgeMod.MOD_ID, value = Dist.CLIENT)
public class TheCyborgAgeClient {
  private static boolean toggleCircleOpened = false;

  public TheCyborgAgeClient(ModContainer container) {
    container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
  }

  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent evt) {
    registerRenderers();
  }

  private static void registerRenderers() {
    EntityRenderers.register(TCAEntities.CYBORG.get(), CyborgRenderer::new);
    EntityRenderers.register(TCAEntities.CYBORG_SCOUT.get(), CyborgScoutRenderer::new);

    CuriosRendererRegistry.register(
        TCAItems.SOLAR_HAT.get(),
        () ->
            new HatCurioRenderer(
                ResourceLocation.fromNamespaceAndPath(
                    TheCyborgAgeMod.MOD_ID, "textures/entity/curios/solar_hat.png"),
                new SolarHatModel(
                    Minecraft.getInstance()
                        .getEntityModels()
                        .bakeLayer(SolarHatModel.LAYER_LOCATION))));
    CuriosRendererRegistry.register(
        TCAItems.PLAYER_RADAR.get(),
        () ->
            new AgingHatCurioRenderer(
                ResourceLocation.fromNamespaceAndPath(
                    TheCyborgAgeMod.MOD_ID, "textures/entity/curios/player_radar.png"),
                new PlayerRadarModel(
                    Minecraft.getInstance()
                        .getEntityModels()
                        .bakeLayer(PlayerRadarModel.LAYER_LOCATION))));
  }

  @SubscribeEvent
  private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions evt) {
    evt.registerLayerDefinition(SolarHatModel.LAYER_LOCATION, SolarHatModel::createLayer);
    evt.registerLayerDefinition(PlayerRadarModel.LAYER_LOCATION, PlayerRadarModel::createLayer);
  }

  @SubscribeEvent
  public static void onRegisterKeybinds(RegisterKeyMappingsEvent evt) {
    evt.register(TCAKeybinds.TOGGLE_CYBORG_JUMP_LEG);
    evt.register(TCAKeybinds.OPEN_TOGGLE_CIRCLE);
  }

  @SubscribeEvent
  public static void onClientTick(ClientTickEvent.Post evt) {
    while (TCAKeybinds.TOGGLE_CYBORG_JUMP_LEG.consumeClick()) {
      Player player = Minecraft.getInstance().player;

      PacketDistributor.sendToServer(
          new ToggleValuePayload(
              ToggleValuePayload.ToggleValue.CYBORG_JUMP_LEG,
              !player.getData(TCAAttachments.CYBORG_JUMP_LEG_TOGGLE_STATE)));
    }

    Minecraft mc = Minecraft.getInstance();
    Player player = mc.player;

    boolean holdKeyDown = TCAKeybinds.isPhysicallyDown(TCAKeybinds.OPEN_TOGGLE_CIRCLE);

    if (player != null && mc.screen == null) {
      if (holdKeyDown && !toggleCircleOpened) {
        if (TCACuriosHelper.getEntityCurioItem(player, TCAItems.CYBORG_VISOR.get()).isPresent()) {
          mc.setScreen(new VisorActionsScreen(player));
        }

        toggleCircleOpened = true;
      }
    }

    if (!holdKeyDown) {
      toggleCircleOpened = false;
    }
  }
}
