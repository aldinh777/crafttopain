package aldinh777.crafttopain.tiles;

import aldinh777.crafttopain.block.CraftingCore;
import aldinh777.crafttopain.util.IngredientsHelper;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileCraftingCore extends TileItemSlot implements ITickable {

    private IngredientsHelper ingredientsHelper;
    private ItemStack latestResult = ItemStack.EMPTY;

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString("Crafting Work Station");
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player) {
        if (CraftingCore.isStructureCompleted(this.world, this.pos)) {
            return super.isUsableByPlayer(player);
        }
        return false;
    }

    private boolean inputsChanged() {
        ArrayList<ItemStack> inputs = Lists.newArrayList();
        for (int z = -1; z <= 1; z++) {
            for (int x = -1; x <= 1; x++) {
                TileItemSlot itemSlot = (TileItemSlot) this.world.getTileEntity(this.pos.add(x, 1, z));
                if (itemSlot != null) {
                    IItemHandler itemHandler = itemSlot.itemHandler;
                    ItemStack itemStack = itemHandler.getStackInSlot(0);
                    inputs.add(itemStack);
                } else {
                    inputs.add(ItemStack.EMPTY);
                }
            }
        }

        if (this.ingredientsHelper == null) {
            this.ingredientsHelper = new IngredientsHelper(inputs);
        } else {
            boolean isDifferentInputs = this.ingredientsHelper.compareInputs(inputs);
            if (isDifferentInputs) {
                this.ingredientsHelper = new IngredientsHelper(inputs);
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean outputChanged() {
        ItemStack output = this.itemHandler.getStackInSlot(0);
        return output.getItem() != this.latestResult.getItem();
    }

    private void changeOutput() {
        if (this.ingredientsHelper.isEmpty()) {
            this.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
            return;
        }

        ItemStack result = ItemStack.EMPTY;
        boolean success = false;

        for (IRecipe recipe : CraftingManager.REGISTRY) {
            NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
            if (recipe.canFit(this.ingredientsHelper.getWidth(), this.ingredientsHelper.getHeight())) {
                ArrayList<ItemStack> ingredients = this.ingredientsHelper.getIngredients();
                int index = 0;
                for (Ingredient ingredient : recipeIngredients) {
                    if (!ingredient.apply(ingredients.get(index))) {
                        break;
                    }
                    index++;
                }
                if (index == ingredients.size()) {
                    success = true;
                    result = recipe.getRecipeOutput();
                    break;
                }
            }
        }

        if (success) {
            this.itemHandler.setStackInSlot(0, result.copy());
            this.latestResult = result.copy();
        } else {
            this.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
            this.latestResult = ItemStack.EMPTY;
        }
    }

    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (inputsChanged() || outputChanged()) {
                changeOutput();
                this.markDirty();
            }
        }
    }
}
