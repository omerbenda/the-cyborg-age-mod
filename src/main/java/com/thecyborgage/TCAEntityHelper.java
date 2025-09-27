package com.thecyborgage;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class TCAEntityHelper {
  public static boolean isEntityMovingHorizontal(LivingEntity entity) {
    Vec3 horizontalMovement = entity.getKnownMovement().with(Direction.Axis.Y, 0);

    return entity.onGround() && horizontalMovement.lengthSqr() > 0.01;
  }
}
