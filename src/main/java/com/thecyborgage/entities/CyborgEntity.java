package com.thecyborgage.entities;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class CyborgEntity extends Monster {
  public CyborgEntity(EntityType<? extends Monster> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  protected void registerGoals() {
    super.registerGoals();

    this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
    this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 128.0F, 1.0F));
    this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
  }

  @Override
  protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
    super.populateDefaultEquipmentSlots(random, difficulty);

    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
    this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
    this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
  }

  @Override
  public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor level,
      DifficultyInstance difficulty,
      MobSpawnType spawnType,
      @Nullable SpawnGroupData spawnGroupData) {
    SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);

    RandomSource random = level.getRandom();
    this.populateDefaultEquipmentSlots(random, difficulty);
    this.populateDefaultEquipmentEnchantments(level, random, difficulty);

    return data;
  }

  public static AttributeSupplier.Builder createCyborgAttributes() {
    return Monster.createMonsterAttributes()
        .add(Attributes.MAX_HEALTH, 30)
        .add(Attributes.FOLLOW_RANGE, 64.0D);
  }
}
