package hiiragi283.advcont.item

import hiiragi283.advcont.util.CraftingBuilder
import hiiragi283.advcont.util.RegistryUtil
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.init.PotionTypes
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionUtils
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.common.IRarity
import net.minecraftforge.oredict.OreDictionary

object ACItemBeaconAugment : ACItemBase("beacon_augment", 5, -1), IBeaconAugment {

    init {
        maxStackSize = 1
    }

    private val MAP: LinkedHashMap<Int, Property> = linkedMapOf(
        0 to Property("Speed Drink", 0, TextFormatting.AQUA, "speed"),
        1 to Property("Haste Pickaxe", 1, TextFormatting.YELLOW, "haste"),
        2 to Property("Speed Drink", 2, TextFormatting.GRAY, "resistance"),
        3 to Property("Speed Drink", 3, TextFormatting.GREEN, "jump_boost"),
        4 to Property("Speed Drink", 4, TextFormatting.RED, "strength"),
        5 to Property("Speed Drink", 5, TextFormatting.LIGHT_PURPLE, "regeneration")
    )

    private class Property(val name: String, val index: Int, val color: Rarity, val potion: Potion) {

        constructor(name: String, index: Int, color: TextFormatting, potion: String) :
                this(name, index, Rarity(color), RegistryUtil.getPotion(ResourceLocation(potion)))

    }

    private class Rarity(val format: TextFormatting) : IRarity {

        override fun getColor(): TextFormatting = format

        override fun getName(): String = ""

    }

    //    General    //

    override fun getForgeRarity(stack: ItemStack): IRarity = MAP.getOrDefault(stack.metadata, MAP[0]!!).color

    //    IACEntry    //

    override fun registerRecipe() {
        //Speed Drink
        CraftingBuilder("${registryName.toString()}_0", ItemStack(this, 1, 0))
            .setPattern("ABA", "BCB", "ABA")
            .setIngredient('A', ItemStack(Blocks.REDSTONE_BLOCK))
            .setIngredient('B', ItemStack(Items.SUGAR))
            .setIngredient('C', PotionUtils.addPotionToItemStack(ItemStack(Items.POTIONITEM), PotionTypes.WATER))
            .build()
        //Haste Pickaxe
        CraftingBuilder("${registryName.toString()}_1", ItemStack(this, 1, 1))
            .setPattern("AAA", " B ", " B ")
            .setIngredient('A', "blockGold")
            .setIngredient('B', ItemStack(Items.BLAZE_ROD))
            .build()
        //Resistance Shield
        CraftingBuilder("${registryName.toString()}_2", ItemStack(this, 1, 2))
            .setPattern("ABA", "BCB", "ABA")
            .setIngredient('A', "blockIron")
            .setIngredient('B', ItemStack(Blocks.ANVIL))
            .setIngredient('C', ItemStack(Items.SHIELD))
            .build()
        //Jump Boots
        CraftingBuilder("${registryName.toString()}_3", ItemStack(this, 1, 3))
            .setPattern("A A", "BCB", "D D")
            .setIngredient('A', ItemStack(Items.FEATHER))
            .setIngredient('B', ItemStack(Blocks.SLIME_BLOCK))
            .setIngredient('C', ItemStack(Items.LEATHER_BOOTS, 1, OreDictionary.WILDCARD_VALUE))
            .setIngredient('D', ItemStack(Items.RABBIT_FOOT))
            .build()
        //Sword of Strength
        CraftingBuilder("${registryName.toString()}_4", ItemStack(this, 1, 4))
            .setPattern("ABA", "BCB", "ABA")
            .setIngredient('A', ItemStack(Blocks.TNT))
            .setIngredient('B', ItemStack(Items.BLAZE_POWDER))
            .setIngredient('C', ItemStack(Items.IRON_SWORD, 1, OreDictionary.WILDCARD_VALUE))
            .build()
        //Regeneration Heart
        CraftingBuilder("${registryName.toString()}_5", ItemStack(this, 1, 5))
            .setPattern("ABA", "BCB", "ABA")
            .setIngredient('A', ItemStack(Items.SPECKLED_MELON))
            .setIngredient('B', ItemStack(Items.GHAST_TEAR))
            .setIngredient('C', ItemStack(Items.BEETROOT))
            .build()
    }

    //    IBeaconAugment    //

    override fun getPotion(stack: ItemStack): Potion = MAP.getOrDefault(stack.metadata, MAP[0]!!).potion

}