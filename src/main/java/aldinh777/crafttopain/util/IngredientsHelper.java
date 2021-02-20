package aldinh777.crafttopain.util;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class IngredientsHelper {

    private final ArrayList<ItemStack> inputs;
    private final ArrayList<ItemStack> ingredients;
    private final int width;
    private final int height;

    public IngredientsHelper(ArrayList<ItemStack> inputs) {
        this.inputs = inputs;

        boolean[] existsX = new boolean[3];
        boolean[] existsY = new boolean[3];

        for (int i = 0; i < 3; i++) {
            ItemStack stackX1 = inputs.get(i);
            ItemStack stackX2 = inputs.get(i + 3);
            ItemStack stackX3 = inputs.get(i + 6);
            existsX[i] = !stackX1.isEmpty() || !stackX2.isEmpty() || !stackX3.isEmpty();
        }
        for (int i = 0; i < 3; i++) {
            ItemStack stackY1 = inputs.get(i*3);
            ItemStack stackY2 = inputs.get(i*3 + 1);
            ItemStack stackY3 = inputs.get(i*3 + 2);
            existsY[i] = !stackY1.isEmpty() || !stackY2.isEmpty() || !stackY3.isEmpty();
        }

        if (existsX[0] && existsX[2]) {
            this.width = 3;
        } else {
            int width = 0;
            for (boolean exists : existsX) {
                width += exists ? 1 : 0;
            }
            this.width = width;
        }
        if (existsY[0] && existsY[2]) {
            this.height = 3;
        } else {
            int height = 0;
            for (boolean exists : existsY) {
                height += exists ? 1 : 0;
            }
            this.height = height;
        }

        this.ingredients = Lists.newArrayList();
        for (int y = 0; y < 3; y++) {
            if (existsY[y] || (y == 1 && this.height > 1)) {
                for (int x = 0; x < 3; x++) {
                    if (existsX[x] || (x == 1 && this.width > 1)) {
                        this.ingredients.add(inputs.get(y*3 + x));
                    }
                }
            }
        }
    }

    public boolean compareInputs(ArrayList<ItemStack> inputs) {
        for (int i = 0; i < 9; i++) {
            if (!(this.inputs.get(i).getItem() == inputs.get(i).getItem())) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return this.width == 0 || this.height == 0;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public ArrayList<ItemStack> getIngredients() {
        return this.ingredients;
    }
}
