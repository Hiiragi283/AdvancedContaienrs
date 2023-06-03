package hiiragi283.advcont.init

import hiiragi283.advcont.item.ACItemPotion
import hiiragi283.advcont.item.ItemRingBeacon
import net.minecraft.client.renderer.color.BlockColors
import net.minecraft.client.renderer.color.ItemColors
import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

object ACItems : IACEntry<Item> {

    val BEACON_RING = ItemRingBeacon
    val POTION = ACItemPotion

    override fun register(registry: IForgeRegistry<Item>) {
        ACBlocks.ANVIL.itemBlock?.register(registry)
        ACBlocks.BREWERY.itemBlock?.register(registry)
        ACBlocks.FURNACE.itemBlock?.register(registry)

        BEACON_RING.register(registry)
        POTION.register(registry)
    }

    override fun registerOreDict() {
        BEACON_RING.registerOreDict()
        POTION.registerOreDict()
    }

    override fun registerRecipe() {
        BEACON_RING.registerRecipe()
        POTION.registerRecipe()
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
        BEACON_RING.registerModel()
        POTION.registerModel()
    }

}