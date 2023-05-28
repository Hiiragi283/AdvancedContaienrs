package hiiragi283.advcont.network

import hiiragi283.advcont.AdvancedContainers
import hiiragi283.advcont.proxy.ACGuiHandler
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.relauncher.Side

object ACNetworkRegistry {

    fun register() {
        NetworkRegistry.INSTANCE.registerGuiHandler(AdvancedContainers.Instance, ACGuiHandler)
        AdvancedContainers.CHANNEL.registerMessage(ACMessageHandler.Furnace::class.java, ACMessage.Furnace::class.java, 0, Side.CLIENT)
    }

}