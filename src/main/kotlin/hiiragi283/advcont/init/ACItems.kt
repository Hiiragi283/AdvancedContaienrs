package hiiragi283.advcont.init

import net.minecraft.item.Item
import net.minecraftforge.registries.IForgeRegistry

object ACItems {

    fun register(registry: IForgeRegistry<Item>) {
        ACBlocks.FURNACE.itemBlock?.register(registry)
    }

    fun registerModel() {
        ACBlocks.FURNACE.itemBlock?.registerModel()
    }

}