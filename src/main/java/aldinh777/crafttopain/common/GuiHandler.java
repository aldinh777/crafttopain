package aldinh777.crafttopain.common;

import aldinh777.crafttopain.container.ContainerItemSlot;
import aldinh777.crafttopain.gui.GuiItemSlot;
import aldinh777.crafttopain.tiles.TileItemSlot;
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
            default:
                return null;
        }
    }
}
