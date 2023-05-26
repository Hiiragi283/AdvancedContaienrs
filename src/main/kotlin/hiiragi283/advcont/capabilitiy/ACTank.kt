package hiiragi283.advcont.capabilitiy

import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTank

open class ACTank(cap: Int) : FluidTank(cap), ICapabilityIO<ACTank>, INBTSerializable<NBTTagCompound> {

    //    ICapabilityIO    //

    override var ioType: EnumIOType = EnumIOType.INPUT

    override fun getIOType(): EnumIOType = ioType

    override fun setIOType(type: EnumIOType): ACTank = also { ioType = type }

    //    INBTSerializable    //

    override fun serializeNBT() = NBTTagCompound().also { fluid?.writeToNBT(it) }

    override fun deserializeNBT(tag: NBTTagCompound?) {
        fluid = FluidStack.loadFluidStackFromNBT(tag ?: NBTTagCompound())
    }

}