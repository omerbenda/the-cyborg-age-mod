package com.thecyborgage.client.renderers;

import com.thecyborgage.entities.CyborgEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CyborgRenderer extends MobRenderer<CyborgEntity, PlayerModel<CyborgEntity>> {
  public CyborgRenderer(EntityRendererProvider.Context context) {
    super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
  }

  @Override
  public ResourceLocation getTextureLocation(CyborgEntity cyborgEntity) {
    return ResourceLocation.fromNamespaceAndPath(
        "minecraft", "textures/entity/player/wide/steve.png");
  }
}
