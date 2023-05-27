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
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object ACBlockFurnace :
    ACBlockContainer.Holdable<ACTileFurnace>("furnace", Material.ROCK, ACTileFurnace::class.java, 0) {

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

}