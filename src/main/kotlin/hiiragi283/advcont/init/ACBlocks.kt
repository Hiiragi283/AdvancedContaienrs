package hiiragi283.advcont.init

import hiiragi283.advcont.block.ACBlockBase
import hiiragi283.advcont.block.ACBlockFurnace
import net.minecraft.block.Block
import net.minecraftforge.registries.IForgeRegistry

object ACBlocks {

    val FURNACE: ACBlockBase = ACBlockFurnace

    fun register(registry: IForgeRegistry<Block>) {
        FURNACE.register(registry)
    }

}