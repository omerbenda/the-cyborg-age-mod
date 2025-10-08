package com.thecyborgage.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class CyborgEntity extends Monster {
  public CyborgEntity(EntityType<? extends Monster> entityType, Level level) {
    super(entityType, level);
  }
}
