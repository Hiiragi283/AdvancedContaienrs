package hiiragi283.advcont.proxy

import hiiragi283.advcont.ACEventHandler
import hiiragi283.advcont.AdvancedContainers
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.*
import net.minecraftforge.fml.common.network.NetworkRegistry

abstract class CommonProxy : IProxy {

    override fun onConstruct(event: FMLConstructionEvent) {
        MinecraftForge.EVENT_BUS.register(ACEventHandler)
    }

    override fun onPreInit(event: FMLPreInitializationEvent) {}

    override fun onInit(event: FMLInitializationEvent) {
        NetworkRegistry.INSTANCE.registerGuiHandler(AdvancedContainers.Instance, ACGuiHandler)
    }

    override fun onPostInit(event: FMLPostInitializationEvent) {}

    override fun onComplete(event: FMLLoadCompleteEvent) {}

}