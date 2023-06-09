package hiiragi283.advcont.client.gui

import hiiragi283.advcont.container.ACContainerFurnace
import hiiragi283.advcont.tile.ACTileFurnace
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation

class ACGuiFurnace(player: InventoryPlayer, private val tile: ACTileFurnace) :
    ACGuiBase<ACTileFurnace>(ACContainerFurnace(player, tile)) {

    override val background: ResourceLocation = ResourceLocation("textures/gui/container/furnace.png")

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
        //燃焼可能な場合は炎を描画する
        if (tile.canBurn()) {
            this.drawTexturedModalRect(guiLeft + 57, guiTop + 36, 176, 0, 14, 14)
        }
        //進行バーは常に更新する
        this.drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, tile.countdown * 24 / 200, 16)
    }
}