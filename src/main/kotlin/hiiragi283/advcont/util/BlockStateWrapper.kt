package hiiragi283.advcont.util

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState

data class BlockStateWrapper(val block: Block, val meta: Int) {

    constructor(state: IBlockState) : this(state.block, state.block.getMetaFromState(state))

    fun toBlockState(): IBlockState = block.getStateFromMeta(meta)

}