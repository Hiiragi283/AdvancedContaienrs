package hiiragi283.advcont.block

import hiiragi283.advcont.AdvancedContainers
import hiiragi283.advcont.item.ACItemBlockBase
import hiiragi283.advcont.tile.ACTileBase
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry

abstract class ACBlockContainer<T : ACTileBase>(ID: String, material: Material, val tile: Class<T>, maxTips: Int) :
    ACBlockBase(ID, material, maxTips), ITileEntityProvider {

    override val itemBlock: ACItemBlockBase? = ACItemBlockBase(this)

    init {
        GameRegistry.registerTileEntity(tile, ResourceLocation(AdvancedContainers.MOD_ID, "te_$ID"))
    }

    //    CommonEvent    //

    override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
        val tile = world.getTileEntity(pos)
        if (tile !== null && tile is ACTileBase) tile.onTileRemoved(world, pos, state)
        super.breakBlock(world, pos, state)
    }

    override fun onBlockActivated(
        world: World,
        pos: BlockPos,
        state: IBlockState,
        player: EntityPlayer,
        hand: EnumHand,
        facing: EnumFacing,
        hitX: Float,
        hitY: Float,
        hitZ: Float
    ): Boolean {
        if (hand == EnumHand.MAIN_HAND) {
            val tile = world.getTileEntity(pos)
            return if (tile !== null && tile is ACTileBase) tile.onTileActivated(
                world,
                pos,
                player,
                hand,
                facing
            ) else false
        }
        return false
    }

    override fun onBlockPlacedBy(
        world: World,
        pos: BlockPos,
        state: IBlockState,
        placer: EntityLivingBase,
        stack: ItemStack
    ) {
        val tile = world.getTileEntity(pos)
        if (tile !== null && tile is ACTileBase) tile.onTilePlaced(world, pos, state, placer, stack)
        super.onBlockPlacedBy(world, pos, state, placer, stack)
    }

    //    ITileEntityProvider    //

    override fun createNewTileEntity(worldIn: World, meta: Int): T = tile.newInstance()

    abstract class Holdable<T : ACTileBase>(ID: String, material: Material, tile: Class<T>, maxTips: Int) :
        ACBlockContainer<T>(ID, material, tile, maxTips) {

        //    General    //

        override fun getDrops(
            drops: NonNullList<ItemStack>,
            world: IBlockAccess,
            pos: BlockPos,
            state: IBlockState,
            fortune: Int
        ) {
            val stack = ItemStack(this)
            world.getTileEntity(pos)?.let {
                stack.getOrCreateSubCompound("BlockEntityTag").merge(it.updateTag)
            }
            drops.add(stack)
        }

        /**
         * Reference: net.minecraft.block.BlockFlowerPot
         */

        override fun removedByPlayer(
            state: IBlockState,
            world: World,
            pos: BlockPos,
            player: EntityPlayer,
            willHarvest: Boolean
        ): Boolean {
            //破壊のタイミングを遅らせる
            return if (willHarvest) true else super.removedByPlayer(state, world, pos, player, false)
        }

        override fun harvestBlock(
            world: World,
            player: EntityPlayer,
            pos: BlockPos,
            state: IBlockState,
            te: TileEntity?,
            tool: ItemStack
        ) {
            super.harvestBlock(world, player, pos, state, te, tool)
            world.setBlockToAir(pos)
        }
    }
}