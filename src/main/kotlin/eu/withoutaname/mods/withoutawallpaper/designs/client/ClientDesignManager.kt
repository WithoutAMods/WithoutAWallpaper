package eu.withoutaname.mods.withoutawallpaper.designs.client

import eu.withoutaname.mods.withoutawallpaper.WithoutAWallpaper
import eu.withoutaname.mods.withoutawallpaper.designs.DesignData
import eu.withoutaname.mods.withoutawallpaper.designs.network.DesignNetwork
import eu.withoutaname.mods.withoutawallpaper.designs.network.RequestDesignPacket
import net.minecraftforge.client.event.ClientPlayerNetworkEvent
import java.util.*

object ClientDesignManager {

    private val designs: MutableMap<UUID, DesignData> = mutableMapOf()
    private val requested: MutableSet<UUID> = mutableSetOf()

    fun get(designID: UUID): DesignData? {
        return designs[designID]
            ?: run {
                if (requested.add(designID)) {
                    DesignNetwork.sendToServer(RequestDesignPacket(designID))
                }
                null
            }
    }

    fun onLoggedIn(event: ClientPlayerNetworkEvent.LoggedInEvent) {
        designs.clear()
        requested.clear()
        WithoutAWallpaper.LOGGER.info("Cleared client side cached designs")
    }

    fun addDesignData(designData: DesignData) {
        designs[designData.uuid] = designData
        requested.remove(designData.uuid)
    }
}