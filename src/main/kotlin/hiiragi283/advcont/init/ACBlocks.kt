package hiiragi283.advcont.init

import hiiragi283.advcont.block.ACBlockBrewery
import hiiragi283.advcont.block.ACBlockFurnace
import net.minecraft.block.Block
import net.minecraft.client.renderer.color.BlockColors
import net.minecraft.client.renderer.color.ItemColors
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

object ACBlocks : IACEntry<Block> {

    val BREWERY = ACBlockBrewery
    val FURNACE = ACBlockFurnace

    override fun register(registry: IForgeRegistry<Block>) {
        BREWERY.register(registry)
        FURNACE.register(registry)
    }

    override fun registerOreDict() {
        BREWERY.registerOreDict()
        FURNACE.registerOreDict()
    }

    override fun registerRecipe() {
        BREWERY.registerRecipe()
        FURNACE.registerRecipe()
    }

    @SideOnly(Side.CLIENT)
    override fun registerColorBlock(blockColors: BlockColors) {

    }

    @SideOnly(Side.CLIENT)
    override fun registerColorItem(itemColors: ItemColors) {

    }

    @SideOnly(Side.CLIENT)
    override fun registerModel() {
        BREWERY.registerModel()
        FURNACE.registerModel()
    }

}