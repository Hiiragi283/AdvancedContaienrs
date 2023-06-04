package hiiragi283.advcont.tile

import hiiragi283.advcont.capabilitiy.ACCapabilityProvider
import hiiragi283.advcont.capabilitiy.ACItemHandler
import hiiragi283.advcont.capabilitiy.ACItemHandlerWrapper
import hiiragi283.advcont.capabilitiy.EnumIOType
import hiiragi283.advcont.container.ACContainerAnvil
import hiiragi283.advcont.util.dropInventory
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler

class ACTileAnvil : ACTileBase(), ITileContainer, ITileProvider.Inventory {

    lateinit var input: ACItemHandler.Tile<ACTileAnvil>
    lateinit var ingredient: ACItemHandler.Tile<ACTileAnvil>
    lateinit var output: ACItemHandler.Tile<ACTileAnvil>

    //    Capability    //

    override fun createInventory(): ACCapabilityProvider<IItemHandler> {
        input = ACItemHandler.Tile(1, this).setIOType(EnumIOType.CATALYST)
        ingredient = ACItemHandler.Tile(1, this).setIOType(EnumIOType.CATALYST)
        output = ACItemHandler.Tile(1, this).setIOType(EnumIOType.CATALYST)
        inventory = ACItemHandlerWrapper(input, ingredient, output)
        return ACCapabilityProvider(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory, inventory)
    }

    //    ACTileBase    //

    override fun onTileActivated(
        world: World,
        pos: BlockPos,
        player: EntityPlayer,
        hand: EnumHand,
        facing: EnumFacing
    ): Boolean {
        if (!world.isRemote) openGui(player, world, pos)
        return true
    }

    override fun onTileRemoved(world: World, pos: BlockPos, state: IBlockState): Unit =
        dropInventory(world, pos, inventory)

    //    ITileContainer    //

    override fun createContainer(playerInventory: InventoryPlayer, player: EntityPlayer) =
        ACContainerAnvil(playerInventory, this)

    override fun getDisplayName(): TextComponentTranslation = super<ITileContainer>.getDisplayName()

    override fun getGuiID(): String = "anvil"

}