package hiiragi283.advcont.container

import hiiragi283.advcont.tile.ACTileBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot

abstract class ACContainerBase<T : ACTileBase>(val player: InventoryPlayer, val tile: T) : Container() {

    fun addPlayerInventory(player: InventoryPlayer, posY: Int) {
        //プレイヤーのインベントリのスロットを設定
        for (y in 0..2) {
            for (x in 0..8) {
                addSlotToContainer(Slot(player, x + y * 9 + 9, 8 + x * 18, y * 18 + posY))
            }
        }
        //プレイヤーのホットバーのスロットを設定
        for (x in 0..8) {
            addSlotToContainer(Slot(player, x, 8 + x * 18, 3 * 18 + (posY + 4)))
        }
    }

    override fun canInteractWith(player: EntityPlayer): Boolean = true

}