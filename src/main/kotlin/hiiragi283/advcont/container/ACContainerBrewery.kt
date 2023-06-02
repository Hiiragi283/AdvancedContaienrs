package hiiragi283.advcont.container

import hiiragi283.advcont.tile.ACTileBrewery
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.items.SlotItemHandler

class ACContainerBrewery(player: InventoryPlayer, tile: ACTileBrewery) : ACContainerBase<ACTileBrewery>(player, tile) {

    init {
        addSlotToContainer(SlotItemHandler(tile.input, 0, 56, 53))
        addSlotToContainer(SlotItemHandler(tile.ingredient, 0, 56, 17))
        addSlotToContainer(SlotItemHandlerOutput(tile.output, 0, 116, 35))
        addPlayerInventory(player, 84)
    }

    override fun transferStackInSlot(player: EntityPlayer, index: Int): ItemStack {
        var stack = ItemStack.EMPTY
        val slot = inventorySlots[index]
        if (slot.hasStack) {
            val stackSlot = slot.stack
            stack = stackSlot.copy()
            when (index) {
                //ingredient, input, output -> inventory, hotbar
                in 0..2 -> if (!mergeItemStack(
                        stackSlot,
                        tile.inventory.slots,
                        inventorySlots.size,
                        false
                    )
                ) return ItemStack.EMPTY
                //Inventory, HotBar -> input, fuel
                else -> if (!mergeItemStack(stackSlot, 0, 2, false)) return ItemStack.EMPTY
            }
            if (stackSlot.isEmpty) slot.putStack(ItemStack.EMPTY) else slot.onSlotChanged()
        }
        return stack
    }

}