package aldinh777.crafttopain.common;

import aldinh777.crafttopain.CraftToPain;
import aldinh777.crafttopain.lists.PainfulBlocks;
import aldinh777.crafttopain.lists.PainfulItems;
import aldinh777.crafttopain.tiles.TileItemSlot;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Objects;

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static void preInit() {
        PainfulItems.init();
        PainfulBlocks.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(CraftToPain.INSTANCE, new GuiHandler());
    }

    public static void init() {
        RecipeUtils.removeVanillaRecipes();
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(PainfulItems.LIST.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(PainfulBlocks.LIST.toArray(new Block[0]));

        ResourceLocation itemSlot = new ResourceLocation("crafttopain:item_slot");
        GameRegistry.registerTileEntity(TileItemSlot.class, itemSlot);
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
