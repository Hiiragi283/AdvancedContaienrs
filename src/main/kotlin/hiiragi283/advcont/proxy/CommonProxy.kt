package hiiragi283.advcont.proxy

import net.minecraftforge.fml.common.event.*

abstract class CommonProxy : IProxy{

    override fun onConstruct(event: FMLConstructionEvent) {}
    override fun onPreInit(event: FMLPreInitializationEvent) {}
    override fun onInit(event: FMLInitializationEvent) {}
    override fun onPostInit(event: FMLPostInitializationEvent) {}
    override fun onComplete(event: FMLLoadCompleteEvent) {}

}