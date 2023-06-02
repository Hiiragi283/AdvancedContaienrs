package hiiragi283.advcont.block

import hiiragi283.advcont.tile.ACTileAnvil
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockFaceShape
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.*
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ACBlockAnvil : ACBlockContainer<ACTileAnvil>("anvil", Material.ANVIL, ACTileAnvil::class.java, 0) {

    private val BOX_X: AxisAlignedBB = AxisAlignedBB(0.0, 0.0, 0.125, 1.0, 1.0, 0.875)
    private val BOX_Z: AxisAlignedBB = AxisAlignedBB(0.125, 0.0, 0.0, 0.875, 1.0, 1.0)

    init {
        blockHardness = 0.5f
        creativeTab = CreativeTabs.DECORATIONS
        defaultState = blockState.baseState.withProperty(BlockHorizontal.FACING, EnumFacing.NORTH)
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

    @Deprecated("Deprecated in Java")
    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB =
        if (state.getValue(BlockHorizontal.FACING).axis == EnumFacing.Axis.X) BOX_X else BOX_Z

    @Deprecated("Deprecated in Java", ReplaceWith("false"))
    override fun isFullBlock(state: IBlockState) = false

    @Deprecated("Deprecated in Java", ReplaceWith("false"))
    override fun isOpaqueCube(state: IBlockState) = false

    //    BlockState    //

    override fun createBlockState(): BlockStateContainer = BlockStateContainer(this, BlockHorizontal.FACING)

    override fun getMetaFromState(state: IBlockState): Int = getFacing(state, BlockHorizontal.FACING).index

    override fun getStateForPlacement(
        world: World,
        pos: BlockPos,
        facing: EnumFacing,
        hitX: Float,
        hitY: Float,
        hitZ: Float,
        meta: Int,
        placer: EntityLivingBase,
        hand: EnumHand
    ): IBlockState {
        return this.defaultState.withProperty(BlockHorizontal.FACING, placer.horizontalFacing.opposite)
    }

    @Deprecated("Deprecated in Java")
    override fun getStateFromMeta(meta: Int): IBlockState {
        var facing = EnumFacing.byIndex(meta % 6)
        if (facing.axis == EnumFacing.Axis.Y) facing = EnumFacing.NORTH
        return this.defaultState.withProperty(BlockHorizontal.FACING, facing)
    }

    @Deprecated("Deprecated in Java")
    override fun withMirror(state: IBlockState, mirror: Mirror): IBlockState {
        val facing = mirror.mirror(state.getValue(BlockHorizontal.FACING))
        return setFacing(state, facing, BlockHorizontal.FACING)
    }

    @Deprecated("Deprecated in Java")
    override fun withRotation(state: IBlockState, rotation: Rotation): IBlockState {
        val facing = rotation.rotate(state.getValue(BlockHorizontal.FACING))
        return setFacing(state, facing, BlockHorizontal.FACING)
    }

    //    Client    //

    @SideOnly(Side.CLIENT)
    override fun getRenderLayer(): BlockRenderLayer = BlockRenderLayer.CUTOUT

    @Deprecated("Deprecated in Java", ReplaceWith("true"))
    @SideOnly(Side.CLIENT)
    override fun shouldSideBeRendered(
        blockState: IBlockState,
        blockAccess: IBlockAccess,
        pos: BlockPos,
        side: EnumFacing
    ): Boolean = true

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
            Ingredient.fromStacks(ItemStack(Blocks.ANVIL))
        )
        GameRegistry.addShapelessRecipe(
            ResourceLocation("anvil_alt"),
            ResourceLocation("anvil_alt"),
            ItemStack(Blocks.ANVIL),
            Ingredient.fromStacks(ItemStack(this))
        )
    }
}