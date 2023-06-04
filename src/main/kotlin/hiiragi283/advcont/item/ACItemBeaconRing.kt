package hiiragi283.advcont.item

import baubles.api.BaubleType
import baubles.api.BaublesApi
import baubles.api.IBauble
import hiiragi283.advcont.capabilitiy.ACItemHandler
import hiiragi283.advcont.util.CraftingBuilder
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.SoundEvents
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.common.IRarity
import net.minecraftforge.fml.common.Optional
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
object ACItemBeaconRing : ACItemBase("beacon_ring", 0, -1), IBauble {

    init {
        maxStackSize = 1
    }

    //    General    //

    override fun getForgeRarity(stack: ItemStack): IRarity = EnumRarity.EPIC

    //    Client    //

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)
        tooltip.add(I18n.format("=== Effect ==="))
        tooltip.add(
            I18n.format(
                TextFormatting.AQUA.toString() + getPotion(stack)?.name?.trim()?.let { I18n.format(it) })
        )
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
        val handler = ACItemHandler.Item(1, stack)
        val augment = handler.getStackInSlot(0)
        val item = augment.item
        if (item is IBeaconAugment) item.getEffect(augment)?.let { player.addPotionEffect(it) }
    }

    private fun getPotion(stack: ItemStack): Potion? {
        val handler = ACItemHandler.Item(1, stack)
        val augment = handler.getStackInSlot(0)
        val item = augment.item
        return if (item is IBeaconAugment) item.getPotion(augment) else null
    }

}