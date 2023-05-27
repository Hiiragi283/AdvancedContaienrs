package hiiragi283.advcont.proxy

import hiiragi283.advcont.client.gui.ACGuiFurnace
import hiiragi283.advcont.container.ACContainerFurnace
import hiiragi283.advcont.tile.ACTileFurnace
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

object ACGuiHandler : IGuiHandler {

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        var container: Container? = null
        if (ID == 0) {
            world.getTileEntity(BlockPos(x, y, z))?.let {
                if (it is ACTileFurnace) container = ACContainerFurnace(player.inventory, it)
            }
        }
        return container
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        var gui: GuiContainer? = null
        if (ID == 0) {
            world.getTileEntity(BlockPos(x, y, z))?.let {
                if (it is ACTileFurnace) gui = ACGuiFurnace(player.inventory, it)
            }
        }
        return gui
    }
}