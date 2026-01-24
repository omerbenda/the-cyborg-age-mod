package com.thecyborgage.mixin;

import com.thecyborgage.init.TCAAttachments;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
  @Inject(method = "isInvisible", at = @At("RETURN"), cancellable = true)
  private void activeCamouflageInvisibility(CallbackInfoReturnable<Boolean> info) {
    if (info.getReturnValueZ()){
      return;
    }

    IAttachmentHolder attachmentHolder = (IAttachmentHolder) this;

    // Check hasData first to reduce calling default value supplier
    if (attachmentHolder.hasData(TCAAttachments.ACTIVE_CAMOUFLAGE_STATE)) {
      if (attachmentHolder.getData(TCAAttachments.ACTIVE_CAMOUFLAGE_STATE)) {
        info.setReturnValue(true);
      }
    }
  }
}
