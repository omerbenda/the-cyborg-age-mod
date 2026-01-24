package com.thecyborgage.items;

import com.thecyborgage.TCACuriosHelper;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCAAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class ActiveCamouflageItem extends Item implements ICurioItem {
  public ActiveCamouflageItem(Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);
    LivingEntity entity = slotContext.entity();

    boolean shouldCamouflage =
        entity.isCrouching()
            && TCACuriosHelper.consumeEntityCoreEnergy(
                entity, TCAServerConfig.CONFIG.activeCamouflageDischargeRate.getAsInt());

    entity.setData(TCAAttachments.ACTIVE_CAMOUFLAGE_STATE, shouldCamouflage);
  }

  @Override
  public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
    ICurioItem.super.onUnequip(slotContext, newStack, stack);
    LivingEntity entity = slotContext.entity();

    entity.setData(TCAAttachments.ACTIVE_CAMOUFLAGE_STATE, false);
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

    tooltipComponents.add(
        Component.translatable("thecyborgage.active_camouflage.tooltip")
            .withStyle(ChatFormatting.GRAY));
  }
}
