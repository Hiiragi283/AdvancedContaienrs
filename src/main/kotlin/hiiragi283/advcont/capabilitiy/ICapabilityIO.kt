package hiiragi283.advcont.capabilitiy

interface ICapabilityIO<T : Any> {

    var ioType: EnumIOType

    fun getIOType(): EnumIOType

    fun setIOType(type: EnumIOType): T

}