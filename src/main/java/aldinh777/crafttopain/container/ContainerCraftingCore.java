package aldinh777.crafttopain.container;

import aldinh777.crafttopain.tiles.TileCraftingCore;
import aldinh777.crafttopain.tiles.TileItemSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerCraftingCore extends Container {

    private final TileCraftingCore tileEntity;

    public ContainerCraftingCore(InventoryPlayer player, TileCraftingCore tileEntity) {
        this.tileEntity = tileEntity;

        World worldIn = this.tileEntity.getWorld();
        BlockPos pos = this.tileEntity.getPos();

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                TileItemSlot itemSlot = (TileItemSlot) worldIn.getTileEntity(pos.add(x, 1, z));
                if (itemSlot != null) {
                    IItemHandler itemHandler = itemSlot.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                    this.addSlotToContainer(new SlotItemHandler(itemHandler, 0, 48 + (18 * x), 35 + (18 * z)));
                }
            }
        }

        IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        this.addSlotToContainer(new SlotItemHandler(itemHandler, 0, 124, 35) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return false;
            }

            @Nonnull
            @Override
            public ItemStack onTake(@Nonnull EntityPlayer thePlayer, @Nonnull ItemStack stack) {
                World worldIn = tileEntity.getWorld();
                BlockPos pos = tileEntity.getPos();

                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        TileItemSlot itemSlot = (TileItemSlot) worldIn.getTileEntity(pos.add(x, 1, z));
                        if (itemSlot != null) {
                            IItemHandler itemHandler = itemSlot.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                            if (itemHandler != null) {
                                ItemStack slotStack =  itemHandler.getStackInSlot(0);
                                slotStack.shrink(1);
                            }
                        }
                    }
                }

                return super.onTake(thePlayer, stack);
            }
        });

        for (int y = 0; y < 3; y++) {
            for (int i = 0; i < 9; i++) {
                this.addSlotToContainer(new Slot(player, i + y * 9 + 9, 8 + i * 18, 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return this.tileEntity.isUsableByPlayer(playerIn);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (index < 10) {
                return ItemStack.EMPTY;
            } else if (index < 37) {
                if (!this.mergeItemStack(stack, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 46) {
                if (!this.mergeItemStack(stack, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
