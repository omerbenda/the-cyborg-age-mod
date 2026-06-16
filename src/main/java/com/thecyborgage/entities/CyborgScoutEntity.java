package com.thecyborgage.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CyborgScoutEntity extends Monster {
  public CyborgScoutEntity(EntityType<? extends Monster> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  protected void registerGoals() {
    super.registerGoals();

    this.goalSelector.addGoal(0, new FloatGoal(this));
    this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
    this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 128.0F, 1.0F));
    this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
  }

  public static AttributeSupplier.Builder createCyborgScoutAttributes() {
    return Monster.createMonsterAttributes()
        .add(Attributes.MAX_HEALTH, 35)
        .add(Attributes.ARMOR, 22)
        .add(Attributes.ATTACK_DAMAGE, 8)
        .add(Attributes.MOVEMENT_SPEED, 0.58);
  }
}
