package hiiragi283.advcont.item

import hiiragi283.advcont.AdvancedContainers
import hiiragi283.advcont.init.IACEntry
import hiiragi283.advcont.util.ModelUtil
import net.minecraft.client.renderer.color.BlockColors
import net.minecraft.client.renderer.color.ItemColors
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

abstract class ACItemBase(ID: String, private var maxMeta: Int) : Item(), IACEntry<Item> {

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