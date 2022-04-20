package eu.withoutaname.mods.withoutawallpaper.setup

import eu.withoutaname.mods.withoutawallpaper.designs.client.ClientDesignManager
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.FORGE_BUS

object ClientSetup {

    fun init(event: FMLClientSetupEvent) {
        FORGE_BUS.addListener(ClientDesignManager::onLoggedIn)
    }
}