package hiiragi283.advcont.client.gui

import hiiragi283.advcont.container.ACContainerFurnace
import hiiragi283.advcont.tile.ACTileFurnace
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation

class ACGuiFurnace(private val player: InventoryPlayer, private val tile: ACTileFurnace) :
    ACGuiBase<ACTileFurnace>(ACContainerFurnace(player, tile)) {

    override val background: ResourceLocation = ResourceLocation("textures/gui/container/furnace.png")

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val title = tile.displayName.unformattedText
        fontRenderer.drawString(title, xSize / 2 - fontRenderer.getStringWidth(title) / 2, 6, 0x404040)
        fontRenderer.drawString(player.displayName.unformattedText, 8, ySize - 96 + 2, 0x404040)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
        //燃焼可能な場合は炎を描画する
        if (tile.canBurn()) {
            this.drawTexturedModalRect(getOriginX() + 57, getOriginY() + 36, 176, 0, 14, 14)
        }
        //進行バーは常に更新する
        this.drawTexturedModalRect(getOriginX() + 79, getOriginY() + 34, 176, 14, tile.countdown * 24 / 200, 16)
    }
}