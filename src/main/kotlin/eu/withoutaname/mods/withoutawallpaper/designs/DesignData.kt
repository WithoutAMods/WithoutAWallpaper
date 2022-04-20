package eu.withoutaname.mods.withoutawallpaper.designs

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import java.util.*

data class DesignData(val uuid: UUID) {
    constructor(tag: CompoundTag) : this(tag.getUUID("uuid"))
    constructor(buf: FriendlyByteBuf) : this(buf.readUUID())

    fun save(): CompoundTag {
        val tag = CompoundTag()
        tag.putUUID("uuid", uuid)
        return tag
    }

    fun toBytes(buf: FriendlyByteBuf) {
        buf.writeUUID(uuid)
    }
}