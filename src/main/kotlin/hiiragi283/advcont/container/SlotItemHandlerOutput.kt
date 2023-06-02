package hiiragi283.advcont.container

import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

open class SlotItemHandlerOutput(itemHandler: IItemHandler, index: Int, x: Int, y: Int) :
    SlotItemHandler(itemHandler, index, x, y) {

    //搬出専用なので搬入不可能
    override fun isItemValid(stack: ItemStack) = false

}