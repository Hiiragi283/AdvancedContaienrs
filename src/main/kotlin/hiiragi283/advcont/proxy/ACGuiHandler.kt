package hiiragi283.advcont.proxy

import hiiragi283.advcont.client.gui.ACGuiAnvil
import hiiragi283.advcont.client.gui.ACGuiBeacon
import hiiragi283.advcont.client.gui.ACGuiBrewery
import hiiragi283.advcont.client.gui.ACGuiFurnace
import hiiragi283.advcont.container.ACContainerAnvil
import hiiragi283.advcont.container.ACContainerBeacon
import hiiragi283.advcont.container.ACContainerBrewery
import hiiragi283.advcont.container.ACContainerFurnace
import hiiragi283.advcont.item.ACItemBeaconRing
import hiiragi283.advcont.tile.ACTileAnvil
import hiiragi283.advcont.tile.ACTileBrewery
import hiiragi283.advcont.tile.ACTileFurnace
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

object ACGuiHandler : IGuiHandler {

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        var container: Container? = null
        //ID: 0 -> TileEntity
        when (ID) {
            0 -> {
                world.getTileEntity(BlockPos(x, y, z))?.let {
                    when (it) {
                        is ACTileAnvil -> container = ACContainerAnvil(player.inventory, it)
                        is ACTileBrewery -> container = ACContainerBrewery(player.inventory, it)
                        is ACTileFurnace -> container = ACContainerFurnace(player.inventory, it)
                        else -> {}
                    }
                }
            }
            //ID: 1 -> Item
            1 -> {
                when (player.getHeldItem(EnumHand.MAIN_HAND).item) {
                    is ACItemBeaconRing -> container = ACContainerBeacon(player)
                }
            }

            else -> {}
        }
        return container
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        var gui: GuiContainer? = null
        //ID: 0 -> TileEntity
        when (ID) {
            0 -> {
                world.getTileEntity(BlockPos(x, y, z))?.let {
                    when (it) {
                        is ACTileAnvil -> gui = ACGuiAnvil(player.inventory, it)
                        is ACTileBrewery -> gui = ACGuiBrewery(player.inventory, it)
                        is ACTileFurnace -> gui = ACGuiFurnace(player.inventory, it)
                        else -> {}
                    }
                }
            }
            //ID: 1 -> Item
            1 -> {
                when (player.getHeldItem(EnumHand.MAIN_HAND).item) {
                    is ACItemBeaconRing -> gui = ACGuiBeacon(player)
                }
            }

            else -> {}
        }
        return gui
    }
}