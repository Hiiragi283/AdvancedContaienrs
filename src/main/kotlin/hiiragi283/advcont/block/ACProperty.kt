package hiiragi283.advcont.block

import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.util.EnumFacing

object ACProperty {

    val ACTIVE: PropertyBool = PropertyBool.create("active")

    val AGE4: PropertyInteger = PropertyInteger.create("age", 0, 3)
    val AGE6: PropertyInteger = PropertyInteger.create("age", 0, 5)
    val AGE8: PropertyInteger = PropertyInteger.create("age", 0, 7)

    val COUNT8: PropertyInteger = PropertyInteger.create("count", 0, 7)

    //0 -> NORTH, 1 -> EAST, 2 -> SOUTH, 3 -> WEST
    val HORIZONTAL: PropertyDirection =
        PropertyDirection.create("facing", listOf(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST))

    val TYPE16: PropertyInteger = PropertyInteger.create("type", 0, 15)

}