package hiiragi283.advcont.tile

import hiiragi283.advcont.AdvancedContainers
import hiiragi283.advcont.capabilitiy.ACItemHandlerWrapper
import hiiragi283.advcont.capabilitiy.ACTankWrapper
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class ACTileBase : TileEntity() {

    open lateinit var inventory: ACItemHandlerWrapper
    open lateinit var tank: ACTankWrapper

    val keyEnergy = "energy"
    val keyGas = "gas"
    val keyHeat = "heat"
    val keyInventory = "inventory"
    val keyTank = "tank"

    //    General    //

    fun getState(): IBlockState = if (hasWorld()) world.getBlockState(pos) else Blocks.AIR.defaultState

    //    NBT tag    //

    override fun getUpdateTag(): NBTTagCompound = writeToNBT(NBTTagCompound()) //オーバーライドしないと正常に更新されない

    //    Packet    //

    override fun getUpdatePacket(): SPacketUpdateTileEntity = SPacketUpdateTileEntity(pos, 0, updateTag) //NBTタグの情報を送る

    override fun onDataPacket(net: NetworkManager, pkt: SPacketUpdateTileEntity) {
        readFromNBT(pkt.nbtCompound) //受け取ったパケットのNBTタグを書き込む
    }

    /**
     * Thanks to defeatedcrow!
     * Reference: https://github.com/defeatedcrow/FluidTankTutorialMod/blob/master/src/main/java/defeatedcrow/tutorial/ibc/base/TileIBC.java#L93
     */

    override fun shouldRefresh(world: World, pos: BlockPos, oldState: IBlockState, newState: IBlockState): Boolean =
        oldState.block != newState.block //更新の前後でBlockが変化する場合のみtrue

    //    CommonEvent    //

    open fun onTileActivated(
        world: World,
        pos: BlockPos,
        player: EntityPlayer,
        hand: EnumHand,
        facing: EnumFacing
    ): Boolean = false

    open fun onTilePlaced(
        world: World,
        pos: BlockPos,
        state: IBlockState,
        placer: EntityLivingBase,
        stack: ItemStack
    ) {
    }

    fun readNBTFromStack(stack: ItemStack) {
        //ItemStackに保存されたNBTタグを取得し，nullでない場合
        stack.getSubCompound("BlockEntityTag")?.let {
            //TileEntityからNBTタグを取得
            val tagTile = updateTag
            //tagTileにtagStackを合併
            tagTile.merge(it)
            //座標を修正する
            tagTile.setInteger("x", this.pos.x)
            tagTile.setInteger("y", this.pos.y)
            tagTile.setInteger("z", this.pos.z)
            //座標が異なる場合
            if (tagTile != updateTag.copy()) {
                readFromNBT(tagTile)
                markDirty()
            }
        }
    }

    open fun onTileRemoved(world: World, pos: BlockPos, state: IBlockState) {}

    fun openGui(player: EntityPlayer, world: World, pos: BlockPos) {
        player.openGui(AdvancedContainers.Instance, 0, world, pos.x, pos.y, pos.z)
    }

    abstract class Tickable(private val maxCount: Int) : ACTileBase(), ITickable {

        var countdown = 0

        //    ITickable    //

        override fun update() {
            onUpdateTick()
            if (countdown >= maxCount) {
                onUpdate()
                if (!world.isRemote) {
                    onUpdateServer()
                } else {
                    onUpdateClient()
                }
                countdown = 0
            } else countdown++
        }

        //1tickごとにサーバー側とクライアント側で同時に実行するメソッド
        open fun onUpdateTick() {}

        //20tickごとにサーバー側とクライアント側で同時に実行するメソッド
        open fun onUpdate() {}

        //20tickごとにサーバー側で実行するメソッド
        open fun onUpdateServer() {}

        //20tickごとにクライアント側で実行するメソッド
        open fun onUpdateClient() {}

    }

}