package hiiragi283.advcont.item

import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

interface IACItem {

    fun register(registry: IForgeRegistry<Item>)

    @SideOnly(Side.CLIENT)
    fun registerModel()

    fun registerOreDict()

    fun registerRecipe()

}