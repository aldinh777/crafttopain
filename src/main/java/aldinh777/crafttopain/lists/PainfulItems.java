package aldinh777.crafttopain.lists;

import aldinh777.crafttopain.item.PainfulItem;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;

import java.util.List;

public class PainfulItems {

    public static List<Item> LIST = Lists.newArrayList();

    public static Item TOOL_BODY;
    public static Item TOOL_HANDLE;

    public static Item WOODEN_BIND;
    public static Item WOODEN_BLADE;
    public static Item STONE_BIND;
    public static Item STONE_BLADE;
    public static Item IRON_BIND;
    public static Item IRON_BLADE;
    public static Item GOLDEN_BIND;
    public static Item GOLDEN_BLADE;
    public static Item DIAMOND_BIND;
    public static Item DIAMOND_BLADE;

    public static void init() {
        TOOL_BODY = new PainfulItem("tool_body");
        TOOL_HANDLE = new PainfulItem("tool_handle");

        WOODEN_BIND = new PainfulItem("wooden_bind");
        STONE_BIND = new PainfulItem("stone_bind");
        IRON_BIND = new PainfulItem("iron_bind");
        GOLDEN_BIND = new PainfulItem("golden_bind");
        DIAMOND_BIND = new PainfulItem("diamond_bind");

        WOODEN_BLADE = new PainfulItem("wooden_blade");
        STONE_BLADE = new PainfulItem("stone_blade");
        IRON_BLADE = new PainfulItem("iron_blade");
        GOLDEN_BLADE = new PainfulItem("golden_blade");
        DIAMOND_BLADE = new PainfulItem("diamond_blade");
    }
}
