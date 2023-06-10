package hiiragi283.advcont.item

import hiiragi283.advcont.creativetab.ACCreativeTabs
import hiiragi283.advcont.util.dropItem
import net.minecraft.client.renderer.color.ItemColors
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.init.PotionTypes
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.potion.PotionType
import net.minecraft.potion.PotionUtils
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.NonNullList
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ACItemPotion : ACItemBase("potion", 0, 0) {

    init {
        creativeTab = ACCreativeTabs.POTION
    }

    //    General    //

    override fun getItemStackDisplayName(stack: ItemStack): String {
        val potion = I18n.format(PotionUtils.getPotionFromItem(stack).getNamePrefixed("potion.effect."))
        return I18n.format("item.advanced_containers.potion.name", potion)
    }

    //    Event    //

    override fun getItemUseAction(stack: ItemStack): EnumAction = EnumAction.DRINK

    override fun getMaxItemUseDuration(stack: ItemStack): Int = 32

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        player.activeHand = hand
        return ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand))
    }

    override fun onItemUseFinish(stack: ItemStack, world: World, entityLiving: EntityLivingBase): ItemStack {
        if (world.isRemote) return stack
        PotionUtils.getEffectsFromStack(stack).forEach {
            //即時効果の場合は付与方法を変更
            if (it.potion.isInstant) {
                it.potion.affectEntity(entityLiving, entityLiving, entityLiving, it.amplifier, 1.0)
            } else {
                entityLiving.addPotionEffect(PotionEffect(it))
            }
        }
        //空のビンをその場にドロップする
        dropItem(world, entityLiving, ItemStack(Items.GLASS_BOTTLE))
        //ポーションを消費する
        stack.shrink(1)
        return stack
    }

    //    Client    //

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        PotionUtils.addPotionTooltip(stack, tooltip, 1.0f)
        super.addInformation(stack, world, tooltip, flag)
    }

    @SideOnly(Side.CLIENT)
    override fun getDefaultInstance(): ItemStack {
        return PotionUtils.addPotionToItemStack(super.getDefaultInstance(), PotionTypes.WATER)
    }

    @SideOnly(Side.CLIENT)
    override fun getSubItems(tab: CreativeTabs, subItems: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab)) {
            PotionType.REGISTRY.forEach {
                if (it !== PotionTypes.EMPTY) {
                    subItems.add(PotionUtils.addPotionToItemStack(ItemStack(this), it))
                }

            }
        }
    }

    //    IACEntry    //

    @SideOnly(Side.CLIENT)
    override fun registerColorItem(itemColors: ItemColors) {
        itemColors.registerItemColorHandler({ stack, tintIndex ->
            if (tintIndex > 0) -1 else PotionUtils.getColor(stack)
        }, this)
    }

}