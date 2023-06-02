package hiiragi283.advcont.integration

import hiiragi283.advcont.client.gui.ACGuiBrewery
import hiiragi283.advcont.client.gui.ACGuiFurnace
import hiiragi283.advcont.container.ACContainerBrewery
import hiiragi283.advcont.container.ACContainerFurnace
import hiiragi283.advcont.init.ACBlocks
import mezz.jei.api.IModPlugin
import mezz.jei.api.IModRegistry
import mezz.jei.api.JEIPlugin
import net.minecraft.item.ItemStack

@JEIPlugin
class ACJEIPlugin : IModPlugin {

    override fun register(registry: IModRegistry) {
        //Add click area
        registry.addRecipeClickArea(ACGuiBrewery::class.java, 78, 32, 28, 23, "minecraft.brewing")
        registry.addRecipeClickArea(ACGuiFurnace::class.java, 78, 32, 28, 23, "minecraft.smelting", "minecraft.fuel")
        //Add transfer handler
        val recipeTransferRegistry = registry.recipeTransferRegistry
        recipeTransferRegistry.addRecipeTransferHandler(
            ACContainerFurnace::class.java,
            "minecraft.smelting",
            0,
            1,
            3,
            36
        )
        recipeTransferRegistry.addRecipeTransferHandler(
            ACContainerBrewery::class.java,
            "minecraft.brewing",
            0,
            1,
            3,
            36
        )
        //Add catalysts
        registry.addRecipeCatalyst(ItemStack(ACBlocks.BREWERY), "minecraft.brewing")
        registry.addRecipeCatalyst(ItemStack(ACBlocks.FURNACE), "minecraft.smelting", "minecraft.fuel")
    }

}