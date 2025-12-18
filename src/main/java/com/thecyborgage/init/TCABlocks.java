package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.blocks.CoreWorkbenchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCABlocks {
  public static final DeferredRegister.Blocks BLOCKS =
      DeferredRegister.createBlocks(TheCyborgAgeMod.MOD_ID);

  public static final DeferredBlock<Block> CORE_WORKBENCH =
      BLOCKS.registerBlock(
          "core_workbench",
          (properties) ->
              new CoreWorkbenchBlock(
                  properties.destroyTime(2.0F).explosionResistance(10.0F).sound(SoundType.METAL)));

  public static void register(IEventBus bus) {
    BLOCKS.register(bus);
  }
}
