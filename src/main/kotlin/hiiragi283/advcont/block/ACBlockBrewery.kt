package hiiragi283.advcont.block

import hiiragi283.advcont.tile.ACTileBrewery
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockFaceShape
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ACBlockBrewery : ACBlockContainer<ACTileBrewery>("brewery", Material.IRON, ACTileBrewery::class.java, 0) {

    init {
        blockHardness = 0.5f
        creativeTab = CreativeTabs.BREWING
    }

    //    General    //

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("BlockFaceShape.UNDEFINED", "net.minecraft.block.state.BlockFaceShape")
    )
    override fun getBlockFaceShape(
        worldIn: IBlockAccess,
        state: IBlockState,
        pos: BlockPos,
        face: EnumFacing
    ): BlockFaceShape = BlockFaceShape.UNDEFINED

    @Deprecated("Deprecated in Java", ReplaceWith("false"))
    override fun isFullBlock(state: IBlockState) = false

    @Deprecated("Deprecated in Java", ReplaceWith("false"))
    override fun isOpaqueCube(state: IBlockState) = false

    //    Client    //

    @SideOnly(Side.CLIENT)
    override fun getRenderLayer(): BlockRenderLayer = BlockRenderLayer.CUTOUT

    //    IACEntry    //

    @SideOnly(Side.CLIENT)
    override fun registerModel() {
        itemBlock?.registerModel()
    }

    override fun registerRecipe() {
        GameRegistry.addShapelessRecipe(
            registryName!!,
            registryName!!,
            ItemStack(this),
            Ingredient.fromStacks(ItemStack(Items.BREWING_STAND))
        )
        GameRegistry.addShapelessRecipe(
            ResourceLocation("brewing_stand_alt"),
            ResourceLocation("brewing_stand_alt"),
            ItemStack(Items.BREWING_STAND),
            Ingredient.fromStacks(ItemStack(this))
        )
    }
}