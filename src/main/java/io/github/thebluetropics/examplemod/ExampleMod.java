package io.github.thebluetropics.examplemod;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExampleMod.ID)
public class ExampleMod {
  public static final String ID = "examplemod";
  public static final Logger LOGGER = LogManager.getLogger(ID);

  public ExampleMod() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    modEventBus.addListener(this::commonSetup);
  }

  private void commonSetup(FMLCommonSetupEvent event) {
    ExampleMod.LOGGER.info("Hello, World! (Common Setup)");
  }
}
