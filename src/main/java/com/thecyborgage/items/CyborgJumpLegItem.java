package com.thecyborgage.items;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.config.TCAServerConfig;
import com.thecyborgage.init.TCAAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class CyborgJumpLegItem extends AttributeCyborgItem {
  public CyborgJumpLegItem(Properties properties) {
    super(
        properties,
        ResourceLocation.fromNamespaceAndPath(
            TheCyborgAgeMod.MOD_ID, "attribute.cyborg_jump_leg.jump_modifier"),
        Attributes.JUMP_STRENGTH,
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
  }

  @Override
  public double getAmount(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.cyborgJumpLegJumpValue.getAsDouble();
  }

  @Override
  public int getEnergyUsage(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.cyborgJumpLegDischargeRate.getAsInt();
  }

  @Override
  public int getMinEnergyRequired(SlotContext slotContext, ItemStack stack) {
    return TCAServerConfig.CONFIG.cyborgJumpLegJumpDischarge.get();
  }

  @Override
  public boolean shouldApplyAttribute(SlotContext slotContext, ItemStack stack) {
    Entity entity = slotContext.entity();

    return entity.getData(TCAAttachments.CYBORG_JUMP_LEG_TOGGLE_STATE);
  }

  @Override
  public boolean shouldConsumeEnergy(SlotContext slotContext, ItemStack stack) {
    Entity entity = slotContext.entity();

    return entity.getData(TCAAttachments.CYBORG_JUMP_LEG_TOGGLE_STATE);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

    tooltipComponents.add(
        Component.translatable("thecyborgage.cyborg_jump_leg.tooltip")
            .withStyle(ChatFormatting.GRAY));

    Player player = Minecraft.getInstance().player;
    boolean toggleState = player.getData(TCAAttachments.CYBORG_JUMP_LEG_TOGGLE_STATE);

    tooltipComponents.add(
        toggleState
            ? Component.translatable("thecyborgage.cyborg_jump_leg.enabled_tooltip")
            : Component.translatable("thecyborgage.cyborg_jump_leg.disabled_tooltip"));
  }
}
