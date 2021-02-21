package aldinh777.crafttopain.tiles;

import aldinh777.crafttopain.block.SmeltingCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TileSmeltingCore extends TileItemSlot implements ITickable {

    private int burnTime = 0;
    private int progress = 0;
    private int totalBurnTime = 0;
    private int maxProgress = 200;

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString("Smelting Station");
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player) {
        if (SmeltingCore.isStructureCompleted(this.world, this.pos)) {
            return super.isUsableByPlayer(player);
        }
        return false;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.burnTime;
            case 1:
                return this.totalBurnTime;
            case 2:
                return this.progress;
            case 3:
                return this.maxProgress;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.burnTime = value;
                break;
            case 1:
                this.totalBurnTime = value;
                break;
            case 2:
                this.progress = value;
                break;
            case 3:
                this.maxProgress = value;
        }
    }

    public static int getItemBurnTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.WOODEN_SLAB) return 150;
                if (block.getDefaultState().getMaterial() == Material.WOOD) return 300;
                if (block == Blocks.COAL_BLOCK) return 16000;
            }

            if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
            if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
            if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
            if (item == Items.STICK) return 100;
            if (item == Items.COAL) return 1600;
            if (item == Items.LAVA_BUCKET) return 20000;
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
            if (item == Items.BLAZE_ROD) return 2400;

            return ForgeEventFactory.getItemBurnTime(fuel);
        }
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    private boolean canSmelt(IItemHandler inputHandler, IItemHandler outputHandler) {
        ItemStack input = inputHandler.getStackInSlot(0);
        ItemStack output = outputHandler.getStackInSlot(0);
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
        if (result.isEmpty()) {
            return false;
        }
        if (output.isEmpty()) {
            return true;
        }
        if (result.getItem() != output.getItem()) {
            return false;
        }
        return output.getCount() < output.getMaxStackSize();
    }

    private boolean canBurn() {
        ItemStack fuel = this.itemHandler.getStackInSlot(0);
        if (fuel.isEmpty()) {
            return false;
        }
        return getItemBurnTime(fuel) > 0;
    }

    private void burnFuel() {
        ItemStack fuel = this.itemHandler.getStackInSlot(0);
        this.burnTime = getItemBurnTime(fuel);
        this.totalBurnTime = this.burnTime;
        fuel.shrink(1);
    }

    private void smeltItem(IItemHandler inputHandler, ItemStackHandler outputHandler) {
        ItemStack input = inputHandler.getStackInSlot(0);
        ItemStack output = outputHandler.getStackInSlot(0);
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
        if (output.isEmpty()) {
            outputHandler.setStackInSlot(0, result.copy());
            return;
        }
        if (output.getCount() < output.getMaxStackSize()) {
            output.grow(1);
        }
        input.shrink(1);
    }

    @Override
    public void update() {
        if (!this.world.isRemote) {
            SmeltingCore.getSlots(this.world, this.pos, (inputSlot, outputSlot) -> {
                IItemHandler input = inputSlot.itemHandler;
                ItemStackHandler output = outputSlot.itemHandler;

                if (this.canSmelt(input, output)) {
                    if (this.isBurning()) {
                        if (this.progress >= this.maxProgress) {
                            smeltItem(input, output);
                            this.progress = 0;
                        } else {
                            this.progress++;
                        }
                        this.markDirty();
                    } else if (this.canBurn()) {
                        this.burnFuel();
                        this.progress++;
                        this.markDirty();
                    } else if (this.progress > 0) {
                        this.progress--;
                        this.markDirty();
                    }
                } else if (this.progress >0) {
                    this.progress--;
                }
                if (this.isBurning()) {
                    burnTime--;
                    this.markDirty();
                }
            });
        }
    }
}
