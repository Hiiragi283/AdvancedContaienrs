package hiiragi283.advcont.init

import hiiragi283.advcont.block.ACBlockFurnace
import net.minecraft.block.Block
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

object ACBlocks : IACEntry<Block> {

    val FURNACE = ACBlockFurnace

    override fun register(registry: IForgeRegistry<Block>) {
        FURNACE.register(registry)
    }

    @SideOnly(Side.CLIENT)
    override fun registerModel() {
        FURNACE.registerModel()
    }

    override fun registerOreDict() {
        FURNACE.registerOreDict()
    }

    override fun registerRecipe() {
        FURNACE.registerRecipe()
    }

}