package aldinh777.crafttopain.lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class PainfulTabs extends CreativeTabs {

    public static final PainfulTabs PAIN_TABS = new PainfulTabs("crafttopain");

    public PainfulTabs(String label) {
        super(label);
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(PainfulItems.TOOL_HANDLE);
    }
}
