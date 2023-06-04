package hiiragi283.advcont;

import hiiragi283.advcont.init.ACItems;
import hiiragi283.advcont.util.ACUtil;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

public class RecipePotionStackable extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    ItemStack POTION = ItemStack.EMPTY;

    //f**k Thermal Expansion
    @Override
    public boolean matches(@NotNull InventoryCrafting inv, World worldIn) {
        int countPotion = 0;
        int countEmpty = 0;
        for (int i = 0; i < 8; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                if (item == Items.POTIONITEM || item == ACItems.INSTANCE.getPOTION()) {
                    countPotion++;
                    POTION = stack;
                }
            } else {
                countEmpty++;
            }
        }
        return countPotion == 1 && countEmpty == 8;
    }

    @NotNull
    @Override
    public ItemStack getCraftingResult(@NotNull InventoryCrafting inv) {
        return ACUtil.convertPotion(POTION);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @NotNull
    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

}