package io.github.thebluetropics.examplemod;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ExampleMod.ID)
public class ExampleMod {
  public static final String ID = "example_mod";
  public static final Logger LOGGER = LoggerFactory.getLogger(ID);

  public ExampleMod(FMLJavaModLoadingContext context) {
    IEventBus modEventBus = context.getModEventBus();
    modEventBus.addListener(this::commonSetup);
  }

  private void commonSetup(FMLCommonSetupEvent event) {
    ExampleMod.LOGGER.info("Hello, World! (Common Setup)");
  }
}
