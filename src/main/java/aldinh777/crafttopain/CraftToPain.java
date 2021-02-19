package aldinh777.crafttopain;

import aldinh777.crafttopain.common.RegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CraftToPain.MODID, name = CraftToPain.NAME, version = CraftToPain.VERSION)
public class CraftToPain {
    public static final String MODID = "crafttopain";
    public static final String NAME = "Craft to Pain";
    public static final String VERSION = "1.0";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        RegistryHandler.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        RegistryHandler.init();
    }
}
