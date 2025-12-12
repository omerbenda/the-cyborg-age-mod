package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCABlocks {
  public static final DeferredRegister.Blocks BLOCKS =
      DeferredRegister.createBlocks(TheCyborgAgeMod.MOD_ID);

  public static final DeferredBlock<Block> CORE_WORKBENCH =
      BLOCKS.registerSimpleBlock("core_workbench");

  public static void register(IEventBus bus) {
    BLOCKS.register(bus);
  }
}
