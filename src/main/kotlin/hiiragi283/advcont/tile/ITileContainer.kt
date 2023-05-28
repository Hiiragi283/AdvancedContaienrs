package hiiragi283.advcont.tile

import hiiragi283.advcont.AdvancedContainers
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.IInteractionObject

interface ITileContainer : IInteractionObject {

    override fun getDisplayName() = TextComponentTranslation(this.name)

    override fun getName() = "gui.${AdvancedContainers.MOD_ID}.$guiID.name"

    override fun hasCustomName() = false

}