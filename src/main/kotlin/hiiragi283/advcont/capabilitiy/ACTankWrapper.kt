package hiiragi283.advcont.capabilitiy

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraftforge.common.util.Constants
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fluids.capability.IFluidTankProperties

class ACTankWrapper(private vararg val tanks: ACTank) : IFluidHandler, INBTSerializable<NBTTagCompound> {

    fun getFluid(index: Int): FluidStack? = tanks[index].fluid

    fun getCapacity(index: Int): FluidStack? = tanks[index].fluid

    //    IFluidHandler    //

    override fun getTankProperties(): Array<IFluidTankProperties> {
        val properties: MutableList<IFluidTankProperties> = mutableListOf()
        tanks.forEach {
            properties.add(it.tankProperties[0])
        }
        return properties.toTypedArray()
    }

    override fun fill(resource: FluidStack?, doFill: Boolean): Int {
        val result1 = tanks.firstOrNull {
            if (it.getIOType().canInput) {
                val result1 = it.fill(resource, false)
                result1 > 0
            } else false
        }
        return result1?.fill(resource, doFill) ?: 0
    }

    override fun drain(resource: FluidStack?, doDrain: Boolean): FluidStack? {
        val result1 = tanks.firstOrNull {
            if (it.getIOType().canOutput) {
                val result1 = it.drain(resource, false)
                result1 !== null
            } else false
        }
        return result1?.drain(resource, doDrain)
    }

    //最初に登録したtankを返す
    override fun drain(maxDrain: Int, doDrain: Boolean): FluidStack? = tanks[0].drain(maxDrain, doDrain)

    //    INBTSerializable<NBTTagCompound>    //

    override fun serializeNBT(): NBTTagCompound {
        val tagList = NBTTagList()
        tanks.forEach {
            val stack = it.fluid
            NBTTagCompound().also { tag ->
                stack?.writeToNBT(tag)
                tagList.appendTag(tag)
            }
        }
        return NBTTagCompound().also { it.setTag("Fluids", tagList) }
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        val tagList = nbt.getTagList("Fluids", Constants.NBT.TAG_COMPOUND)
        for (i in 0 until tagList.tagCount()) {
            val tag = tagList.getCompoundTagAt(i)
            tanks[i].fluid = FluidStack.loadFluidStackFromNBT(tag)
        }
    }
}