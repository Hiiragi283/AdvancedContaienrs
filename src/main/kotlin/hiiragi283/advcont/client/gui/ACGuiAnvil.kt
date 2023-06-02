package hiiragi283.advcont.client.gui

import hiiragi283.advcont.container.ACContainerAnvil
import hiiragi283.advcont.tile.ACTileAnvil
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation

class ACGuiAnvil(player: InventoryPlayer, tile: ACTileAnvil) : ACGuiBase<ACTileAnvil>(ACContainerAnvil(player, tile)) {

    override val background: ResourceLocation = ResourceLocation("textures/gui/container/anvil.png")

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
        drawTexturedModalRect(guiLeft + 59, guiTop + 20, 0, ySize, 110, 16)
    }

}