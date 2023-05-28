@file:JvmName("DropUtil")

package hiiragi283.advcont.util

import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.IItemHandler

fun dropInventory(world: World, pos: BlockPos, inventory: IItemHandler) {
    dropInventory(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), inventory)
}

fun dropInventory(world: World, entity: Entity, inventory: IItemHandler) {
    dropInventory(world, entity.posX, entity.posY, entity.posZ, inventory)
}

fun dropInventory(world: World, x: Double, y: Double, z: Double, inventory: IItemHandler) {
    for (i in 0 until inventory.slots) {
        val stack = inventory.getStackInSlot(i)
        if (!stack.isEmpty) {
            InventoryHelper.spawnItemStack(world, x, y, z, stack)
        }
    }
}

fun dropItem(world: World, pos: BlockPos, stack: ItemStack) {
    dropItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
}

fun dropItem(world: World, entity: Entity, stack: ItemStack) {
    dropItem(world, entity.posX, entity.posY, entity.posZ, stack)
}

fun dropItem(world: World, x: Double, y: Double, z: Double, stack: ItemStack) {
    val drop = EntityItem(world, x, y, z, stack)
    drop.setPickupDelay(0)
    drop.motionX = 0.0
    drop.motionY = 0.0
    drop.motionZ = 0.0
    world.spawnEntity(drop)
}