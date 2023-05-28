package hiiragi283.advcont.proxy

import hiiragi283.advcont.ACEventHandler
import hiiragi283.advcont.init.ACBlocks
import hiiragi283.advcont.init.ACItems
import hiiragi283.advcont.network.ACNetworkRegistry
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.*

abstract class CommonProxy : IProxy {

    override fun onConstruct(event: FMLConstructionEvent) {
        MinecraftForge.EVENT_BUS.register(ACEventHandler)
    }

    override fun onPreInit(event: FMLPreInitializationEvent) {}

    override fun onInit(event: FMLInitializationEvent) {
        ACBlocks.registerOreDict()
        ACBlocks.registerRecipe()
        ACItems.registerOreDict()
        ACItems.registerRecipe()
    }

    override fun onPostInit(event: FMLPostInitializationEvent) {}

    override fun onComplete(event: FMLLoadCompleteEvent) {
        ACNetworkRegistry.register()
    }

}