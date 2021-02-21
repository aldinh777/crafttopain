package aldinh777.crafttopain.container;

import aldinh777.crafttopain.block.SmeltingCore;
import aldinh777.crafttopain.tiles.TileSmeltingCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerSmeltingCore extends Container {

    private final TileSmeltingCore tileEntity;
    private int burnTime;
    private int totalBurnTime;
    private int progress;
    private int maxProgress;

    public ContainerSmeltingCore(InventoryPlayer player, TileSmeltingCore tileEntity) {
        this.tileEntity = tileEntity;

        World world = tileEntity.getWorld();
        BlockPos pos = tileEntity.getPos();

        IItemHandler fuelHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        this.addSlotToContainer(new SlotItemHandler(fuelHandler, 0, 56, 53) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return TileSmeltingCore.getItemBurnTime(stack) > 0;
            }
        });

        SmeltingCore.getSlots(world, pos, (inputSlot, outputSlot) -> {
            IItemHandler inputHandler = inputSlot.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            IItemHandler outputHandler = outputSlot.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            this.addSlotToContainer(new SlotItemHandler(inputHandler, 0, 56, 17));
            this.addSlotToContainer(new SlotItemHandler(outputHandler, 0, 116, 35));
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
    public void addListener(@Nonnull IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileEntity);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener icontainerlistener : this.listeners) {
            if (this.progress != this.tileEntity.getField(2)) {
                icontainerlistener.sendWindowProperty(this, 2, this.tileEntity.getField(2));
            }

            if (this.burnTime != this.tileEntity.getField(0)) {
                icontainerlistener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
            }

            if (this.totalBurnTime != this.tileEntity.getField(1)) {
                icontainerlistener.sendWindowProperty(this, 1, this.tileEntity.getField(1));
            }

            if (this.maxProgress != this.tileEntity.getField(3)) {
                icontainerlistener.sendWindowProperty(this, 3, this.tileEntity.getField(3));
            }
        }

        this.progress = this.tileEntity.getField(2);
        this.burnTime = this.tileEntity.getField(0);
        this.totalBurnTime = this.tileEntity.getField(1);
        this.maxProgress = this.tileEntity.getField(3);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        this.tileEntity.setField(id, data);
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

            if (index < 3) {
                if (!this.mergeItemStack(stack, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 30) {
                if (!this.mergeItemStack(stack, 30, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 39) {
                if (!this.mergeItemStack(stack, 3, 30, false)) {
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
