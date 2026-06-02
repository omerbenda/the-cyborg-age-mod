package com.thecyborgage.init;

import com.thecyborgage.TheCyborgAgeMod;
import com.thecyborgage.blocks.CoreWorkbenchBlock;
import com.thecyborgage.blocks.CyborgBeaconBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCABlocks {
  public static final DeferredRegister.Blocks BLOCKS =
      DeferredRegister.createBlocks(TheCyborgAgeMod.MOD_ID);

  public static final DeferredBlock<CoreWorkbenchBlock> CORE_WORKBENCH =
      BLOCKS.registerBlock(
          "core_workbench",
          CoreWorkbenchBlock::new,
          BlockBehaviour.Properties.of()
              .destroyTime(2.0F)
              .explosionResistance(10.0F)
              .sound(SoundType.METAL));

  public static final DeferredBlock<CyborgBeaconBlock> CYBORG_BEACON =
      BLOCKS.registerBlock(
          "cyborg_beacon",
          CyborgBeaconBlock::new,
          BlockBehaviour.Properties.of()
              .destroyTime(4.0F)
              .explosionResistance(20.0F)
              .sound(SoundType.METAL)
              .noOcclusion());

  public static void register(IEventBus bus) {
    BLOCKS.register(bus);
  }
}
