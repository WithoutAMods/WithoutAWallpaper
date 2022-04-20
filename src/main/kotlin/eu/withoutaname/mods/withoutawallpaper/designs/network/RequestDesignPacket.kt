package eu.withoutaname.mods.withoutawallpaper.designs.network

import eu.withoutaname.mods.withoutawallpaper.designs.data.DesignManager
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.*
import java.util.function.Supplier

class RequestDesignPacket(private val uuid: UUID) {
    constructor(buf: FriendlyByteBuf) : this(buf.readUUID())

    fun toBytes(buf: FriendlyByteBuf) {
        buf.writeUUID(uuid)
    }

    fun handle(supplier: Supplier<NetworkEvent.Context>) {
        val ctx = supplier.get()
        ctx.enqueueWork {
            DesignManager.get().sendToPlayer(ctx.sender!!, uuid)
        }
    }
}