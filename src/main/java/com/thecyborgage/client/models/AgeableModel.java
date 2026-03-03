package com.thecyborgage.client.models;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public abstract class AgeableModel extends Model {
  public AgeableModel(Function<ResourceLocation, RenderType> renderType) {
    super(renderType);
  }

  public abstract void setAge(float ageInTicks);
}
