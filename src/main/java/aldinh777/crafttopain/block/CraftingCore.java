package aldinh777.crafttopain.block;

import aldinh777.crafttopain.CraftToPain;
import aldinh777.crafttopain.lists.PainfulBlocks;
import aldinh777.crafttopain.tiles.TileCraftingCore;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CraftingCore extends PainfulBlock {

    public CraftingCore(Material materialIn, String name) {
        super(materialIn, name);
    }

    @Override
    public boolean hasTileEntity(@Nonnull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileCraftingCore();
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!isStructureCompleted(worldIn, pos)) {
            return false;
        }
        if (!worldIn.isRemote) {
            playerIn.openGui(CraftToPain.INSTANCE, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    public static boolean isStructureCompleted(World world, BlockPos pos) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                IBlockState topState = world.getBlockState(pos.add(x, 1, z));
                if (topState.getBlock() != PainfulBlocks.ITEM_SLOT) {
                    return false;
                }
            }
        }
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                IBlockState bottomState = world.getBlockState(pos.add(x, -1, z));
                if (bottomState.getBlock() != PainfulBlocks.CRAFTING_STAND) {
                    return false;
                }
            }
        }
        return true;
    }
}
