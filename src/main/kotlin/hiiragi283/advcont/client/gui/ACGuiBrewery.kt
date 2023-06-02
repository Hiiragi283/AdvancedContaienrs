package hiiragi283.advcont.client.gui

import hiiragi283.advcont.container.ACContainerBrewery
import hiiragi283.advcont.tile.ACTileBrewery
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation

class ACGuiBrewery(player: InventoryPlayer, private val tile: ACTileBrewery) :
    ACGuiBase<ACTileBrewery>(ACContainerBrewery(player, tile)) {

    override val background: ResourceLocation = ResourceLocation("textures/gui/container/furnace.png")

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
        //進行バーは常に更新する
        this.drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, tile.countdown * 24 / 200, 16)
    }
}