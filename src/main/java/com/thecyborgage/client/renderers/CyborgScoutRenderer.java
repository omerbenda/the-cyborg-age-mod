package com.thecyborgage.client.renderers;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.entities.CyborgScoutEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class CyborgScoutRenderer
    extends MobRenderer<CyborgScoutEntity, PlayerModel<CyborgScoutEntity>> {
  private static final ResourceLocation TEXTURE =
      ResourceLocation.fromNamespaceAndPath(
          TheCyborgAgeMod.MOD_ID, "textures/entity/cyborg_scout.png");

  public CyborgScoutRenderer(EntityRendererProvider.Context context) {
    super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);

    this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
  }

  @Override
  public ResourceLocation getTextureLocation(CyborgScoutEntity cyborgEntity) {
    return TEXTURE;
  }
}
