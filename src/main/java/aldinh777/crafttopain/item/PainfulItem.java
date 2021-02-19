package aldinh777.crafttopain.item;

import aldinh777.crafttopain.lists.PainfulItems;
import aldinh777.crafttopain.lists.PainfulTabs;
import net.minecraft.item.Item;

public class PainfulItem extends Item {

    public PainfulItem (String name) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(PainfulTabs.PAIN_TABS);
        PainfulItems.LIST.add(this);
    }
}
