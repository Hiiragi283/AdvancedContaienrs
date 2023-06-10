package hiiragi283.advcont.recipe

import hiiragi283.advcont.init.ACItems
import hiiragi283.advcont.util.convertPotion
import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistryEntry

object RecipePotionStackable : IForgeRegistryEntry.Impl<IRecipe?>(), IRecipe {

    private var potion: ItemStack = ItemStack.EMPTY

    override fun matches(inv: InventoryCrafting, worldIn: World): Boolean {
        var countPotion = 0
        var countEmpty = 0
        IntRange(0, 8).forEach {
            val stack = inv.getStackInSlot(it)
            if (!stack.isEmpty) {
                val item = stack.item
                if (item === Items.POTIONITEM || item === ACItems.POTION) {
                    countPotion++
                    potion = stack
                }
            } else {
                countEmpty++
            }
        }
        return countPotion == 1 && countEmpty == 8
    }

    override fun getCraftingResult(inv: InventoryCrafting): ItemStack = convertPotion(potion)

    override fun canFit(width: Int, height: Int): Boolean = width >= 3 && height >= 3

    override fun getRecipeOutput(): ItemStack = ItemStack.EMPTY

}