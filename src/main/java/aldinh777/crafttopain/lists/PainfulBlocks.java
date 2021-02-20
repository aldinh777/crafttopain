package aldinh777.crafttopain.lists;

import aldinh777.crafttopain.block.BlockItemSlot;
import aldinh777.crafttopain.block.CraftingCore;
import aldinh777.crafttopain.block.PainfulBlock;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.List;

public class PainfulBlocks {

    public static List<Block> LIST = Lists.newArrayList();

    public static Block CRAFTING_STAND;
    public static Block SMELTING_STAND;
    public static Block CRAFTING_CORE;
    public static Block SMELTING_CORE;
    public static Block ITEM_SLOT;

    public static void init() {
        CRAFTING_STAND = new PainfulBlock(Material.WOOD, "crafting_stand");
        SMELTING_STAND = new PainfulBlock(Material.ROCK, "smelting_stand");
        CRAFTING_CORE = new CraftingCore(Material.WOOD, "crafting_core");
        SMELTING_CORE = new PainfulBlock(Material.ROCK, "smelting_core");

        ITEM_SLOT = new BlockItemSlot(Material.WOOD, "item_slot");
    }
}
