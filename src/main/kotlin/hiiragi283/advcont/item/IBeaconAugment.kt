package hiiragi283.advcont.item

import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect

interface IBeaconAugment {

    fun getPotion(stack: ItemStack): Potion?

    fun getEffect(stack: ItemStack): PotionEffect? = getPotion(stack)?.let { PotionEffect(it, 110, 1, true, false) }

}