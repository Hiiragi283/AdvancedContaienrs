package hiiragi283.advcont.container

import hiiragi283.advcont.tile.ACTileAnvil
import hiiragi283.advcont.util.dropInventory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.items.SlotItemHandler

class ACContainerAnvil(player: InventoryPlayer, tile: ACTileAnvil) : ACContainerBase.Tile<ACTileAnvil>(player, tile) {

    init {
        addSlotToContainer(object : SlotItemHandler(tile.input, 0, 27, 47) {

            override fun onSlotChanged(): Unit = getRepairedStack(tile)

        })
        addSlotToContainer(object : SlotItemHandler(tile.ingredient, 0, 76, 47) {

            override fun onSlotChanged(): Unit = getRepairedStack(tile)

        })
        addSlotToContainer(object : SlotItemHandlerOutput(tile.output, 0, 134, 47) {
            override fun onTake(thePlayer: EntityPlayer, stack: ItemStack): ItemStack {
                onRepair(tile)
                return super.onTake(thePlayer, stack)
            }
        })
        addPlayerInventory(player, 84)
    }

    fun getRepairedStack(tile: ACTileAnvil) {
        val tool = tile.input.getStackInSlot(0).copy()
        val stack = tile.ingredient.getStackInSlot(0).copy()
        //道具と素材がそろっている場合
        if (!tool.isEmpty && !stack.isEmpty) {
            val item = tool.item
            //修理可能な組み合わせの場合
            if (item.getIsRepairable(tool, stack) && stack.count >= 3) {
                //最大まで耐久地を回復させる
                tool.itemDamage = 0
                //outputスロットに搬入を試みる
                val excess = tile.output.insertItem(0, tool, true)
                //余剰分が存在しない，すなわち搬入が成功する場合
                if (excess.isEmpty) tile.output.insertItem(0, tool, false)
            }
        }
        //片方でもかけていたら完成品を取り消す
        else tile.output.setStackInSlot(0, ItemStack.EMPTY)
    }

    fun onRepair(tile: ACTileAnvil) {
        tile.input.extractItem(0, 1, false)
        tile.ingredient.extractItem(0, 3, false)
    }

    override fun transferStackInSlot(player: EntityPlayer, index: Int): ItemStack {
        var stack = ItemStack.EMPTY
        val slot = inventorySlots[index]
        if (slot.hasStack) {
            val stackSlot = slot.stack
            stack = stackSlot.copy()
            when (index) {
                //input, ingredient -> inventory, hotbar
                in 0..1 -> if (!mergeItemStack(
                        stackSlot,
                        tile.inventory.slots,
                        inventorySlots.size,
                        false
                    )
                ) return ItemStack.EMPTY
                //output -> inventory, hotbar, remove input and ingredient
                2 -> {
                    onRepair(tile)
                    if (!mergeItemStack(
                            stackSlot,
                            tile.inventory.slots,
                            inventorySlots.size,
                            false
                        )
                    ) return ItemStack.EMPTY
                }
                //Inventory, HotBar -> input, fuel
                else -> if (!mergeItemStack(stackSlot, 0, 2, false)) return ItemStack.EMPTY
            }
            if (stackSlot.isEmpty) slot.putStack(ItemStack.EMPTY) else slot.onSlotChanged()
        }
        return stack
    }

    override fun onContainerClosed(player: EntityPlayer) {
        super.onContainerClosed(player)
        dropInventory(player.world, player.position, tile.input, tile.ingredient)
    }

}