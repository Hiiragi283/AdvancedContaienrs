package hiiragi283.advcont

import hiiragi283.advcont.init.ACBlocks
import hiiragi283.advcont.init.ACItems
import hiiragi283.advcont.recipe.RecipePotionStackable
import hiiragi283.advcont.tile.ITileProvider
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.crafting.IRecipe
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * Thanks to SkyTheory!
 * Reference: https://github.com/SkyTheory/SkyTheoryLib/blob/1.12.2/java/skytheory/lib/event/CapabilityEvent.java
 */

object ACEventHandler {

    private val keyInventory = ResourceLocation(AdvancedContainers.MOD_ID, "inventory")
    private val keyTank = ResourceLocation(AdvancedContainers.MOD_ID, "tank")

    @SubscribeEvent
    fun attachCapability(event: AttachCapabilitiesEvent<TileEntity>) {
        val tile = event.`object`
        if (tile is ITileProvider.Inventory) event.addCapability(keyInventory, tile.createInventory())
        if (tile is ITileProvider.Tank) event.addCapability(keyTank, tile.createTank())
    }

    //    Registry    //

    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        ACBlocks.register(event.registry)
    }

    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        ACItems.register(event.registry)
    }

    @SubscribeEvent
    fun registerRecipe(event: RegistryEvent.Register<IRecipe>) {
        val registry = event.registry
        registry.register(RecipePotionStackable.setRegistryName(AdvancedContainers.MOD_ID, "potion"))
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun registerColorBlock(event: ColorHandlerEvent.Block) {
        ACBlocks.registerColorBlock(event.blockColors)
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun registerColorItem(event: ColorHandlerEvent.Item) {
        ACItems.registerColorBlock(event.blockColors)
        ACItems.registerColorItem(event.itemColors)
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun registerModel(event: ModelRegistryEvent) {
        ACBlocks.registerModel()
        ACItems.registerModel()
    }

}