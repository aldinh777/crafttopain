package aldinh777.crafttopain.block;

import aldinh777.crafttopain.CraftToPain;
import aldinh777.crafttopain.lists.PainfulBlocks;
import aldinh777.crafttopain.tiles.TileItemSlot;
import aldinh777.crafttopain.tiles.TileSmeltingCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public class SmeltingCore extends PainfulBlock {

    public SmeltingCore(Material materialIn, String name) {
        super(materialIn, name);
    }

    @Override
    public boolean hasTileEntity(@Nonnull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileSmeltingCore();
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!isStructureCompleted(worldIn, pos)) {
            return false;
        }
        if (!worldIn.isRemote) {
            playerIn.openGui(CraftToPain.INSTANCE, 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote) {
            TileItemSlot tileEntity = (TileItemSlot) worldIn.getTileEntity(pos);
            if (tileEntity != null) {
                IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

                if (itemHandler != null) {
                    spawnAsEntity(worldIn, pos, itemHandler.getStackInSlot(0));
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    public static boolean isStructureCompleted(World world, BlockPos pos) {
        Block westSlot = world.getBlockState(pos.west()).getBlock();
        Block eastSlot = world.getBlockState(pos.east()).getBlock();

        int smeltingStandCount = 0;

        if (westSlot == PainfulBlocks.ITEM_SLOT && eastSlot == PainfulBlocks.ITEM_SLOT) {
            BlockPos under = pos.down().west();
            for (int i = 0; i < 3; i++) {
                Block stand = world.getBlockState(under).getBlock();
                if (stand == PainfulBlocks.SMELTING_STAND) {
                    smeltingStandCount++;
                }
                under = under.east();
            }
        }

        if (smeltingStandCount == 3) {
            return true;
        }

        smeltingStandCount = 0;

        Block northSlot = world.getBlockState(pos.north()).getBlock();
        Block southSlot = world.getBlockState(pos.south()).getBlock();

        if (northSlot == PainfulBlocks.ITEM_SLOT && southSlot == PainfulBlocks.ITEM_SLOT) {
            BlockPos under = pos.down().north();
            for (int i = 0; i < 3; i++) {
                Block stand = world.getBlockState(under).getBlock();
                if (stand == PainfulBlocks.SMELTING_STAND) {
                    smeltingStandCount++;
                }
                under = under.south();
            }
        }

        return smeltingStandCount == 3;
    }

    public static void getSlots(World world, BlockPos pos, BiConsumer<TileItemSlot, TileItemSlot> consumer) {
        Block westSlot = world.getBlockState(pos.west()).getBlock();
        Block eastSlot = world.getBlockState(pos.east()).getBlock();

        int smeltingStandCount = 0;

        if (westSlot == PainfulBlocks.ITEM_SLOT && eastSlot == PainfulBlocks.ITEM_SLOT) {
            BlockPos under = pos.down().west();
            for (int i = 0; i < 3; i++) {
                Block stand = world.getBlockState(under).getBlock();
                if (stand == PainfulBlocks.SMELTING_STAND) {
                    smeltingStandCount++;
                }
                under = under.east();
            }
        }

        if (smeltingStandCount == 3) {
            TileItemSlot inputSlot = (TileItemSlot) world.getTileEntity(pos.west());
            TileItemSlot outputSlot = (TileItemSlot) world.getTileEntity(pos.east());
            consumer.accept(inputSlot, outputSlot);
            return;
        }

        smeltingStandCount = 0;

        Block northSlot = world.getBlockState(pos.north()).getBlock();
        Block southSlot = world.getBlockState(pos.south()).getBlock();

        if (northSlot == PainfulBlocks.ITEM_SLOT && southSlot == PainfulBlocks.ITEM_SLOT) {
            BlockPos under = pos.down().north();
            for (int i = 0; i < 3; i++) {
                Block stand = world.getBlockState(under).getBlock();
                if (stand == PainfulBlocks.SMELTING_STAND) {
                    smeltingStandCount++;
                }
                under = under.south();
            }
        }

        if (smeltingStandCount == 3) {
            TileItemSlot inputSlot = (TileItemSlot) world.getTileEntity(pos.north());
            TileItemSlot outputSlot = (TileItemSlot) world.getTileEntity(pos.south());
            consumer.accept(inputSlot, outputSlot);
        }
    }
}
