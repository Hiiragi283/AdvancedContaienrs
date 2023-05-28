package hiiragi283.advcont.init

import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

interface IACEntry<T : IForgeRegistryEntry.Impl<T>> {

    fun register(registry: IForgeRegistry<T>)

    @SideOnly(Side.CLIENT)
    fun registerModel()

    fun registerOreDict()

    fun registerRecipe()

}