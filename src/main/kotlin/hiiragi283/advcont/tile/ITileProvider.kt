package hiiragi283.advcont.tile

import hiiragi283.advcont.capabilitiy.ACCapabilityProvider
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.items.IItemHandler

object ITileProvider {

    interface Inventory {
        fun createInventory(): ACCapabilityProvider<IItemHandler>
    }

    interface Tank {
        fun createTank(): ACCapabilityProvider<IFluidHandler>
    }

}