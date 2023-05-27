package hiiragi283.advcont.tile

import hiiragi283.advcont.capabilitiy.ACCapabilityProvider
import hiiragi283.advcont.capabilitiy.ACItemHandler
import hiiragi283.advcont.capabilitiy.ACItemHandlerWrapper
import hiiragi283.advcont.capabilitiy.EnumIOType
import hiiragi283.advcont.container.ACContainerFurnace
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler

class ACTileFurnace : ACTileBase.Tickable(20 * 10), ITileContainer, ITileProvider.Inventory {

    lateinit var input: ACItemHandler<ACTileFurnace>
    lateinit var fuel: ACItemHandler<ACTileFurnace>
    lateinit var output: ACItemHandler<ACTileFurnace>

    private var burnTime: Int = 0

    fun canBurn(): Boolean = burnTime >= 200

    //    Packet    //

    override fun readFromNBT(compound: NBTTagCompound) {
        burnTime = compound.getInteger("time")
        super.readFromNBT(compound)
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setInteger("time", burnTime)
        return super.writeToNBT(compound)
    }

    //    Capability    //

    override fun createInventory(): ACCapabilityProvider<IItemHandler> {
        input = ACItemHandler(1, this).setIOType(EnumIOType.INPUT)
        fuel = ACItemHandler(1, this).setIOType(EnumIOType.INPUT)
        output = ACItemHandler(1, this).setIOType(EnumIOType.OUTPUT)
        inventory = ACItemHandlerWrapper(input, fuel, output)
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

    //    Tickable    //

    override fun onUpdateServer() {
        //fuelスロット内のItemStackから燃焼時間を取得
        val fuel = fuel.getStackInSlot(0)
        val time = TileEntityFurnace.getItemBurnTime(fuel)
        if (time > 0) {
            burnTime += time
            fuel.shrink(1)
        }
        //inputスロット内のItemStackから完成品を取得
        val input = input.getStackInSlot(0)
        if (!input.isEmpty) {
            val result = FurnaceRecipes.instance().getSmeltingResult(input)
            if (!result.isEmpty) {
                //outputスロットに搬入を試みる
                val excess = output.insertItem(0, result, true)
                //余剰分が存在しない，すなわち搬入が成功する場合 && burnTimeが200以上の場合
                if (excess.isEmpty && canBurn()) {
                    input.shrink(1)
                    output.insertItem(0, result, false)
                    burnTime -= 200
                }
            }
        }
    }

    //    ITileContainer    //

    override fun createContainer(playerInventory: InventoryPlayer, player: EntityPlayer) =
        ACContainerFurnace(playerInventory, this)

    override fun getDisplayName(): TextComponentTranslation = super<ITileContainer>.getDisplayName()

    override fun getGuiID(): String = "furnace"

}