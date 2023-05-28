package hiiragi283.advcont.network

import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

sealed class ACMessage(var x: Int = 0, var y: Int = 0, var z: Int = 0) : IMessage {

    override fun fromBytes(buf: ByteBuf?) {
        buf?.let {
            //座標を読み取る
            x = it.readInt()
            y = it.readInt()
            z = it.readInt()
        }
    }

    override fun toBytes(buf: ByteBuf?) {
        //座標を書き込む
        buf?.writeInt(x)
        buf?.writeInt(y)
        buf?.writeInt(z)
    }

    class Furnace(x: Int = 0, y: Int = 0, z: Int = 0, var time: Int = 0) : ACMessage(x, y, z) {

        override fun fromBytes(buf: ByteBuf?) {
            super.fromBytes(buf)
            buf?.let {
                time = it.readInt()
            }
        }

        override fun toBytes(buf: ByteBuf?) {
            super.toBytes(buf)
            buf?.writeInt(time)
        }
    }
}