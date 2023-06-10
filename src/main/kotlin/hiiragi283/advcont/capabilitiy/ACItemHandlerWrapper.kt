package hiiragi283.advcont.capabilitiy

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraftforge.common.util.Constants
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.IItemHandlerModifiable

/**
 * RagiItemHandlerとスロットの番号を紐づけたPairの一覧を作ることで，複数のRagiItemHandlerをまとめて処理できる
 * Thanks to SkyTheory!
 */

open class ACItemHandlerWrapper(vararg itemHandlers: ACItemHandler) : IItemHandler, IItemHandlerModifiable,
    INBTSerializable<NBTTagCompound> {

    private val pairs: MutableList<Pair<ACItemHandler, Int>> = mutableListOf()

    init {
        itemHandlers.forEach { handler -> (0 until handler.slots).forEach { pairs.add(handler to it) } }
    }

    //    Slot    //

    override fun getSlots(): Int = pairs.size

    override fun getStackInSlot(slot: Int): ItemStack {
        val pair = getSlotHandler(slot)
        return pair.first.getStackInSlot(pair.second)
    }

    override fun setStackInSlot(slot: Int, stack: ItemStack) {
        val pair = getSlotHandler(slot)
        pair.first.setStackInSlot(pair.second, stack)
    }

    override fun getSlotLimit(slot: Int): Int = 64

    private fun getSlotHandler(slot: Int): Pair<ACItemHandler, Int> = pairs[slot]

    //    Extraction    //

    //指定したスロットが搬出可能な場合のみアイテムを取り出すメソッド
    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
        return if (canExtract(slot)) {
            val pair = getSlotHandler(slot)
            pair.first.extractItem(pair.second, amount, simulate)
        } else ItemStack.EMPTY
    }

    //アイテムを取り出せるか判定するメソッド
    fun canExtract(slot: Int): Boolean = getSlotHandler(slot).first.getIOType().canOutput

    //    Insertion    //

    //指定したスロットが搬入可能な場合のみアイテムを入れるメソッド
    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
        return if (canInsert(slot)) {
            val pair = getSlotHandler(slot)
            pair.first.insertItem(pair.second, stack, simulate)
        } else stack
    }

    //アイテムを入れられるか判定するメソッド
    fun canInsert(slot: Int): Boolean = getSlotHandler(slot).first.getIOType().canInput

    //    INBTSerializable    //

    override fun serializeNBT(): NBTTagCompound {
        val nbtTagList = NBTTagList()
        pairs.indices.forEach {
            val pair = getSlotHandler(it)
            val stack = pair.first.getStackInSlot(pair.second)
            if (!stack.isEmpty) NBTTagCompound().also { tag ->
                tag.setInteger("Slot", it)
                stack.writeToNBT(tag)
                nbtTagList.appendTag(tag)
            }
        }
        return NBTTagCompound().also { it.setTag("Items", nbtTagList) }
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        val tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND)
        (0 until tagList.tagCount()).forEach {
            val tag = tagList.getCompoundTagAt(it)
            val slot = tag.getInteger("Slot")
            if (slot in 0 until pairs.size) {
                val pair = getSlotHandler(slot)
                pair.first.setStackInSlot(pair.second, ItemStack(tag))
            }
        }
    }

    //    Custom    //

    fun isEmpty(): Boolean = (0 until slots - 1)
        .toList()
        .map { getStackInSlot(it) }
        .all { it.isEmpty }

    fun clear(): Unit = clear(0 until slots)

    fun clear(range: IntRange) {
        range.forEach { setStackInSlot(it, ItemStack.EMPTY) }
    }

}