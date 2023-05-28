package hiiragi283.advcont.init

import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

object ACItems : IACEntry<Item> {

    override fun register(registry: IForgeRegistry<Item>) {
        ACBlocks.FURNACE.itemBlock?.register(registry)
    }

    @SideOnly(Side.CLIENT)
    override fun registerModel() {

    }

    override fun registerOreDict() {

    }

    override fun registerRecipe() {

    }

}