package aldinh777.crafttopain.common;

import aldinh777.crafttopain.lists.PainfulItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static void preInit() {
        PainfulItems.init();
    }

    public static void init() {
        RecipeUtils.removeVanillaRecipes();
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(PainfulItems.LIST.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : PainfulItems.LIST) {
            registerModel(item);
        }
    }

    private static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(
                item, 0, new ModelResourceLocation(
                        Objects.requireNonNull(item.getRegistryName()).toString(), "inventory"
                )
        );
    }
}
