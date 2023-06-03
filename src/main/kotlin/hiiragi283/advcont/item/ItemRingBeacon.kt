package hiiragi283.advcont.item

import baubles.api.BaubleType
import baubles.api.BaublesApi
import baubles.api.IBauble
import hiiragi283.advcont.util.CraftingBuilder
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.SoundEvents
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.common.IRarity
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.fml.common.Optional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
object ItemRingBeacon : ACItemBase("beacon_ring", 0, -1), IBauble {

    init {
        creativeTab = CreativeTabs.MISC
        maxStackSize = 1
    }

    //    General    //

    override fun getForgeRarity(stack: ItemStack): IRarity = EnumRarity.EPIC

    //    Capability    //

    override fun initCapabilities(stack: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider =
        BeaconCapabilityProvider(stack)

    class BeaconCapabilityProvider(val stack: ItemStack) : ICapabilityProvider {

        override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean =
            capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY

        override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
            if (hasCapability(capability, facing)) {
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(BeaconRingHandler(stack))
            } else null

    }

    class BeaconRingHandler(val stack: ItemStack) : ItemStackHandler(1) {

        init {
            setStackInSlot(0, stack)
        }

        fun onClosed() {
            val tag = stack.tagCompound ?: NBTTagCompound()
            tag.setTag("inventory", serializeNBT())
            stack.tagCompound = tag
        }

    }

    //    Event    //

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        if (!world.isRemote) {
            //スニーク時，インベントリを開く
            if (player.isSneaking) {
                openGui(player, world, player.position)
            } else {
                //スニークしていない場合，Baublesのインベントリに自動ではめる
                val baubles = BaublesApi.getBaublesHandler(player)
                for (i in 0 until baubles.slots) {
                    if ((baubles.getStackInSlot(i).isEmpty) && baubles.isItemValidForSlot(i, stack, player)) {
                        baubles.setStackInSlot(i, stack.copy())
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY)
                        }
                        onEquipped(stack, player)
                        break
                    }
                }
            }
        }
        return ActionResult(EnumActionResult.SUCCESS, stack)
    }

    //    IACEntry    //

    override fun registerRecipe() {
        CraftingBuilder(registryName!!, ItemStack(this))
            .setPattern("AB ", "B B", " B ")
            .setIngredient('A', ItemStack(Blocks.BEACON))
            .setIngredient('B', ItemStack(Blocks.DIAMOND_BLOCK))
            .build()
    }

    //    IBauble    //

    override fun getBaubleType(stack: ItemStack): BaubleType = BaubleType.RING

    override fun onEquipped(stack: ItemStack, player: EntityLivingBase) {
        player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.75f, 1.9f)
    }

    override fun onUnequipped(stack: ItemStack, player: EntityLivingBase) {
        player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.75f, 2.0f)
    }

    override fun onWornTick(stack: ItemStack, player: EntityLivingBase) {

    }

}