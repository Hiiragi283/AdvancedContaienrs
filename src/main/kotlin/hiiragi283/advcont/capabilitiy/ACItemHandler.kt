package hiiragi283.advcont.capabilitiy

import hiiragi283.advcont.tile.ACTileBase
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.items.ItemStackHandler

sealed class ACItemHandler(slots: Int) : ItemStackHandler(slots), ICapabilityIO<ACItemHandler> {

    //    ICapabilityIO    //

    override var ioType = EnumIOType.INPUT

    override fun getIOType(): EnumIOType = ioType

    override fun setIOType(type: EnumIOType): ACItemHandler = also { ioType = type }

    //    Custom    //

    /*open fun isEmpty(): Boolean {
        var result = 0
        IntRange(0, slots - 1).forEach {
            val stack = getStackInSlot(it)
            if (stack.isEmpty) result++
        }
        return result == slots
    }*/

    open fun isEmpty(): Boolean = (0 until slots - 1)
        .toList()
        .map { getStackInSlot(it) }
        .all { it.isEmpty }

    open fun clear(): Unit = clear(0 until slots)

    open fun clear(range: IntRange) {
        range.forEach { setStackInSlot(it, ItemStack.EMPTY) }
    }

    //    For ItemStack    //

    class Item(slots: Int, private val parent: ItemStack) : ACItemHandler(slots) {

        init {
            deserializeNBT(parent.tagCompound?.getCompoundTag("inventory") ?: NBTTagCompound())
        }

        fun onClosed() {
            val tag = parent.tagCompound ?: NBTTagCompound()
            tag.setTag("inventory", serializeNBT())
            parent.tagCompound = tag
        }

    }

    //    For TileEntity    //

    open class Tile<T : ACTileBase>(slots: Int, val tile: T) : ACItemHandler(slots) {

        override fun onContentsChanged(slot: Int): Unit = tile.markDirty()

        override fun setIOType(type: EnumIOType): Tile<T> = also { ioType = type }

    }

}