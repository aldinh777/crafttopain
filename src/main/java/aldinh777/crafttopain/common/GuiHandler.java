package aldinh777.crafttopain.common;

import aldinh777.crafttopain.container.ContainerCraftingCore;
import aldinh777.crafttopain.container.ContainerItemSlot;
import aldinh777.crafttopain.container.ContainerSmeltingCore;
import aldinh777.crafttopain.gui.GuiCraftingCore;
import aldinh777.crafttopain.gui.GuiItemSlot;
import aldinh777.crafttopain.gui.GuiSmeltingCore;
import aldinh777.crafttopain.tiles.TileCraftingCore;
import aldinh777.crafttopain.tiles.TileItemSlot;
import aldinh777.crafttopain.tiles.TileSmeltingCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case 0:
                TileItemSlot itemSlot = (TileItemSlot) tileEntity;
                return new ContainerItemSlot(player.inventory, Objects.requireNonNull(itemSlot));
            case 1:
                TileCraftingCore craftingCore = (TileCraftingCore) tileEntity;
                return new ContainerCraftingCore(player.inventory, Objects.requireNonNull(craftingCore));
            case 2:
                TileSmeltingCore smeltingCore = (TileSmeltingCore) tileEntity;
                return  new ContainerSmeltingCore(player.inventory, Objects.requireNonNull(smeltingCore));
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case 0:
                TileItemSlot itemSlot = (TileItemSlot) tileEntity;
                return new GuiItemSlot(player.inventory, Objects.requireNonNull(itemSlot));
            case 1:
                TileCraftingCore craftingCore = (TileCraftingCore) tileEntity;
                return new GuiCraftingCore(player.inventory, Objects.requireNonNull(craftingCore));
            case 2:
                TileSmeltingCore smeltingCore = (TileSmeltingCore) tileEntity;
                return  new GuiSmeltingCore(player.inventory, Objects.requireNonNull(smeltingCore));
            default:
                return null;
        }
    }
}
