package hiiragi283.advcont

import hiiragi283.advcont.proxy.CommonProxy
import hiiragi283.advcont.proxy.IProxy
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(
    modid = AdvancedContainers.MOD_ID,
    name = AdvancedContainers.MOD_NAME,
    version = AdvancedContainers.VERSION,
    acceptedMinecraftVersions = "[1.12, 1.12.2]",
    modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter"
)
object AdvancedContainers : IProxy {

    const val MOD_ID: String = "advanced_containers"
    const val MOD_NAME: String = "Advanced Containers"
    const val VERSION: String = "0.0.1"

    val LOGGER: Logger = LogManager.getLogger(MOD_NAME)

    @JvmStatic
    @Mod.Instance(MOD_ID)
    lateinit var Instance: AdvancedContainers

    @SidedProxy(
        clientSide = "hiiragi283.advcont.proxy.ClientProxy",
        serverSide = "hiiragi283.advcont.proxy.ServerProxy"
    )
    lateinit var proxy: CommonProxy

    @Mod.EventHandler
    override fun onConstruct(event: FMLConstructionEvent): Unit = proxy.onConstruct(event)

    @Mod.EventHandler
    override fun onPreInit(event: FMLPreInitializationEvent): Unit = proxy.onPreInit(event)

    @Mod.EventHandler
    override fun onInit(event: FMLInitializationEvent): Unit = proxy.onInit(event)

    @Mod.EventHandler
    override fun onPostInit(event: FMLPostInitializationEvent): Unit = proxy.onPostInit(event)

    @Mod.EventHandler
    override fun onComplete(event: FMLLoadCompleteEvent): Unit = proxy.onComplete(event)

}