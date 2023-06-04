package hiiragi283.advcont.util

import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.init.PotionTypes
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.fml.common.registry.ForgeRegistries

object RegistryUtil {

    fun getBlock(location: ResourceLocation): Block {
        return ForgeRegistries.BLOCKS.getValue(location) ?: Blocks.AIR
    }

    fun getEnchantment(location: ResourceLocation): Enchantment {
        return ForgeRegistries.ENCHANTMENTS.getValue(location) ?: Enchantment.getEnchantmentByID(0)!!
    }

    fun getItem(location: ResourceLocation): Item {
        return ForgeRegistries.ITEMS.getValue(location) ?: Items.AIR
    }

    fun getPotion(location: ResourceLocation): Potion {
        return ForgeRegistries.POTIONS.getValue(location) ?: Potion.getPotionById(1)!!
    }

    fun getPotionType(location: ResourceLocation): PotionType {
        return ForgeRegistries.POTION_TYPES.getValue(location) ?: PotionTypes.EMPTY
    }

    fun getSound(location: ResourceLocation): SoundEvent {
        return ForgeRegistries.SOUND_EVENTS.getValue(location) ?: SoundEvents.AMBIENT_CAVE
    }

}