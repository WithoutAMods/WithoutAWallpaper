package eu.withoutaname.mods.withoutawallpaper.designs.network

import eu.withoutaname.mods.withoutawallpaper.WithoutAWallpaper
import eu.withoutaname.mods.withoutawallpaper.designs.DesignData
import eu.withoutaname.mods.withoutawallpaper.designs.client.ClientDesignManager
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.PacketDistributor
import net.minecraftforge.network.simple.SimpleChannel
import java.util.function.BiConsumer

object DesignNetwork {

    private lateinit var INSTANCE: SimpleChannel

    private var packetId = 0
        get() = field++

    fun register() {
        INSTANCE = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation(WithoutAWallpaper.ID, "designs"))
            .networkProtocolVersion { "1.0" }
            .clientAcceptedVersions { true }
            .serverAcceptedVersions { true }
            .simpleChannel()

        INSTANCE.messageBuilder(RequestDesignPacket::class.java, packetId, NetworkDirection.PLAY_TO_SERVER)
            .decoder(::RequestDesignPacket)
            .encoder(RequestDesignPacket::toBytes)
            .consumer(RequestDesignPacket::handle)
            .add()

        INSTANCE.messageBuilder(DesignData::class.java, packetId, NetworkDirection.PLAY_TO_CLIENT)
            .decoder(::DesignData)
            .encoder(DesignData::toBytes)
            .consumer(BiConsumer { designData, supplier ->
                supplier.get().enqueueWork { ClientDesignManager.addDesignData(designData) }
            })
            .add()
    }

    fun <MSG : Any> sendToServer(message: MSG) {
        INSTANCE.sendToServer(message)
    }

    fun <MSG : Any> sendToPlayer(message: MSG, player: ServerPlayer) {
        INSTANCE.send(PacketDistributor.PLAYER.with { player }, message)
    }
}