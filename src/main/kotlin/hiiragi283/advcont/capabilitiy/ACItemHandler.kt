package hiiragi283.advcont.capabilitiy

import hiiragi283.advcont.tile.ACTileBase
import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler

open class ACItemHandler<T : ACTileBase>(slots: Int, val tile: T) : ItemStackHandler(slots),
    ICapabilityIO<ACItemHandler<*>> {

    override fun onContentsChanged(slot: Int) {
        tile.markDirty()
    }

    //    ICapabilityIO    //

    override var ioType = EnumIOType.INPUT

    override fun getIOType(): EnumIOType = ioType

    override fun setIOType(type: EnumIOType): ACItemHandler<T> = also { ioType = type }

    //    Custom    //

    open fun isEmpty(): Boolean {
        var result = 0
        for (slot in 0 until slots) {
            val stack = getStackInSlot(slot)
            if (stack.isEmpty) result++
        }
        return result == slots
    }

    open fun clear(): Unit = clear(0 until slots)

    open fun clear(range: IntRange) {
        for (slot in range) {
            setStackInSlot(slot, ItemStack.EMPTY)
        }
    }
}