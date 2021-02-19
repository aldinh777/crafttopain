package aldinh777.crafttopain.block;

import aldinh777.crafttopain.lists.PainfulBlocks;
import aldinh777.crafttopain.lists.PainfulItems;
import aldinh777.crafttopain.lists.PainfulTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class PainfulBlock extends Block {

    public PainfulBlock(Material materialIn, String name) {
        super(materialIn);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(PainfulTabs.PAIN_TABS);

        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(name);

        PainfulBlocks.LIST.add(this);
        PainfulItems.LIST.add(itemBlock);
    }
}
