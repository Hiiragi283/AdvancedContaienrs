@file:JvmName("ACUtil")

package hiiragi283.advcont.util

import hiiragi283.advcont.AdvancedContainers
import hiiragi283.advcont.init.ACItems
import net.minecraft.client.Minecraft
import net.minecraft.command.ICommandSender
import net.minecraft.init.Items
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionUtils
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.IRarity
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryModifiable

fun Boolean.toInt() = if (this) 1 else 0

fun Int.toBoolean() = this % 2 != 0

fun List<ItemStack>.toNonNullList(): NonNullList<ItemStack> {
    return NonNullList.withSize(this.size, ItemStack.EMPTY).also {
        for (i in 0 until this.size) {
            it[i] = this[i]
        }
    }
}

fun executeCommand(sender: ICommandSender, command: String) {
    Minecraft.getMinecraft().integratedServer?.getCommandManager()?.executeCommand(sender, command)
}

fun getEnumRarity(name: String): IRarity {
    return when (name) {
        "Uncommon" -> EnumRarity.UNCOMMON
        "Rare" -> EnumRarity.RARE
        "Epic" -> EnumRarity.EPIC
        else -> EnumRarity.COMMON
    }
}

//エントリーを削除するメソッド
fun removeRegistryEntry(registry: IForgeRegistry<*>, registryName: ResourceLocation) {
    if (registry is IForgeRegistryModifiable<*>) {
        registry.remove(registryName)
        AdvancedContainers.LOGGER.warn("The entry $registryName was removed from ${registry::class.java.name}!")
    } else AdvancedContainers.LOGGER.warn("The registry ${registry::class.java.name} is not implementing IForgeRegistryModifiable!")
}

fun remove(registry: IForgeRegistry<*>, registryName: String) {
    removeRegistryEntry(registry, ResourceLocation(registryName))
}

fun convertPotion(stack: ItemStack): ItemStack {
    val itemConverted = when (stack.item) {
        ACItems.POTION -> Items.POTIONITEM
        else -> ACItems.POTION
    }
    val result = ItemStack(itemConverted)
    return PotionUtils.addPotionToItemStack(result, PotionUtils.getPotionFromItem(stack))
}