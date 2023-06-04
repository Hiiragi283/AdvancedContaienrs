package hiiragi283.advcont.client.gui

import hiiragi283.advcont.container.ACContainerBeacon
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation

class ACGuiBeacon(val player: EntityPlayer) : GuiContainer(ACContainerBeacon(player)) {

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val title = I18n.format("gui.advanced_containers.beacon_ring.name")
        fontRenderer.drawString(title, xSize / 2 - fontRenderer.getStringWidth(title) / 2, 6, 0x404040)
        fontRenderer.drawString(player.displayName.unformattedText, 8, ySize - 96 + 2, 0x404040)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        mc.textureManager.bindTexture(ResourceLocation("textures/gui/container/anvil.png"))
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
    }
}