package com.thecyborgage.items;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCAAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class MagnetChipItem extends Item implements ICurioItem {
  public MagnetChipItem(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);
    LivingEntity entity = slotContext.entity();

    if (entity.level().isClientSide() || entity.isCrouching()) {
      return;
    }

    if (!entity.getData(TCAAttachments.MAGNET_CHIP_TOGGLE_STATE)) {
      return;
    }

    double range = TCAServerConfig.CONFIG.magnetChipRange.getAsDouble();
    AABB searchBox = entity.getBoundingBox().inflate(range);
    List<ItemEntity> nearbyItems = entity.level().getEntitiesOfClass(ItemEntity.class, searchBox);

    if (nearbyItems.isEmpty()) {
      return;
    }

    if (!TCACuriosHelper.consumeEntityCoreEnergy(
        entity, TCAServerConfig.CONFIG.magnetChipDischargeRate.getAsInt())) {
      return;
    }

    Vec3 target = entity.position().add(0, entity.getBbHeight() / 2.0, 0);

    for (ItemEntity itemEntity : nearbyItems) {
      Vec3 diff = target.subtract(itemEntity.position());
      double distance = diff.length();

      if (distance > 0) {
        Vec3 pull = diff.normalize().scale(0.06);
        itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(pull));
        itemEntity.hasImpulse = true;
      }
    }
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

    tooltipComponents.add(
        Component.translatable("thecyborgage.magnet_chip.tooltip").withStyle(ChatFormatting.GRAY));

    Player player = Minecraft.getInstance().player;

    if (player != null) {
      boolean enabled = player.getData(TCAAttachments.MAGNET_CHIP_TOGGLE_STATE);
      tooltipComponents.add(
          Component.translatable(
                  enabled
                      ? "thecyborgage.magnet_chip.enabled_tooltip"
                      : "thecyborgage.magnet_chip.disabled_tooltip")
              .withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED));
    }
  }
}
