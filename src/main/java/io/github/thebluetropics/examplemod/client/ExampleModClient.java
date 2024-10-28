package io.github.thebluetropics.examplemod.client;

import io.github.thebluetropics.examplemod.ExampleMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ExampleMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ExampleModClient {
  @SubscribeEvent
  public static void clientSetup(FMLClientSetupEvent event) {
    ExampleMod.LOGGER.info("Hello, World! (Client Setup)");
  }
}
