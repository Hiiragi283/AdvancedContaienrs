package hiiragi283.advcont.init

import hiiragi283.advcont.item.ACItemPotion
import net.minecraft.client.renderer.color.BlockColors
import net.minecraft.client.renderer.color.ItemColors
import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

object ACItems : IACEntry<Item> {

    val POTION = ACItemPotion

    override fun register(registry: IForgeRegistry<Item>) {
        ACBlocks.ANVIL.itemBlock?.register(registry)
        ACBlocks.BREWERY.itemBlock?.register(registry)
        ACBlocks.FURNACE.itemBlock?.register(registry)

        ACItemPotion.register(registry)
    }

    override fun registerOreDict() {
        ACItemPotion.registerOreDict()
    }

    override fun registerRecipe() {
        ACItemPotion.registerRecipe()
    }

    @SideOnly(Side.CLIENT)
    override fun registerColorBlock(blockColors: BlockColors) {

    }

    @SideOnly(Side.CLIENT)
    override fun registerColorItem(itemColors: ItemColors) {
        POTION.registerColorItem(itemColors)
    }

    @SideOnly(Side.CLIENT)
    override fun registerModel() {
        ACItemPotion.registerModel()
    }

}