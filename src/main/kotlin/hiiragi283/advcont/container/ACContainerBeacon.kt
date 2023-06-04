package hiiragi283.advcont.container

import hiiragi283.advcont.capabilitiy.ACItemHandler
import hiiragi283.advcont.item.IBeaconAugment
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraftforge.items.SlotItemHandler

class ACContainerBeacon(val player: EntityPlayer) : ACContainerBase() {

    private val handler = ACItemHandler.Item(1, player.getHeldItem(EnumHand.MAIN_HAND))

    init {
        addSlotToContainer(object : SlotItemHandler(handler, 0, 27, 47) {

            //IBeaconAugmentを実装したItemのみ通す
            override fun isItemValid(stack: ItemStack): Boolean = stack.item is IBeaconAugment

        })
        addPlayerInventory(player.inventory, 84)
    }

    override fun transferStackInSlot(player: EntityPlayer, index: Int): ItemStack {
        var stack = ItemStack.EMPTY
        val slot = inventorySlots[index]
        if (slot.hasStack) {
            val stackSlot = slot.stack
            stack = stackSlot.copy()
            when (index) {
                //input -> inventory, hotbar
                0 -> if (!mergeItemStack(stackSlot, 1, inventorySlots.size, false)) return ItemStack.EMPTY
                //Inventory, HotBar -> input
                else -> if (!mergeItemStack(stackSlot, 0, 1, false)) return ItemStack.EMPTY
            }
            if (stackSlot.isEmpty) slot.putStack(ItemStack.EMPTY) else slot.onSlotChanged()
        }
        return stack
    }


    override fun onContainerClosed(playerIn: EntityPlayer) {
        super.onContainerClosed(playerIn)
        handler.onClosed()
    }

}