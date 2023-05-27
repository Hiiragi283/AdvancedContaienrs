package hiiragi283.advcont.block

import hiiragi283.advcont.AdvancedContainers
import hiiragi283.advcont.item.ACItemBlockBase
import net.minecraft.block.Block
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.IStringSerializable
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

abstract class ACBlockBase(ID: String, Material: Material, private val maxTips: Int) : Block(Material) {

    abstract val itemBlock: ACItemBlockBase?

    init {
        setRegistryName(AdvancedContainers.MOD_ID, ID)
        creativeTab = CreativeTabs.BUILDING_BLOCKS
        translationKey = "${AdvancedContainers.MOD_ID}.${ID}"
    }

    //    BlockState    //

    fun getBoolean(state: IBlockState, property: PropertyBool = ACProperty.ACTIVE): Boolean {
        return if (property in state.propertyKeys) state.getValue(property) else false
    }

    fun <T> getEnum(state: IBlockState, property: PropertyEnum<T>): T?
            where T : Enum<T>, T : IStringSerializable {
        return if (property in state.propertyKeys) state.getValue(property) else null
    }

    fun getFacing(state: IBlockState, property: PropertyDirection = BlockHorizontal.FACING): EnumFacing {
        return if (property in state.propertyKeys) state.getValue(property) else EnumFacing.NORTH
    }

    fun getInteger(state: IBlockState, property: PropertyInteger): Int {
        return if (property in state.propertyKeys) state.getValue(property) else -1
    }

    fun setBoolean(state: IBlockState, value: Boolean, property: PropertyBool = ACProperty.ACTIVE): IBlockState {
        if (property in state.propertyKeys) state.withProperty(property, value)
        return state
    }

    fun <T> setEnum(state: IBlockState, value: T, property: PropertyEnum<T>): IBlockState
            where T : Enum<T>, T : IStringSerializable {
        if (property in state.propertyKeys) state.withProperty(property, value)
        return state
    }

    fun setFacing(
        state: IBlockState,
        value: EnumFacing,
        property: PropertyDirection = BlockHorizontal.FACING
    ): IBlockState {
        if (property in state.propertyKeys) state.withProperty(property, value)
        return state
    }

    fun setInteger(state: IBlockState, value: Int, property: PropertyInteger): IBlockState {
        if (property in state.propertyKeys) state.withProperty(property, value)
        return state
    }

    //    Client    //

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        val path = stack.item.registryName!!.path
        if (maxTips != -1) {
            tooltip.add("§e=== Info ===")
            for (i in 0..maxTips) {
                tooltip.add(I18n.format("tips.${AdvancedContainers.MOD_ID}.${path}.$i"))
            }
        }
    }

    //    BlockBase    //

    fun register(registry: IForgeRegistry<Block>) {
        registry.register(this)
        AdvancedContainers.LOGGER.debug("The block $registryName was registered!")
    }

}