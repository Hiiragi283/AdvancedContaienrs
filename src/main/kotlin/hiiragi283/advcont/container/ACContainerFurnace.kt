package hiiragi283.advcont.container

import hiiragi283.advcont.tile.ACTileFurnace
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraftforge.items.SlotItemHandler

class ACContainerFurnace(player: InventoryPlayer, tile: ACTileFurnace) : ACContainerBase<ACTileFurnace>(player, tile) {

    init {
        addSlotToContainer(object : SlotItemHandler(tile.fuel, 0, 56, 53) {
            override fun isItemValid(stack: ItemStack): Boolean = TileEntityFurnace.isItemFuel(stack)
        })
        addSlotToContainer(SlotItemHandler(tile.input, 0, 56, 17))
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
                //fuel, input, output -> inventory, hotbar
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