package eu.withoutaname.mods.withoutawallpaper.designs.data

import eu.withoutaname.mods.withoutawallpaper.WithoutAWallpaper
import eu.withoutaname.mods.withoutawallpaper.designs.DesignData
import eu.withoutaname.mods.withoutawallpaper.designs.network.DesignNetwork
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.saveddata.SavedData
import net.minecraftforge.fml.util.thread.SidedThreadGroups
import net.minecraftforge.server.ServerLifecycleHooks
import java.util.*

class DesignManager(tag: CompoundTag? = null) : SavedData() {

    private val designs: MutableMap<UUID, DesignData> = mutableMapOf()

    init {
        if (tag != null) {
            val designsTag = tag.getList("designs", Tag.TAG_COMPOUND.toInt())
            designsTag.forEach {
                val designData = DesignData(it as CompoundTag)
                designs[designData.uuid] = designData
            }
        }
    }

    override fun save(tag: CompoundTag): CompoundTag {
        val designsTag = ListTag()
        designs.values.forEach { designsTag.add(it.save()) }
        tag.put("designs", designsTag)
        return tag
    }

    fun sendToPlayer(player: ServerPlayer, designID: UUID) {
        designs[designID]
            ?.let { DesignNetwork.sendToPlayer(it, player) }
            ?: WithoutAWallpaper.LOGGER.warn("Design $designID not found but was requested from player ${player.displayName}")
    }

    companion object {

        fun get(): DesignManager {
            if (Thread.currentThread().threadGroup != SidedThreadGroups.SERVER) {
                throw RuntimeException("Only available on server side")
            }
            val server =
                (ServerLifecycleHooks.getCurrentServer() ?: throw RuntimeException("Couldn't get server instance"))
            return get(server)
        }

        fun get(server: MinecraftServer): DesignManager {
            return server.overworld().dataStorage.computeIfAbsent(
                ::DesignManager, ::DesignManager, "${WithoutAWallpaper.ID}_designs"
            )
        }
    }
}