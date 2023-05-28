package hiiragi283.advcont.block

import hiiragi283.advcont.tile.ACTileFurnace
import hiiragi283.advcont.util.toBoolean
import hiiragi283.advcont.util.toInt
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ACBlockFurnace :
    ACBlockContainer.Holdable<ACTileFurnace>("furnace", Material.ROCK, ACTileFurnace::class.java, -1) {

    init {
        blockHardness = 3.5f
        creativeTab = CreativeTabs.DECORATIONS
        defaultState = blockState.baseState.withProperty(BlockHorizontal.FACING, EnumFacing.NORTH)
            .withProperty(ACProperty.ACTIVE, false)
        soundType = SoundType.STONE
    }

    //    BlockState    //

    override fun createBlockState(): BlockStateContainer =
        BlockStateContainer(this, BlockHorizontal.FACING, ACProperty.ACTIVE)

    override fun getMetaFromState(state: IBlockState): Int =
        getFacing(state, BlockHorizontal.FACING).index + getBoolean(state).toInt() * 4

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
        return setFacing(this.defaultState, placer.horizontalFacing.opposite, BlockHorizontal.FACING)
    }

    @Deprecated("Deprecated in Java")
    override fun getStateFromMeta(meta: Int): IBlockState {
        var facing = EnumFacing.byIndex(meta % 6)
        if (facing.axis == EnumFacing.Axis.Y) facing = EnumFacing.NORTH
        return this.defaultState.withProperty(BlockHorizontal.FACING, facing)
            .withProperty(ACProperty.ACTIVE, (meta / 4).toBoolean())
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
            Ingredient.fromStacks(ItemStack(Blocks.FURNACE))
        )
        GameRegistry.addShapelessRecipe(
            ResourceLocation("furnace_alt"),
            ResourceLocation("furnace_alt"),
            ItemStack(Blocks.FURNACE),
            Ingredient.fromStacks(ItemStack(this))
        )
    }
}