package hiiragi283.advcont.item

import hiiragi283.advcont.AdvancedContainers
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

abstract class ACItemBase(ID: String, private var maxMeta: Int) : Item() {

    init {
        setRegistryName(AdvancedContainers.MOD_ID, ID)
        creativeTab = CreativeTabs.MISC
        hasSubtypes = maxMeta > 0
        maxMeta = 0.coerceAtLeast(maxMeta)
        translationKey = ID
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

    //    ItemBase    //

    open fun register(registry: IForgeRegistry<Item>) {
        registry.register(this)
        AdvancedContainers.LOGGER.debug("The item $registryName was registered!")
    }

    @SideOnly(Side.CLIENT)
    open fun registerModel() {}

    open fun registerOreDict() {}

    open fun registerRecipe() {}

}