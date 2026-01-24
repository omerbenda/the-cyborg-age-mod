package com.thecyborgage.mixin;

import com.thecyborgage.init.TCAAttachments;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
  @Inject(method = "getArmorCoverPercentage", at = @At("HEAD"), cancellable = true)
  private void activeCamouflageArmorCoverPercentage(CallbackInfoReturnable<Float> info) {
    IAttachmentHolder attachmentHolder = (IAttachmentHolder) this;

    // Check hasData first to reduce calling default value supplier
    if (attachmentHolder.hasData(TCAAttachments.ACTIVE_CAMOUFLAGE_STATE)) {
      if (attachmentHolder.getData(TCAAttachments.ACTIVE_CAMOUFLAGE_STATE)) {
        info.setReturnValue(0.0F);
      }
    }
  }
}
