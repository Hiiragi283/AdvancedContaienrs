package hiiragi283.advcont.util

import hiiragi283.advcont.block.ACBlockBase
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader

object ModelUtil {

    private fun getLocation(location: ResourceLocation?, meta: Int): ModelResourceLocation {
        val builder = StringBuilder().also {
            it.append(location.toString())
            it.append("_")
            it.append(meta)
        }
        return ModelResourceLocation(String(builder), "inventory")
    }

    fun setItemModel(
        item: Item,
        meta: Int = 0,
        location: ModelResourceLocation = getLocation(item.registryName, meta)
    ) {
        ModelLoader.setCustomModelResourceLocation(item, meta, location)
    }

    fun setItemModel(stack: ItemStack): Unit = setItemModel(stack.item, stack.metadata)

    fun setItemModel(block: ACBlockBase, meta: Int = 0): Unit = setItemModel(Item.getItemFromBlock(block), meta)

}