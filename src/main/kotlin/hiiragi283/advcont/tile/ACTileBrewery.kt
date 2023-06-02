package hiiragi283.advcont.tile

import hiiragi283.advcont.capabilitiy.ACCapabilityProvider
import hiiragi283.advcont.capabilitiy.ACItemHandler
import hiiragi283.advcont.capabilitiy.ACItemHandlerWrapper
import hiiragi283.advcont.capabilitiy.EnumIOType
import hiiragi283.advcont.container.ACContainerBrewery
import hiiragi283.advcont.init.ACItems
import hiiragi283.advcont.util.convertPotion
import hiiragi283.advcont.util.dropInventory
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import net.minecraftforge.common.brewing.BrewingRecipeRegistry
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler

class ACTileBrewery : ACTileBase.Tickable(20 * 10), ITileContainer, ITileProvider.Inventory {

    lateinit var input: ACItemHandler<ACTileBrewery>
    lateinit var ingredient: ACItemHandler<ACTileBrewery>
    lateinit var output: ACItemHandler<ACTileBrewery>

    //    Capability    //

    override fun createInventory(): ACCapabilityProvider<IItemHandler> {

        input = object : ACItemHandler<ACTileBrewery>(1, this) {

            override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack =
                if (isItemValid(slot, stack)) super.insertItem(slot, stack, simulate) else stack

            override fun isItemValid(slot: Int, stack: ItemStack): Boolean =
                BrewingRecipeRegistry.isValidInput(stack) || stack.item == ACItems.POTION

        }.setIOType(EnumIOType.INPUT)

        ingredient = object : ACItemHandler<ACTileBrewery>(1, this) {

            override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack =
                if (isItemValid(slot, stack)) super.insertItem(slot, stack, simulate) else stack

            override fun isItemValid(slot: Int, stack: ItemStack): Boolean =
                BrewingRecipeRegistry.isValidIngredient(stack)

        }.setIOType(EnumIOType.INPUT)

        output = ACItemHandler(1, this).setIOType(EnumIOType.OUTPUT)

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

    //    Tickable    //

    override fun onUpdateServer() {
        //inputスロット内のItemStackから完成品を取得
        var stack = input.getStackInSlot(0)
        if (stack.item == ACItems.POTION) stack = convertPotion(stack)
        if (!stack.isEmpty) {
            val ing = ingredient.getStackInSlot(0)
            val list = NonNullList.withSize(1, stack)
            //醸造が実行できる場合
            if (BrewingRecipeRegistry.canBrew(list, ing, intArrayOf(0))) {
                //醸造を行う
                BrewingRecipeRegistry.brewPotions(list, ing, intArrayOf(0))
                //スタック可能なポーションに変換
                val potion = convertPotion(list[0])
                //outputスロットに搬入を試みる
                val excess = output.insertItem(0, potion.copy(), true)
                //余剰分が存在しない，すなわち搬入が成功する場合
                if (excess.isEmpty) {
                    input.extractItem(0, 1, false)
                    ingredient.extractItem(0, 1, false)
                    output.insertItem(0, potion.copy(), false)
                }
            }
        }
    }

    //    ITileContainer    //

    override fun createContainer(playerInventory: InventoryPlayer, player: EntityPlayer) =
        ACContainerBrewery(playerInventory, this)

    override fun getDisplayName(): TextComponentTranslation = super<ITileContainer>.getDisplayName()

    override fun getGuiID(): String = "brewery"

}