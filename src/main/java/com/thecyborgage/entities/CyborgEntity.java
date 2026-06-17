package com.thecyborgage.entities;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CyborgEntity extends Monster {
  public CyborgEntity(EntityType<? extends Monster> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  protected void registerGoals() {
    super.registerGoals();

    this.goalSelector.addGoal(0, new FloatGoal(this));
    this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
    this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 128.0F, 1.0F));
    this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
  }

  public static AttributeSupplier.Builder createCyborgAttributes() {
    return Monster.createMonsterAttributes()
        .add(Attributes.MAX_HEALTH, 35)
        .add(Attributes.ARMOR, 18)
        .add(Attributes.ATTACK_DAMAGE, 6)
        .add(Attributes.FOLLOW_RANGE, 64.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.48);
  }
}
