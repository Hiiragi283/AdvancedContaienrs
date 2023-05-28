package hiiragi283.advcont.network

import hiiragi283.advcont.AdvancedContainers
import hiiragi283.advcont.tile.ACTileFurnace
import net.minecraft.client.Minecraft
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

object ACMessageHandler {

    class Furnace : IMessageHandler<ACMessage.Furnace, IMessage> {

        override fun onMessage(message: ACMessage.Furnace, ctx: MessageContext?): IMessage? {
            val tile = Minecraft.getMinecraft().world.getTileEntity(BlockPos(message.x, message.y, message.z))
            if (tile !== null && tile is ACTileFurnace) {
                tile.burnTime = message.time
                AdvancedContainers.LOGGER.debug("[${tile.displayName.unformattedText}]The burnTime was synced!")
            }
            return null
        }
    }
}