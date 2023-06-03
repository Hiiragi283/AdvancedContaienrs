package hiiragi283.advcont.item

import hiiragi283.advcont.AdvancedContainers
import hiiragi283.advcont.init.IACEntry
import hiiragi283.advcont.util.ModelUtil
import net.minecraft.client.renderer.color.BlockColors
import net.minecraft.client.renderer.color.ItemColors
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

abstract class ACItemBase(ID: String, private var maxMeta: Int, private val maxTips: Int) : Item(), IACEntry<Item> {

    init {
        setRegistryName(AdvancedContainers.MOD_ID, ID)
        creativeTab = CreativeTabs.MISC
        hasSubtypes = maxMeta > 0
        maxMeta = 0.coerceAtLeast(maxMeta)
        translationKey = "${AdvancedContainers.MOD_ID}.${ID}"
    }

    //    General    //

    override fun getMetadata(damage: Int): Int = if (damage in 0..maxMeta) damage else maxMeta

    override fun getTranslationKey(stack: ItemStack): String =
        super.getTranslationKey() + if (maxMeta == 0) "" else ".${stack.metadata}"

    fun openGui(player: EntityPlayer, world: World, pos: BlockPos) {
        player.openGui(AdvancedContainers.Instance, 1, world, pos.x, pos.y, pos.z)
    }
    //    Client    //

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        val path = stack.item.registryName!!.path
        if (maxTips != -1) {
            tooltip.add("§e=== Info ===")
            for (i in 0..maxTips) {
                tooltip.add(I18n.format("tips.${AdvancedContainers.MOD_ID}.${path}.$i"))
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override fun getSubItems(tab: CreativeTabs, subItems: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab)) {
            //メタデータの最大値まで処理を繰り返す
            for (i in 0..maxMeta) {
                subItems.add(ItemStack(this, 1, i))
            }
        }
    }

    //    IACEntry    //

    override fun register(registry: IForgeRegistry<Item>) {
        registry.register(this)
        AdvancedContainers.LOGGER.debug("The item $registryName was registered!")
    }

    override fun registerOreDict() {}

    override fun registerRecipe() {}

    @SideOnly(Side.CLIENT)
    override fun registerColorBlock(blockColors: BlockColors) {
    }

    @SideOnly(Side.CLIENT)
    override fun registerColorItem(itemColors: ItemColors) {
    }

    @SideOnly(Side.CLIENT)
    override fun registerModel() {
        for (i in 0..maxMeta) {
            ModelUtil.setItemModel(this, i)
        }
    }

}