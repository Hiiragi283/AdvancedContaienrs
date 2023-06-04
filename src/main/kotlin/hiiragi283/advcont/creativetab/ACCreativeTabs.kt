package hiiragi283.advcont.creativetab

import hiiragi283.advcont.AdvancedContainers
import hiiragi283.advcont.init.ACItems
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.PotionTypes
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionUtils
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ACCreativeTabs {

    object MAIN : CreativeTabs("${AdvancedContainers.MOD_ID}.main") {

        @SideOnly(Side.CLIENT)
        override fun createIcon(): ItemStack = ItemStack(ACItems.BEACON_RING)
    }

    object POTION : CreativeTabs("${AdvancedContainers.MOD_ID}.potion") {

        @SideOnly(Side.CLIENT)
        override fun createIcon(): ItemStack =
            PotionUtils.addPotionToItemStack(ItemStack(ACItems.POTION), PotionTypes.REGENERATION)
    }

}