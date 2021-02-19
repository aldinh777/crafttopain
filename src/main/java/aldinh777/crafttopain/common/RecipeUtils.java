package aldinh777.crafttopain.common;

import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.ArrayList;
import java.util.Objects;

public class RecipeUtils {
    
    public static ArrayList<Item> removeList = new ArrayList<>();

    public static void removeVanillaRecipes() {
        IForgeRegistryModifiable<IRecipe> recipeRegistry = (IForgeRegistryModifiable<IRecipe>) ForgeRegistries.RECIPES;
        ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValuesCollection());

        initRemove();
        
        for (IRecipe r : recipes) {
            ItemStack output = r.getRecipeOutput();
            String domain = Objects.requireNonNull(r.getRegistryName()).getResourceDomain();

            if (removeList.contains(output.getItem()) && domain.equals("minecraft")) {
                recipeRegistry.remove(r.getRegistryName());
            }
        }
    }
    
    public static void removeRecipe(Item item) {
        if (!removeList.contains(item)) {
            removeList.add(item);
        }
    }

    private static void initRemove() {
        removeRecipe(Items.WOODEN_SWORD);
        removeRecipe(Items.WOODEN_AXE);
        removeRecipe(Items.WOODEN_PICKAXE);
        removeRecipe(Items.WOODEN_SHOVEL);
        removeRecipe(Items.WOODEN_HOE);

        removeRecipe(Items.STONE_SWORD);
        removeRecipe(Items.STONE_AXE);
        removeRecipe(Items.STONE_PICKAXE);
        removeRecipe(Items.STONE_SHOVEL);
        removeRecipe(Items.STONE_HOE);

        removeRecipe(Items.IRON_SWORD);
        removeRecipe(Items.IRON_AXE);
        removeRecipe(Items.IRON_PICKAXE);
        removeRecipe(Items.IRON_SHOVEL);
        removeRecipe(Items.IRON_HOE);

        removeRecipe(Items.GOLDEN_SWORD);
        removeRecipe(Items.GOLDEN_AXE);
        removeRecipe(Items.GOLDEN_PICKAXE);
        removeRecipe(Items.GOLDEN_SHOVEL);
        removeRecipe(Items.GOLDEN_HOE);

        removeRecipe(Items.DIAMOND_SWORD);
        removeRecipe(Items.DIAMOND_AXE);
        removeRecipe(Items.DIAMOND_PICKAXE);
        removeRecipe(Items.DIAMOND_SHOVEL);
        removeRecipe(Items.DIAMOND_HOE);
    }
}
